package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.TimeOverEvent;

public class BombSystem extends BaseSystem {

	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";
	

	public BombSystem() {
		super();
	}
	
	public BombSystem(EntityManager model) {
		super(model);
		
	}
	
	@Override
	public void update() {
		
		//Get ActionCommandEvent events
		List<Event> actionEvents = getEntityManager().getEvents(ActionCommandEvent.class);
		
		for(Event event:actionEvents){
			ActionCommandEvent actionCommand = (ActionCommandEvent) event;
			//verify if is it a DROP_BOMB command
			if(actionCommand.getType()== ActionType.DROP_BOMB){

				BombDropper dropper = (BombDropper) getEntityManager().getComponent(BombDropper.class, 
						actionCommand.getEntityId());
				verifyAndDropBomb(dropper);				
			}
			
			//verify if is it a EXPLODE_REMOTE_BOMB command
			if(actionCommand.getType()== ActionType.EXPLODE_REMOTE_BOMB){
				
				BombDropper dropper = (BombDropper) getEntityManager().getComponent(BombDropper.class, 
						actionCommand.getEntityId());
				
				createRemoteBombExplosion(dropper);
					
			} 
		}
		
		//Verify TimeOutEvent of bombs that have to explode in this turn
		List<Component>explosivesInGame = getEntityManager().getComponents(Explosive.class);
				
		for(Component component : explosivesInGame ){
			
			Explosive bombExplosive = (Explosive) component;
			
			//get the ID of the bomb belonging to this Explosive component 
			int bombID = bombExplosive.getEntityId();
			
			//getting the Timer component from the bomb with ID == bombID
			Timer bombtimer = (Timer) getEntityManager().getComponent(Timer.class, bombID);
			//getting the initial bomb position
			CellPlacement bombPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, bombID);
			
			//getting the explosive component to obtain the bombPower
			Explosive bombPower = (Explosive) getEntityManager().getComponent(Explosive.class, bombID);		
			
			//the condition bellow is enough, because the exploded bombs are already removed by the entity manager
			if( bombtimer.isOver() ){
				
				//a new event (ExplosionStartedEvent) is created for the time to explode				
				ExplosionStartedEvent explodeNow = new ExplosionStartedEvent();
				explodeNow.setInitialPosition(bombPlacement);
				explodeNow.setPower(bombPower.getPower());
				
				//adding the event to the entity manager
				getEntityManager().addEvent(explodeNow);
			}
		}
		
		//Verify InAnExplosionEvent of bombs that are in the same range of other bombs
		//those bombs have to explode together in a cascade reaction.
		//GETTING THE InAnExplosionEvents EVENTS
		List<Event> InAnExplosionEvents = getEntityManager().getEvents(InAnExplosionEvent.class);
		
		//FOR EACH EVENT OF THE TYPE
		for (Event event:InAnExplosionEvents) {
			
			InAnExplosionEvent inAnExpEv = (InAnExplosionEvent) event;
			
			//GETTING THE ID OF THE BOMB TO EXPLODE
			int idBomb = inAnExpEv.getIdHit();
			
			//GETTING THE PLACEMENT (COMPONENT) OF THE BOMB FROM THE ENTITY MANAGER
			CellPlacement bombPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, idBomb);
			
			//GETTING THE EXPLOSIVE (COMPONENT) OF THE BOMB FROM THE ENTITY MANAGER
			Explosive bombExplosive = (Explosive) getEntityManager().getComponent(Explosive.class, idBomb);
			
			//CREATING THE EVENT TO BE ADDED
			ExplosionStartedEvent explosionStartedEv = new ExplosionStartedEvent();
			explosionStartedEv.setInitialPosition(bombPlacement);
			explosionStartedEv.setPower(bombExplosive.getPower());
			
