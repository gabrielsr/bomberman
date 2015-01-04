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

import java.util.UUID;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.FIFORemoteControl;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.TimeOverEvent;
import br.unb.unbomber.misc.EntityBuilder2;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.GroupManager;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;

/**
 * @author <img src="https://avatars2.githubusercontent.com/u/8586137?v=2&s=30" width="30" height="30"/> <a href="https://github.com/JeffVFA" target="_blank">JeffVFA</a>
 * @author <img src="https://avatars1.githubusercontent.com/u/4968411?v=2&s=30" width="30" height="30"/> <a href="https://github.com/zidenis" target="_blank"> zidenis </a>
 * @author <img src="https://avatars2.githubusercontent.com/u/3778188?v=2&s=30" width="30" height="30"/> <a href="https://github.com/DRA2840" target="_blank"> DRA2840 </a>
 * @author <img src="https://avatars2.githubusercontent.com/u/7716247?v=2&s=30" width="30" height="30"/> <a href="https://github.com/brenoxp2008" target="_blank"> brenoxp2008</a>
 */

@Wire
public class BombSystem extends VoidEntitySystem {
	/*
	 * documented by @author JeffVFA //Define default Value of a bomb
	 */
	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";
	
	ComponentMapper<BombDropper> cmBombDropper;

	ComponentMapper<Position> cmPosition;
	
	ComponentMapper<Explosive> cmExplosive;
	
	ComponentMapper<FIFORemoteControl> emFIFORemoteControl;
	
	UuidEntityManager uuidEm;
	
	EventManager em;
	
	@Override
	protected void processSystem() {
	}

	/**
	 * Handle Events of the type Time Out (Triggers bomb).
	 * 
	 * @param actionCommand
	 */
	@Subscribe
	public void handle(TimeOverEvent<UUID> timeOver){
		if (timeOver.getAction().equals(TRIGGERED_BOMB_ACTION)) {
			createExplosionEvent(timeOver.getPayload());
		}
	}

	
	/**
	 * Handle Events of the type Action Command (drops bomb & triggers remote bomb).
	 * 
	 * @param actionCommand
	 */
	@Subscribe
	public void handle(ActionCommandEvent actionCommand){
		Entity dropperEntity = world.getManager(UuidEntityManager.class).getEntity(actionCommand.getEntityUuid());
		
		if (actionCommand.getType() == ActionType.DROP_BOMB) {
			
			verifyAndDropBomb(dropperEntity);
			
		}else if (actionCommand.getType() == ActionType.TRIGGERS_REMOTE_BOMB) {
			trigRemoteBomb(dropperEntity);
		}
	}
	
	public void trigRemoteBomb(Entity dropperEntity ){

		FIFORemoteControl remoteControl = emFIFORemoteControl.getSafe(dropperEntity);
		if(remoteControl!=null){
			UUID toTrigBombUUID = remoteControl.next();
			createExplosionEvent(toTrigBombUUID);
		}
	}

	/**
	 * Handle Events of the type Action Command (drops bomb & triggers remote bomb).
	 * 
	 * @param actionCommand
	 */
	@Subscribe
	public void handle(InAnExplosionEvent inAnExplosion){
		// Event of the type InAnExplosionEvent (Bombs in the range of exploding bombs 
		// should explode as well)
		UUID entityInExplosionId = inAnExplosion.getIdHit();
		
		Entity hitEntity = uuidEm.getEntity(entityInExplosionId);
		
		//probably already handled
		if(hitEntity==null){
			return;
		}
		
		/** if the hit entity is an explosive, create an explosion */
		Explosive explosive = cmExplosive.getSafe(hitEntity);
		if (explosive != null) {
			createExplosionEvent(entityInExplosionId);
		}
	}

	/**
	 * creates a createExplosionEvent event
	 * 
	 * @param bombID id of the event
	 */
	private void createExplosionEvent(UUID bombID) {


		/*
		 * 1) Gets the  placement and the explosive
		 * 2) Starts a new explosion
		 * 3) Set explosion atributes
		 * 4) Adds explosion event to Event Manager
		 */
		
		// 1)		
		Entity bombEntity = uuidEm.getEntity(bombID);
		Position bombPlacement = cmPosition.get(bombEntity);
		Explosive bombExplosive = cmExplosive.get(bombEntity); 
		
		// 2)
		ExplosionStartedEvent explosion = new ExplosionStartedEvent(); 
		
		// 3)
		explosion.setCreatorUUID(bombExplosive.getCreatorUUID()); //The Explosion owner is the bomb ownwer
		explosion.setInitialPosition(bombPlacement.getIndex()); 
		explosion.setExplosionRange(bombExplosive.getExplosionRange());
		
		// 4)
		bombEntity.deleteFromWorld();
		
		em.dispatch(explosion);
	}

