/*
 * BombSystem
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

package br.unb.unbomber.systems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Draw;
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

/**
 * @author <img src="https://avatars2.githubusercontent.com/u/8586137?v=2&s=30" width="30" height="30"/> <a href="https://github.com/JeffVFA" target="_blank">JeffVFA</a>
 * @author <img src="https://avatars1.githubusercontent.com/u/4968411?v=2&s=30" width="30" height="30"/> <a href="https://github.com/zidenis" target="_blank"> zidenis </a>
 * @author <img src="https://avatars2.githubusercontent.com/u/3778188?v=2&s=30" width="30" height="30"/> <a href="https://github.com/DRA2840" target="_blank"> DRA2840 </a>
 * @author <img src="https://avatars2.githubusercontent.com/u/7716247?v=2&s=30" width="30" height="30"/> <a href="https://github.com/brenoxp2008" target="_blank"> brenoxp2008</a>
 */

public class BombSystem extends BaseSystem {
	/*
	 * documented by @author JeffVFA //Define default Value of a bomb
	 */
	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";

	// Define a set of processed events
	Set<Event> processedEvents = new HashSet<Event>(500);

	/**
	 * bomb constructor
	 */
	public BombSystem() {
		super();
	}

	/**
	 * bomb constructor
	 * 
	 * @param model one instance of the EntityManager
	 */
	public BombSystem(EntityManager model) {
		super(model);
	}



	/**
	 * Method for the updating of all bombs.
	 * This method is called every turn. It checks if there is any 
	 * {@link Event} related to this module and process them.
	 */
	public void update() {
		// one instance of the EntityManage
		EntityManager entityManager = getEntityManager();

		/*
		 * For each different type of Event, do:
		 * 1) iterate over all events of that type
		 * 2) Verify if this event interests me and if it wasn't already processed
		 * 3) process the event
		 * 4) add event to processed list
		 * 
		 */

		// Events of the type Action Command (drops bomb & triggers remote bomb)
		// 1)
		List<Event> actionEvents = entityManager
				.getEvents(ActionCommandEvent.class);
		for (Event event : actionEvents) {
			ActionCommandEvent actionCommand = (ActionCommandEvent) event;

			// 2) 
			if ((actionCommand.getType() == ActionType.DROP_BOMB)
					&& (!processedEvents.contains(actionCommand))) {

				// 3)
				BombDropper dropper = (BombDropper) entityManager.getComponent(BombDropper.class, actionCommand.getEntityId());
				verifyAndDropBomb(dropper);

				// 4)
				processedEvents.add(actionCommand);

				// 2) 
			}else if ((actionCommand.getType() == ActionType.TRIGGERS_REMOTE_BOMB)
					&& (!processedEvents.contains(actionCommand))) {

				// 3)
				List<Component> components =  entityManager.getComponents(Explosive.class);
				for (Component component: components) {
					Explosive explosive = (Explosive) component;
					if (explosive.getOwnerId() == actionCommand.getEntityId()) {
						createExplosionEvent(explosive.getEntityId());
					}
				}

				// 4)
				processedEvents.add(actionCommand);
			}
		}

		// Events of the type Time Out (Triggers bomb)
		// 1)
		List<Event> timerOvers = entityManager.getEvents(TimeOverEvent.class);
		for (Event event : timerOvers) {
			TimeOverEvent timeOver = (TimeOverEvent) event;

			// 2)
			if ((timeOver.getAction().equals(TRIGGERED_BOMB_ACTION))
					&& (!processedEvents.contains(timeOver))) {

				// 3)
				createExplosionEvent(timeOver.getOwnerId());

				// 4)
				processedEvents.add(timeOver);
			}
		}

		// Event of the type InAnExplosionEvent (Bombs in the range of exploding bombs 
		// should explode as well)
		// 1)
		List<Event> inExplosionEvents = entityManager
				.getEvents(InAnExplosionEvent.class);
		for (Event event : inExplosionEvents) {
			InAnExplosionEvent inAnExplosion = (InAnExplosionEvent) event;

			// 2)
			if (!processedEvents.contains(inAnExplosion)) {

				// 3)
				int entityInExplosionId = inAnExplosion.getIdHit();
				Explosive bombExplosive = (Explosive) entityManager.getComponent(Explosive.class, entityInExplosionId);

				if (bombExplosive != null) {
					createExplosionEvent(entityInExplosionId);
				}
				// 4)
				processedEvents.add(inAnExplosion);
			}
		}
	}

