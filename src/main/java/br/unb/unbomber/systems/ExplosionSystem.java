package br.unb.unbomber.systems;

import java.util.List;
import java.util.UUID;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.TimeOverEvent;
import br.unb.unbomber.misc.EntityBuilder2;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.ImmutableBag;

@Wire
public class ExplosionSystem extends EntitySystem {


	ComponentMapper<Explosion> cmExplosion;
	
	ComponentMapper<Position> cmPosition;
	
	ComponentMapper<ExplosionBarrier> cmExplosionBarrier;
	
	GridSystem gridSystem;
	
	EventManager em;
	
	public static final String EXPIRED_EXPLOSION = "expired_explosion";
	
	public ExplosionSystem() {
		super(Aspect.getAspectForAll(Explosion.class,Position.class));
	}

	@Override
	public void processEntities(ImmutableBag<Entity> entities) {
		
		for (Entity entity : entities) {
			propagateAnExplosion(entity);
			checkEntitiesInExplosion(entity);
		}
	}
	

	/**
	 * Handle an explosion propagation
	 * 
	 * Propagate.
	 * @param explosionEntity
	 */
	private void propagateAnExplosion(Entity explosionEntity) {
		Explosion exp = cmExplosion.get(explosionEntity);
		
		/** finish of propagation */
		if(!exp.isShouldPropagate() || exp.getExplosionRange() <= 0 ){
			return;
		}
		
		int newRange = exp.getExplosionRange() - 1;
		
		Position position = cmPosition.get(explosionEntity);
		Vector2D<Integer> cellIndex = position.getIndex();
		
		
		if(exp.isCenterOfExplosion()){
			/* the center propagate in the four directions */
			for(Direction direction: Direction.values()){
				Vector2D<Integer> destination = cellIndex.add(direction.asVector());
				
				tryPropagateTo(destination, 
						newRange, exp.getOwnerUuid(), false, direction);
					
			}
		}else{
			/* propagate in a specific directions */			
			tryPropagateTo(cellIndex.add(exp.getPropagationDirection().asVector()), 
					newRange, exp.getOwnerUuid(), false, exp.getPropagationDirection());
		}
		
		exp.setShouldPropagate(false);
	}

	private void tryPropagateTo(Vector2D<Integer> cellIndex, int expRange,
			UUID explosionCauseUUID, boolean isCenter, Direction direction) {
		
		boolean canPropagate = checkIfCanPropagateTo(cellIndex, explosionCauseUUID);
		
		if(canPropagate){
			createExplosion(cellIndex, expRange, explosionCauseUUID, isCenter, direction);
		}
		
	}
	
	/**
	 * Check if can propagate an create an hit a STOPPER if there is one. 
	 * 
	 * @param cellIndex
	 * @param explosionCause
	 * @return
	 */
	private boolean checkIfCanPropagateTo(Vector2D<Integer> cellIndex, UUID explosionCauseUUID){
		boolean canPropagate = true;
		
		/** get all entities in destination */
		List<Entity> entities = gridSystem.getInPosition(cellIndex);
		
		/**
		* TODO the result of this loop is sensible to the order of entities
		* 	returned by gridSystem. Its a bug when there is more than a entity in a cell
		* */
		for(Entity entity:entities){
			ExplosionBarrier explosionBarrier = cmExplosionBarrier.getSafe(entity);
						
			/** Test null because not all components will have this component */
 			if( explosionBarrier== null){
				continue;
			}else if (explosionBarrier.getType() == ExplosionBarrierType.BLOCKER) {
				canPropagate = false;
				break;
			} else if (explosionBarrier.getType() == ExplosionBarrierType.STOPPER) {
				
				//put the stopper at the explosion but don't propagate
				dispathInAExplosion(entity.getUuid(), explosionCauseUUID);
				canPropagate = false;
				break;
			}
		}
		return canPropagate;
	}

	@Subscribe
	public void handle(ExplosionStartedEvent explosionStartedEvent){
		/* creating explosion */
		createExplosion(explosionStartedEvent.getInitialPosition(),
				explosionStartedEvent.getExplosionRange(), explosionStartedEvent.getCreatorUUID(), true, null);
	}
	
	@Subscribe
	public void handle(TimeOverEvent<UUID> timeOverEvent){
		if(EXPIRED_EXPLOSION.equals(timeOverEvent.getAction())){
			UUID entityUUID = timeOverEvent.getPayload();
			Entity entity = world.getManager(UuidEntityManager.class).getEntity(entityUUID);
			entity.deleteFromWorld();
		}
	}

	/**
	 * Makes the time pass for the explosion and removes explosion if elapsed time is over
	 */
	public void createExplosion(Vector2D<Integer> cellIndex, int expRange, 
			UUID ownerId, boolean isCenter, Direction direction) {
		
		Explosion exp = new Explosion();
		exp.setExplosionRange(expRange);
		exp.setOwnerId(ownerId);
		exp.setCenterOfExplosion(isCenter);
		exp.setPropagationDirection(direction);


		Entity explosionEntity = EntityBuilder2.create(world)
			.withDraw("explosion")
			.with(new Position(cellIndex))
			.with(exp)			
			.build();

		//create a timer and a event of the explosion expiration
		TimeOverEvent<UUID> toe = new TimeOverEvent<>(EXPIRED_EXPLOSION, explosionEntity.getUuid());		
		Timer expTimer = new Timer(16, toe);

		explosionEntity.edit().add(expTimer);
	}
	
	/**
	 *  checks if someone entered an explosion 
	 **/
	protected void checkEntitiesInExplosion(Entity explosionEntity) {
		
		Position cellOfExplosion = cmPosition.get(explosionEntity);
		Explosion explosion = cmExplosion.get(explosionEntity);
		
		List<Entity> inExplosionEntities = gridSystem.getInPosition(cellOfExplosion.getIndex());
		for(Entity inExplosionEntity:inExplosionEntities){
			dispathInAExplosion(inExplosionEntity.getUuid(), 
					explosion.getOwnerUuid());
		}
	}
	
	protected void dispathInAExplosion(UUID hitUuid, UUID causeUuid){
		InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
		inAnExplosionEvent.setHitUuid(hitUuid);
		inAnExplosionEvent.setExplosionCause(causeUuid);
		em.dispatch(inAnExplosionEvent);
	}


}