	/**
	 * Drop a bomb if dropper didn't reach the limit
	 * @param dropperEntity {@link BombDropper} that wants to drop a bomb
	 */
	public final void verifyAndDropBomb(Entity dropperEntity) {		
		BombDropper dropperComponent = cmBombDropper.get(dropperEntity);
		
		if (canDropMoreBombs(dropperComponent, dropperEntity.getUuid())) {	
			
			dropBomb(dropperEntity, dropperComponent);
		}
	}

	public void dropBomb(Entity dropperEntity, BombDropper dropperComponent){
		// find dropper placement

		if (dropperComponent.isCanRemoteTrigger()) {
			createRemoteBomb(dropperEntity);
		}else{
			createTimeBomb(dropperEntity);
		}
	}
			
	private Entity createRemoteBomb(Entity dropper) {
	
		Entity bomb = createGenericBomb(dropper);
		
		//include it in the remote control queue
		FIFORemoteControl remoteControl = emFIFORemoteControl.getSafe(dropper);
		remoteControl.include(bomb.getUuid());

		return bomb;
	}

	/**
	 * Create a timed bomb
	 * 
	 * @param dropper {@link BombDropper} that will drop a bomb
	 * @return new Bomb
	 */
	private Entity createTimeBomb(final Entity dropper) {
		/*
		 * The Bomb Entity is made of this components Explosive Placement Timer
		 * components
		 */

		Entity bomb = createGenericBomb(dropper);

		// create Event for time over
		TimeOverEvent<UUID> triggeredBombEvent
			= new TimeOverEvent<UUID>(TRIGGERED_BOMB_ACTION, bomb.getUuid());
		// create timer component
		Timer bombTimer = new Timer(90, triggeredBombEvent);

		// Add component
		bomb.edit().add(bombTimer);		
		return bomb;
	}
	
	//Method created to avoid code duplication
	Entity createGenericBomb(Entity dropperEntity){
		/*
		 * The Bomb Entity is made of this components Explosive Placement Timer
		 * components
		 */

		Position dropperPlacement = cmPosition.get(dropperEntity);
		BombDropper dropperComponent = cmBombDropper.get(dropperEntity);
		UUID creatorUUID = dropperEntity.getUuid();
		
		// Create the placement component
		Position bombPlacement = new Position();
		// the Bomb should have the same placement of its dropper
		bombPlacement.setCellX(dropperPlacement.getCellX());
		bombPlacement.setCellY(dropperPlacement.getCellY());

		// create explosive component
		Explosive bombExplosive = new Explosive();
		// the Bomb should have the same power of its dropper
		bombExplosive.setExplosionRange(dropperComponent.getExplosionRange());
		bombExplosive.setOwnerId(creatorUUID);
			
		Entity bomb = EntityBuilder2.create(world)
			.withDraw("bomb")
			.withMovementBarrier(MovementBarrierType.BLOCKER)
			.with(bombPlacement)
			.with(bombExplosive)
			.build();

		//TAG the bomb with the Dropper UUID
		world.getManager(GroupManager.class).add(bomb,
				dropperEntity.getUuid().toString());
		
		return bomb;
	}
	
	/**
	 * Check it the entity can drop more bombs.
	 * 
	 * Count the number of active bombs owner by the same dropper entity.
	 * 
	 * @param dropperEntity
	 * @return can drop
	 */
	public boolean canDropMoreBombs(BombDropper dropperComponent, UUID dropperUUID){

		// Counting the number of active bombs owner by the same dropper entity.

		int permittedSimultaneousBombs = dropperComponent.getPermittedSimultaneousBombs();
		
		ImmutableBag<Entity> inWorldBombs = world.getManager(GroupManager.class).getEntities(
				dropperUUID.toString());
		
		//TODO check if already there is a bomb at the same place.
		if(inWorldBombs.size() < permittedSimultaneousBombs){
			return true;
		}else{
			return false;
		}
	}
}
