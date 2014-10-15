/*
 * BombSystem
 *
 * Version 1
 *
 * UnB
 *
 * Handles the way bombs are treated in the Bomberman game.
 */

package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.TimeOverEvent;

public class BombSystem2 extends BaseSystem {
	
	
	/**
	 * instance variables of the class
	 */
	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";
	
	
	/**
	 * constructors of the class
	 * the second one receives a parameter of type EntityManager
	 */
	public BombSystem2() {
		super();
	}
	
	
	public BombSystem2 (EntityManager model) {
		super(model);
	}
	
	
	/**
	 * methods of the class
	 */
	@Override
	public void update() {
		
		/* get the ActionCommandEvent events */
		List<Event> actionEvents = getEntityManager().getEvents(ActionCommandEvent.class);
		
		/* for each event of the type */
		for (Event event : actionEvents) {
			
			ActionCommandEvent actionCommand = (ActionCommandEvent) event;
			
			/* verify if it is a DROP_BOMB command */
			if (actionCommand.getType() == ActionType.DROP_BOMB) {

				BombDropper dropper = (BombDropper) getEntityManager().getComponent(BombDropper.class, 
																					actionCommand.getEntityId());
				verifyAndDropBomb (dropper);				
			}
			
			else {
			
				/* verify if it is a EXPLODE_REMOTE_BOMB command */
				if (actionCommand.getType() == ActionType.EXPLODE_REMOTE_BOMB) {
					
					BombDropper dropper = (BombDropper) getEntityManager().getComponent(BombDropper.class, 
																						actionCommand.getEntityId());
					createRemoteBombExplosion (dropper);
						
				}
			
			}
			
		} 
		
		/* verify TimeOutEvent of bombs that have to explode in this turn */
		List<Component> explosivesInGame = getEntityManager().getComponents(Explosive.class);
		
		/* for each components of the type */
		for (Component component : explosivesInGame) {
			
			Explosive bombExplosive = (Explosive) component;
			
			/* get the id of the bomb belonging to this Explosive component */ 
			int bombID = bombExplosive.getEntityId();
			
			/* getting the Timer component from the bomb with ID == bombID */
			Timer bombtimer = (Timer) getEntityManager().getComponent(Timer.class, bombID);
			
			/* getting the initial bomb position */
			CellPlacement bombPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, bombID);
			
			/* getting the explosive component to obtain the bombPower */
			Explosive bombPower = (Explosive) getEntityManager().getComponent(Explosive.class, bombID);		
			
			/* the condition bellow is enough, because the exploded bombs are already removed by the entity manager */
			if (bombtimer.isOver()) {
				
				/* a new event (ExplosionStartedEvent) is created for the time to explode */				
				ExplosionStartedEvent explodeNow = new ExplosionStartedEvent();
				explodeNow.setInitialPosition(bombPlacement);
				explodeNow.setExplosionRange(bombPower.getExplosionRange());
				
				/* adding the event to the entity manager */
				getEntityManager().addEvent(explodeNow);
				
			}
			
		}
		
		/*verify InAnExplosionEvent of bombs that are in the same range of other bombs.
		  these bombs have to explode together in a cascade reaction*/
		List<Event> InAnExplosionEvents = getEntityManager().getEvents(InAnExplosionEvent.class);
		
		/*for each event of the type*/
		for (Event event : InAnExplosionEvents) {
			
			InAnExplosionEvent inAnExpEv = (InAnExplosionEvent) event;
			
			/*get the id of the bomb to explode*/
			int idBomb = inAnExpEv.getIdHit();
			
			/*get the placement (component) of the bomb from the entity manager*/
			CellPlacement bombPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class, idBomb);
			
			/*get the explosive (component) of the bomb from the entity manager*/
			Explosive bombExplosive = (Explosive) getEntityManager().getComponent(Explosive.class, idBomb);
			
			/*create the event to be added*/
			ExplosionStartedEvent explosionStartedEv = new ExplosionStartedEvent();
			explosionStartedEv.setInitialPosition(bombPlacement); 
			explosionStartedEv.setExplosionRange(bombExplosive.getExplosionRange());
			
