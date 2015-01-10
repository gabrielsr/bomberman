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
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.component.Velocity;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.HitWallEvent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;

/**
 * Kicks bombs around.
 * 
 * @author Paulo,
 * @author Gabriel Rodigues
 *
 */
@Wire
public class KickSystem extends VoidEntitySystem {
    
	
	ComponentMapper<BombDropper> cmBombDropper;
	
	ComponentMapper<Explosive> cmExplosive;
	
	ComponentMapper<PowerUp> cmPowerUp;
	
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
    
	@Subscribe
	private void handle(HitWallEvent hitWallEvent) {
		Entity entity = uuidEm.getEntity(hitWallEvent.getEntityUuid());
		entity.edit().remove(Velocity.class);
	}

	/**
     * This method is for keeping track of the status of each bombDropper who
     * tries to kick a bomb.
     */
   @Subscribe
   public void handle(CollisionEvent currentCollision){
    	Entity source = uuidEm.getEntity(currentCollision.getSourceId());
    	Entity target = uuidEm.getEntity(currentCollision.getTargetId());
        
        /* gets the dropper from entity manager */
        BombDropper dropper = cmBombDropper.getSafe( source);
        
        /* boolean variable to check if it's a collision between a bombDroper and a bomb */
        boolean collisionBetweenBombAndDropper = false; 
        
        /* if the dropper was found with the sourceId, we will check to see if it is a collision with a bomb */
        if (dropper != null){
            
            /* will return true if the targetId matches with a bomb */
            collisionBetweenBombAndDropper = (cmExplosive.getSafe(target) != null); 
        
        }
        
        /* it is a collision between bomb and dropper */
        if (collisionBetweenBombAndDropper == true){
            
                /* if the dropper can kick bombs we will check the movement events to check its direction */
                /* and kick the bomb */
                boolean dropperCanKickBombs = checkIfCanKickBombs (source);
                
                if (dropperCanKickBombs == true){
                    
                    /* the dropper can kick bombs, we will get the direction  */
                    Direction dropperDirection =  getDropperDirection (source);
                    
                    /* kick the target bomb with that direction */
                    kickBomb(target, dropperDirection);
                }
        }
    }
    
    /**
     * Checks if the dropper has the kick powerup
     * @param dropper
     * @return boolean
     */
    private boolean checkIfCanKickBombs(Entity source){
		
		/* instacia powerup  */
		PowerUp powerup = cmPowerUp.get(source);
		
		/* testa se o kick está ativo */
		if (powerup.getTypes().contains(PowerType.KICKACQUIRED) ) {
			return true;
		}        
       return false;
    }
    
    
    /**
     * We will check every MovementCommandEvent, if it was made by the source of the collision
     * return the direction.
     * @param source
     * @return Direction
     */
    private Direction getDropperDirection (Entity source){
		Movable movable = cmMovable.getSafe(source);
        return movable.getFaceDirection();    //returns the direction component of the current dropper
    }
    
    
    /**
     * Creates a new MovedEntityEvent to kick the bomb (targetId) towards a direction.
     * The speed doesn't influence on the kick of the bomb.
     * @param targetId, direction
     */
    private void kickBomb (final Entity target, final Direction direction) {
    	Velocity velocity = new Velocity();
    	
    	Vector2D<Float> velo = direction.asVector()
    			.toFloatVector().mult(SPEED_OF_KICKED_OBJECT);
    	velocity.setMovement(velo);
    	target.edit().add(velocity);
    }
}