			getEntityManager().addEvent(explosionStartedEv);
					
		}
	} //end of bombSystem update
	
	/**
	 * Drop a bomb
	 */
	public void verifyAndDropBomb(BombDropper dropper){
		
		//verify if the character has not dropped too many bombs
		//Get bomb entities from entity manager
		List<Component> explosivesInGame = (List<Component>) getEntityManager().getComponents(Explosive.class);
		
		int numberOfBombsDroppedByDropper = 0; // bomb counter
		
		// Checks if there are bombs in game
		if (explosivesInGame != null){
			// we will now check every bomb in the game
			for (Component component : explosivesInGame){
					
				Explosive bombInGame = (Explosive) component;
				
				// check if the current bomb was dropped by the current dropper
				if(bombInGame.getOnwnerId() == dropper.getOnwnerId()){
					// we have to consider only the bombs that are still ticking
					// with this declaration we are saving the time component from the current bomb.
					Timer bombTimer = (Timer) getEntityManager().getComponent(Timer.class, bombInGame.getEntityId()); 
					
					// if the timer is higher than 0, the bomb is still ticking. We have to increment the bomb counter.
					if(!bombTimer.isOver()){
						numberOfBombsDroppedByDropper++;
					}
				}
			}
		}
		
		// if dropper has not dropped too many bombs, it is allowed to drop one more bomb.
		if (dropper.getPermittedSimultaneousBombs() > numberOfBombsDroppedByDropper) {
			
			//if the dropper is allowed to drop remote controlled bombs we will create the link
			//so it can remote explode it
			if(dropper.isCanRemoteTrigger() == true){
				//xx
				
			}
			
			else {
				Entity bomb = createTimeBomb(dropper);
				getEntityManager().addEntity(bomb);
			}
		}
		
	} // end of verify and drop bomb method
	
		
	public void createRemoteBombExplosion(BombDropper dropper){
		
		List<Component> explosivesInGame = (List<Component>) getEntityManager().getComponents(Explosive.class);
		
		// Checks if there are bombs in game
		if (explosivesInGame != null){
			// we will now check every bomb in the game
			for (Component component : explosivesInGame){
					
				Explosive bombInGame = (Explosive) component;
				
				// check if the current bomb was dropped by the current dropper
				if(bombInGame.getOnwnerId() == dropper.getOnwnerId()){
					//it was, we will now create the ExplosionStartedEvent
					//a new event (ExplosionStartedEvent) is created for the time to explode				
					ExplosionStartedEvent explodeRemoteBombNow = new ExplosionStartedEvent();
					
					int bombID = bombInGame.getEntityId();
					
					//getting the initial bomb position
					CellPlacement bombPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, bombID);
					
					//getting the explosive component to obtain the bombPower
					Explosive bombPower = (Explosive) getEntityManager().getComponent(Explosive.class, bombID);	
					
					explodeRemoteBombNow.setInitialPosition(bombPlacement);
					explodeRemoteBombNow.setPower(bombPower.getPower());
					
					//adding the event to the entity manager
					getEntityManager().addEvent(explodeRemoteBombNow);
				} 
			}
		}
	}
	
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
		bombPlacement.setEntityId(bomb.getEntityId()); //adding the bomb id to the component bombPlacement
		
		//create explosive component
		Explosive bombExplosive = new Explosive();
		//the Bomb should have the same power of its dropper
		bombExplosive.setPower(dropper.getBombRange());
		bombExplosive.setOnwnerId(bomb.getEntityId());	//adding the bomb id to the component bombExplosive

		//create Event for time over
		TimeOverEvent triggeredBombEvent = new TimeOverEvent(); 
		triggeredBombEvent.setAction(TRIGGERED_BOMB_ACTION);
		
		//create timer component considering that the bomb can be remote controlled
		//it shouldn't explode until the character decides to explode it
		Timer bombTimer = new Timer(90, triggeredBombEvent);
		
		if(dropper.isCanRemoteTrigger() == true){
			//setting a high timer so that the bomb will never explode during the game
			bombTimer = new Timer(9999999, triggeredBombEvent);
		}
		
		//adding the components to the bomb entity
		bomb.addComponent(bombExplosive);
		bomb.addComponent(bombPlacement);
		bomb.addComponent(bombTimer);
		
		return bomb;
		
	}
	
}