			getEntityManager().addEvent(explosionStartedEv);
					
		}
		
	}		//end of bombSystem update
	
	/**
	 * method to drop a bomb; but first we verify if the character has not dropped too many bombs.
	 */
	public void verifyAndDropBomb (BombDropper dropper) {
		
		/* get explosive components from entity manager */
		List<Component> explosivesInGame = (List<Component>) getEntityManager().getComponents(Explosive.class);
		
		int numberOfBombsDroppedByDropper = 0; // bomb counter
		
		/* check if there are bombs in game */
		if (explosivesInGame != null) {
			
			/* check every bomb in game */
			for (Component component : explosivesInGame) {
					
				Explosive bombInGame = (Explosive) component;
				
				/* check if the current bomb was dropped by the current dropper */
				if (bombInGame.getOnwnerId() == dropper.getOnwnerId()) {
					
					/* consider only the bombs that are still ticking */
					Timer bombTimer = (Timer) getEntityManager().getComponent(Timer.class, bombInGame.getEntityId()); 
					
					/* if the timer is higher than zero, the bomb is still ticking. increment the bomb counter */
					if (!bombTimer.isOver()) {
						numberOfBombsDroppedByDropper++;
		
					}
					
				}
				
			}
			
		} 
		
		/* if the dropper has not dropped too many bombs, it drops one more bomb */
		if (dropper.getPermittedSimultaneousBombs() > numberOfBombsDroppedByDropper) {
				
			Entity bomb = createTimeBomb(dropper);	// creating the new bomb entity 
			getEntityManager().addEntity(bomb); 	// adding the new bomb to the entity manager
			
		}
		
	}		// end of verifyAndDropBomb method
	
	
	/**
	 * method to explode a remote bomb when the player decides to
	 */
	public void createRemoteBombExplosion (BombDropper dropper) {
		
		List<Component> explosivesInGame = (List<Component>) getEntityManager().getComponents(Explosive.class);
		
		/* check if there are bombs in game */
		if (explosivesInGame != null) {
			
			/* check every bomb in game */
			for (Component component : explosivesInGame){
					
				Explosive bombInGame = (Explosive) component;
				
				/* check if the current bomb was dropped by the current dropper */
				if (bombInGame.getOnwnerId() == dropper.getOnwnerId()) {
					
					/* a new event (ExplosionStartedEvent) is created for the time to explode */				
					ExplosionStartedEvent explodeRemoteBombNow = new ExplosionStartedEvent();
					
					int bombID = bombInGame.getEntityId();
					
					/* get the initial bomb position */
					CellPlacement bombPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class,
																								  bombID);
					
					/* get the explosive component to obtain the bombPower */
					Explosive bombPower = (Explosive) getEntityManager().getComponent(Explosive.class, bombID);	
					
					/* set the initial position and power to the event */
					explodeRemoteBombNow.setInitialPosition(bombPlacement);		
					explodeRemoteBombNow.setExplosionRange(bombPower.getExplosionRange());
					
					/* add the event to the entity manager */
					getEntityManager().addEvent(explodeRemoteBombNow);
					
					/* stops searching for the remote controlled bomb */
					break;
				} 
			
			}
			
		}
		
	}		// end of createRemoteBombExplosion method
	
	/**
	 * method to create a bomb (time and remote). 
	 * a bomb is made of three components: Explosive, Placement and Timer.
	 */
	private Entity createTimeBomb (BombDropper dropper) {
		
		/* find dropper placement */
		CellPlacement dropperPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class,
				dropper.getEntityId());
		
		Entity bomb = new Entity();
		
		/* the bomb is owned by its dropper */
		bomb.setOnwnerId(dropper.getEntityId());

		/* create the placement component */
		CellPlacement bombPlacement = new CellPlacement();
		
		/* the bomb has the same placement of its dropper */
		bombPlacement.setCellX(dropperPlacement.getCellX());
		bombPlacement.setCellY(dropperPlacement.getCellY());
		bombPlacement.setEntityId(bomb.getEntityId());		//add the bomb id to the component bombPlacement
		
		/* create explosive component */
		Explosive bombExplosive = new Explosive();
		
		/*the bomb should have the same power of its dropper */
		bombExplosive.setExplosionRange(dropper.getExplosionRange());
		bombExplosive.setOnwnerId(bomb.getEntityId());		//add the bomb id to the component bombExplosive

		/* create an event for time over */
		TimeOverEvent triggeredBombEvent = new TimeOverEvent(); 
		triggeredBombEvent.setAction(TRIGGERED_BOMB_ACTION);
		
		/* create a timer component */
		Timer bombTimer = new Timer(90, triggeredBombEvent);
		
		/* if the dropper is allowed to remote trigger bombs, create different timer */
		if (dropper.isCanRemoteTrigger() == true) {
			
			/* setting a high timer so that the bomb will never explode during the game */
			bombTimer = new Timer(9999999, triggeredBombEvent);
		
		}
		
		/* adding the components to the bomb entity */
		bomb.addComponent(bombExplosive);
		bomb.addComponent(bombPlacement);
		bomb.addComponent(bombTimer);
		
		return bomb;
		
	}    	//end of createTimeBomb method
	
}		//end of BombSystem	