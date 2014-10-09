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

	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";
	
	Set<Event> processedEvents = new HashSet<Event>(500);   
	
	public BombSystem() {
		super();
	}
	
	public BombSystem(EntityManager model) {
		super(model);
		
	}
	
	@Override
	public void update() {
		
		EntityManager entityManager = getEntityManager();
		//Get ActionCommandEvent events
		List<Event> actionEvents = entityManager.getEvents(ActionCommandEvent.class);
		
		if (actionEvents != null) {
			for(Event event:actionEvents){
				ActionCommandEvent actionCommand = (ActionCommandEvent) event;
				//verify if is it a DROP_BOMB command
				if ((actionCommand.getType()== ActionType.DROP_BOMB) && (!processedEvents.contains(actionCommand))){
	
					BombDropper dropper = (BombDropper) entityManager.getComponent(BombDropper.class, 
							actionCommand.getEntityId());
					verifyAndDropBomb(dropper);
					processedEvents.add(actionCommand);
				}
			}
		}
		
		//Triggers active bombs whose timer expired
		List<Event> timerOvers = entityManager.getEvents(TimeOverEvent.class);
		if (timerOvers != null) {
			for (Event event: timerOvers) {
				TimeOverEvent timeOver = (TimeOverEvent) event;
				if ((timeOver.getAction().equals(TRIGGERED_BOMB_ACTION)) && (!processedEvents.contains(timeOver))) {

					createExplosionEvent(timeOver.getOwnerId());
					processedEvents.add(timeOver);
				}
			}
		}
		
		// verificar InAnExplosionEvent de bombas que estao no range de outras bombas
		// e devem ser disparadas por efeito cascata
		List<Event> inExplosionEvents = entityManager.getEvents(InAnExplosionEvent.class);
		
		if (inExplosionEvents != null) {
			for(Event event:inExplosionEvents){
				InAnExplosionEvent explosionEvent = (InAnExplosionEvent) event;
				processedEvents.add(explosionEvent);
				
				createExplosionEvent(explosionEvent.getOwnerId());
			}	
		}
	}
	
	private void createExplosionEvent(int bombID){
		
		EntityManager entityManager = getEntityManager();
		
		CellPlacement bombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, bombID);
		Explosive bombExplosive = (Explosive) entityManager.getComponent(Explosive.class, bombID);
		ExplosionStartedEvent explosion = new ExplosionStartedEvent();
		explosion.setEventId(entityManager.getUniqueId());
		explosion.setOwnerId(bombID);
		explosion.setInitialPosition(bombPlacement);
		explosion.setExplosionRange(bombExplosive.getExplosionRange());
		entityManager.addEvent(explosion);
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
			getEntityManager().addEntity(bomb);
		}
				
		//TODO if it is a romete controlled bomb, 
		//make the link so the user can remote explod it
		
	}

//	public void verifyAndDropBomb(BombDropper dropper){
//		
//		//TODO verify if the character has not dropped too much bombs
//		
//		Entity bomb = createTimeBomb(dropper);
//		getEntityManager().addEntity(bomb);
//		
//		//TODO if it is a romete controlled bomb, 
//		//make the link so the user can remote explod it
//		
//	}
	
	/**
	 * Make a Bomb
	 * 
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
		
		Entity bomb = new Entity();
		
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
