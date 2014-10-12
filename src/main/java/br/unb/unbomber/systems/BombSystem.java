package br.unb.unbomber.systems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.TimeOverEvent;

public class BombSystem extends BaseSystem {
	/* 
	documented by @author JeffVFA
	//Define default Value of a bomb  	*/ 
	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";

	//Define a set of processed events  
	Set<Event> processedEvents = new HashSet<Event>(500);   
	
	/* 
	bomb constructor 
	*/
	public BombSystem() {
		super();
	}
	
	/* 
	bomb constructor 
	@param model 	one instance of the EntityManager
	*/
	public BombSystem(EntityManager model) {
		super(model);
	}
	
	@Override 
	
	//Method for the updating of all bombs
	public void update() {
		//one instance of the EntityManage
		EntityManager entityManager = getEntityManager();
		
		//Get ActionCommandEvent events from the entityManager
		List<Event> actionEvents = entityManager.getEvents(ActionCommandEvent.class);
		//if exist any event :.
		if (actionEvents != null) {
			//for each event do:.
			for(Event event:actionEvents){
				ActionCommandEvent actionCommand = (ActionCommandEvent) event;
				//verify if is it a DROP_BOMB command
				if ((actionCommand.getType()== ActionType.DROP_BOMB) && (!processedEvents.contains(actionCommand))){
					BombDropper dropper = (BombDropper) entityManager.getComponent(BombDropper.class, 
							actionCommand.getEntityId());
					//if is it drop the bomb 
					verifyAndDropBomb(dropper); 
					// and add the event bomb drop to processedEvents set 
					processedEvents.add(actionCommand);
				}
			}
		}
		
		//Triggers active bombs whose timer expired
		List<Event> timerOvers = entityManager.getEvents(TimeOverEvent.class); 
		//Get TimeOverEvent events from the entityManager
		//if exist any event :.
		if (timerOvers != null) { 
			//for each event do:.
			for (Event event: timerOvers) {
				TimeOverEvent timeOver = (TimeOverEvent) event; 
				//verify if is it a TRIGGERED_BOMB_ACTION command
				if ((timeOver.getAction().equals(TRIGGERED_BOMB_ACTION)) && (!processedEvents.contains(timeOver))) {
					//if is it the explosion event starts
					createExplosionEvent(timeOver.getOwnerId()); 
					// and add the event time over to processedEvents set 
					processedEvents.add(timeOver);
				}
			}
		}
		
		// InAnExplosionEvent de bombas que estao no range de outras bombas
		// Bombas devem ser disparadas por efeito cascata 
		//Get InAnExplosionEvent events from the entityManager
		List<Event> inExplosionEvents = entityManager.getEvents(InAnExplosionEvent.class);
		//if exist any event :.
		if (inExplosionEvents != null) {
			//for each event do:.
			for(Event event:inExplosionEvents){
				InAnExplosionEvent inAnExplosion = (InAnExplosionEvent) event;
				// get the Id of the InAnExplosionEvent event
				int entityInExplosionId = inAnExplosion.getIdHit(); 
				//create a new explosive component and set it for any explosive component from the entityManager
				Explosive bombExplosive = (Explosive) entityManager.getComponent(Explosive.class, entityInExplosionId);
				Timer bombTimer = (Timer) entityManager.getComponent(Timer.class, entityInExplosionId);
				//if the explosive exists and its time isn't over and it is active 
				if ((bombExplosive != null) && (!bombTimer.isOver() && bombTimer.isActive())) {
					//call the method createExplosionEvent to start a new explosion
					createExplosionEvent(entityInExplosionId);
				} 
				// and add the event in an explosion to processedEvents set 
				processedEvents.add(inAnExplosion);
			}	
		}
	}
	
	/*  
	creates a createExplosionEvent event
	@param bombID	id of the event
	*/
	private void createExplosionEvent(int bombID) {

		EntityManager entityManager = getEntityManager(); // get the entityManager
		CellPlacement bombPlacement = (CellPlacement)
			entityManager.getComponent(CellPlacement.class, bombID); //get the bomb placement from the entityManager
		Explosive bombExplosive = (Explosive) entityManager.
			getComponent(Explosive.class, bombID); //get the bomb explosive from the entityManager
		Timer bombTimer = (Timer) entityManager.getComponent(Timer.class, bombID); // get the bomb timer from the entityManager 
		ExplosionStartedEvent explosion = new ExplosionStartedEvent(); //starts a new explosion
		explosion.setEventId(entityManager.getUniqueId()); //set the event ID on the entityManager
		explosion.setOwnerId(bombID); //set the ownerID to the bobID value
		explosion.setInitialPosition(bombPlacement); //set the initial position to the position got from the entityManager
		explosion.setExplosionRange(bombExplosive.getExplosionRange());//set bomb range to the range got from the explosive
		bombTimer.setActive(false); //desactivate the bomb - when it's active it explode!-
		entityManager.addEvent(explosion); //add this event to the entityManager
	}
	
	/**
	 * Drop a bomb
	 */
	public void verifyAndDropBomb(BombDropper dropper){
		
		// Counting the number of active bombs owner by the same dropper entity.
		List<Component> explosives = getEntityManager().getComponents(Explosive.class);
		int bombCounter = 0;
		if (explosives != null) {
			for (Component component: explosives) {
				Explosive explosive = (Explosive) component;
				if (explosive.getOnwnerId() == dropper.getOnwnerId()) {
					Timer timer = (Timer) getEntityManager().getComponent(Timer.class, explosive.getEntityId());
					// Should count only the active bombs
					if (!timer.isOver()) {
						bombCounter++;
					}
				}
			}
		}
		if (bombCounter < dropper.getPermittedSimultaneousBombs()) {
			Entity bomb = createTimeBomb(dropper);
			getEntityManager().update(bomb);
		}
				
		//TODO if it is a romete controlled bomb, 
		//make the link so the user can remote explod it
		
	}

	/**
	 * Make a Bomb
	 * @param dropper
	 */
	private Entity createTimeBomb(BombDropper dropper){
		 /*  The Bomb Entity is made of this components
		  * 	Explosive 
		  * 	Placement 
		  * 	Timer
		  * components */
		
		// find dropper placement
		CellPlacement dropperPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class,
				dropper.getEntityId());
		
		Entity bomb = getEntityManager().createEntity();
		
		
		//The bomb is owned by its dropper
		bomb.setOnwnerId(dropper.getEntityId());

		//Create the placement component
		CellPlacement bombPlacement = new CellPlacement();
		
		//the Bomb should have the same placement of its dropper
		bombPlacement.setCellX(dropperPlacement.getCellX());
		bombPlacement.setCellY(dropperPlacement.getCellY());
		
		//create explosive component
		Explosive bombExplosive = new Explosive();
		//the Bomb should have the same power of its dropper
		bombExplosive.setExplosionRange(dropper.getExplosionRange());

		//create Event for time over
		TimeOverEvent triggeredBombEvent = new TimeOverEvent(); 
		triggeredBombEvent.setAction(TRIGGERED_BOMB_ACTION);
		
		//create timer component
		Timer bombTimer = new Timer(90, triggeredBombEvent);			
		
		bomb.addComponent(bombExplosive);
		bomb.addComponent(bombPlacement);
		bomb.addComponent(bombTimer);
		
		
		return bomb;
		
	}



}
