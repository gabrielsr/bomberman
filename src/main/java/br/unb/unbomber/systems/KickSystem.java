/**
 * KickSystem
 *
 * UnB
 *
 * Handles the way the Kick powerup is treated during the game.
 * @author Paulo, William, Yuri.
 * @since 20/11/14
 * @version 2.0
 */

package br.unb.unbomber.systems;
 
import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.component.PowerUpInventory;
import br.unb.unbomber.component.Velocity;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.HitWallEvent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;

@Wire
public class KickSystem extends VoidEntitySystem {
    
	
	ComponentMapper<BombDropper> cmBombDropper;
	
	ComponentMapper<Explosive> cmExplosive;
	
	ComponentMapper<PowerUpInventory> cmPowerUpInventory;
	
	ComponentMapper<Velocity> cmInertia;
	
	EventManager em;
	
	UuidEntityManager uuidEm;

	private ComponentMapper<Movable> cmMovable;
	
	private static final float SPEED_OF_KICKED_OBJECT = 1.0f/8.0f;
	
	
    /**
     * constructors of the class
     */
    public KickSystem() {
    }
    
	@Override
	protected void processSystem() { }
    
	/**
     * This method is for keeping track of the status of each bombDropper who
     * tries to kick a bomb.
     */
	@Subscribe
	public void handle(CollisionEvent currentCollision) {
		Entity source = uuidEm.getEntity(currentCollision.getSourceUuid());
		Entity target = uuidEm.getEntity(currentCollision.getTargetUuid());

		/* if an entity that can kick collided with a bomb */
		if (checkIfCanKickBombs(source)
				&& (cmExplosive.getSafe(target) != null)) {

			/* the dropper can kick bombs, we will get the direction */
			Direction dropperDirection = getDropperDirection(source);

			/* kick the target bomb with that direction */
			kickBomb(target, dropperDirection);
		}
	}
    
    /**
     * Checks if the dropper has the kick powerup
     * @param dropper
     * @return boolean
     */
    private boolean checkIfCanKickBombs(Entity source){
		
		/* instacia powerup  */
    	PowerUpInventory inventory = cmPowerUpInventory.getSafe(source);
		
		/* testa se o kick está ativo */
		if (inventory!= null 
				&& inventory.hasType(PowerType.KICKACQUIRED) ) {
			return true;
		}        
       return false;
    }
    
    
    /**
     * The facing direction of the kicker
     * @param source
     * @return Direction
     */
    private Direction getDropperDirection (Entity source){
		Movable movable = cmMovable.getSafe(source);
        return movable.getFaceDirection();    //returns the direction component of the current dropper
    }
    
    
    /**
     * Creates a new Velocity component to a kicked (targetId) towards a direction.
     * @param targetId, direction
     */
    private void kickBomb (final Entity target, final Direction direction) {
    	Velocity velocity = new Velocity();
    	
    	Vector2D<Float> velo = direction.asVector()
    			.toFloatVector().mult(SPEED_OF_KICKED_OBJECT);
    	velocity.setMovement(velo);
    	target.edit().add(velocity);
    }
    
	@Subscribe
	private void handle(HitWallEvent hitWallEvent) {
		Entity entity = uuidEm.getEntity(hitWallEvent.getEntityUuid());
		//stop the moving entity
		entity.edit().remove(Velocity.class); 
	}
}