	/**
	 * creates a createExplosionEvent event
	 * 
	 * @param bombID id of the event
	 */
	private void createExplosionEvent(int bombID) {

		/*
		 * 1) Gets the  placement and the explosive from Entity Manager
		 * 2) Starts a new explosion
		 * 3) Set explosion atributes
		 * 4) Adds explosion event to Entity Manager
		 */

		// 1)
		EntityManager entityManager = getEntityManager(); 
		CellPlacement bombPlacement = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, bombID);
		Explosive bombExplosive = (Explosive) entityManager.getComponent(
				Explosive.class, bombID); 

		// 2)
		ExplosionStartedEvent explosion = new ExplosionStartedEvent(); 

		// 3)
		explosion.setEventId(entityManager.getUniqueId());
		explosion.setOwnerId(bombExplosive.getOwnerId()); //The Explosion owner is the bomb ownwer
		explosion.setInitialPosition(bombPlacement); 
		explosion.setExplosionRange(bombExplosive.getExplosionRange());

		// 4)
		entityManager.addEvent(explosion);
	}

	/**
	 * Drop a bomb if dropper didn't reach the limit
	 * @param dropper {@link BombDropper} that wants to drop a bomb
	 */
	public final void verifyAndDropBomb(BombDropper dropper) {

		// Counting the number of active bombs owner by the same dropper entity.
		List<Component> explosives = getEntityManager().getComponents(
				Explosive.class);
		int bombCounter = 0;
		if (explosives != null) {
			for (Component component : explosives) {
				Explosive explosive = (Explosive) component;
				if (explosive.getOwnerId() == dropper.getEntityId()) {

					bombCounter++;

				}
			}
		}
		if (bombCounter < dropper.getPermittedSimultaneousBombs()) {

			Entity bomb = null;

			if (dropper.isCanRemoteTrigger()) {

				bomb = createRemoteBomb(dropper);

			} else {

				bomb = createTimeBomb(dropper);

			}
			getEntityManager().update(bomb);
		}

	}

	private Entity createRemoteBomb(BombDropper dropper) {
		/*
		 * The Bomb Entity is made of this components Explosive Placement Timer
		 * components
		 */

		Entity bomb = createGenericBomb(dropper);

		//TODO What else does a Remotely controled bomb needs?

		return bomb;
	}

	/**
	 * Create a timed bomb
	 * 
	 * @param dropper {@link BombDropper} that will drop a bomb
	 * @return new Bomb
	 */
	private Entity createTimeBomb(final BombDropper dropper) {
		/*
		 * The Bomb Entity is made of this components Explosive Placement Timer
		 * components
		 */

		Entity bomb = createGenericBomb(dropper);

		// create Event for time over
		TimeOverEvent triggeredBombEvent = new TimeOverEvent();
		triggeredBombEvent.setAction(TRIGGERED_BOMB_ACTION);
		// create timer component
		Timer bombTimer = new Timer(90, triggeredBombEvent);

		// Add component
		bomb.addComponent(bombTimer);

		return bomb;

	}

	//Method created to avoid code duplication
	Entity createGenericBomb(BombDropper dropper){
		/*
		 * The Bomb Entity is made of this components Explosive Placement Timer
		 * components
		 */

		// find dropper placement
		CellPlacement dropperPlacement = (CellPlacement) getEntityManager()
				.getComponent(CellPlacement.class, dropper.getEntityId());

		Entity bomb = getEntityManager().createEntity();

		// The bomb is owned by its dropper
		bomb.setOwnerId(dropper.getEntityId());

		// Create the placement component
		CellPlacement bombPlacement = new CellPlacement();

		// the Bomb should have the same placement of its dropper
		bombPlacement.setCellX(dropperPlacement.getCellX());
		bombPlacement.setCellY(dropperPlacement.getCellY());

		// create explosive component
		Explosive bombExplosive = new Explosive();
		// the Bomb should have the same power of its dropper
		bombExplosive.setExplosionRange(dropper.getExplosionRange());
		bombExplosive.setOwnerId(dropper.getEntityId());

		// Add components
		bomb.addComponent(bombExplosive);
		bomb.addComponent(bombPlacement);
		bomb.addComponent(new Draw("bomb"));
		return bomb;
	}

}
