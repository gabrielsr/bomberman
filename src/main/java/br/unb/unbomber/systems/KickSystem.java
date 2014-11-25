/**
 * KickSystem
 *
 * UnB
 *
 * Handles the way the Kick powerup is treated during the game.
 * @author Paulo, William, Yuri.
 * @since 20/11/14
 * @version 1.0
 */

package br.unb.unbomber.systems;
 
import java.util.List;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.KickPowerUpEvent;
import br.unb.unbomber.event.MovedEntityEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

/**
 * Extending the BaseSystem.
 * @author Paulo
 *
 */
public class KickSystem extends BaseSystem {
	
	/**
     * constructors of the class
     */
    public KickSystem() {
        super();
    }
    
    /**
     * receives a parameter of type EntityManager.
     * @param model
     */
    public KickSystem ( final EntityManager model ) {
        super(model);
    }

    /**
     * methods of the class
     */
    /**
     * This method is for keeping track of the status of each bombDropper who
     * tries to kick a bomb.
     */
    @Override
	public final void update() {
    	/* one instance of the EntityManager */
    	EntityManager entityManager = getEntityManager();
    	
    	 /* get the CollisionEvent events */
        List<Event> collisionEvents = entityManager.getEvents(CollisionEvent.class);
        
        /* checks if there are CollisionEvents happening in the game */
        if (collisionEvents != null){
        	
        	/* for each event of the type */
	        for (Event event : collisionEvents) {
	        	CollisionEvent currentCollision = (CollisionEvent) event;
	        
	        	int sourceId = currentCollision.getSourceId();
	        	int targetId = currentCollision.getTargetId();
	        	
	        	/* gets the dropper from entity manager */
	        	BombDropper dropper = (BombDropper) entityManager.getComponent(BombDropper.class, 
	        			 										 			   sourceId);
	        	
	        	/* boolean variable to check if it's a collision between a bombDroper and a bomb */
	        	boolean collisionBetweenBombAndDropper = false;	
	        	
	        	/* if the dropper was found with the sourceId, we will check to see if it is a collision with a bomb */
	        	if (dropper != null){
	        		
	        		/* will return true if the targetId matches with a bomb */
	        		collisionBetweenBombAndDropper = checkIfBomb(targetId); 
	        	
	        	}
	        	
	        	/* it is a collision between bomb and dropper */
	        	if (collisionBetweenBombAndDropper == true){
	        		
			        	/* if the dropper can kick bombs we will check the movement events to check its direction */
			        	/* and kick the bomb */
	        			boolean dropperCanKickBombs = checkIfCanKickBombs (dropper);
	        			
	        			if (dropperCanKickBombs == true){
	        				
	        				/* the dropper can kick bombs, we will get the direction  */
			        		Direction dropperDirection =  getDropperDirection (sourceId);
			        		
			        		/* kick the target bomb with that direction */
			        		kickBomb(targetId, dropperDirection);
		        		
	        			}
		        	
	        	} 
	        	
	        }
	        
        }	//end of CollisionEvents update
        
    }	//end of update
    
    
    /**
     * Checks if the targetId is a bomb
     * @param targetId
     * @return boolean
     */
    private boolean checkIfBomb (final int targetId){
    	
    	/* get explosive components from the entity manager */
        List<Component> explosivesInGame = (List<Component>) getEntityManager().getComponents(Explosive.class);
         
        /* check if there are bombs in game */
        if (explosivesInGame != null) {
             
            /* check every bomb in game */
            for (Component component : explosivesInGame) {
                     
                Explosive bombInGame = (Explosive) component;
                 
                /* check if the current bomb was the target from the collision*/
                if (bombInGame.getEntityId() == targetId) {
					return true;
				} 
                
             }
        
        }
        
        return false; 	/* not a bomb */
        
    }
    
    
    /**
     * Checks if the dropper has the kick powerup
     * @param dropper
     * @return boolean
     */
    private boolean checkIfCanKickBombs(final BombDropper dropper){
    	
    	/* get the KickPowerUpEvents events */
        List<Event> kickPowerUpEvents = getEntityManager().getEvents(KickPowerUpEvent.class);
        
        /* check if there are kick power up events in game */
        if (kickPowerUpEvents != null){
       
            /* check every event in game */
            for (Event event : kickPowerUpEvents) {
                     
                KickPowerUpEvent currentKick = (KickPowerUpEvent) event;
                 
                /* if true the dropper has the kick power up and can kick bombs*/
                if (currentKick.getSourceId() == dropper.getEntityId()) {
					return true;
				} 
                
             }
        
        }	//end of KickPowerUp events
        
        return false; 	// the dropper doesn't have the kick powerup 
        
    }
    
    
 	/**
 	 * We will check every MovementCommandEvent, if it was made by the source of the collision
 	 * return the direction.
 	 * @param sourceId
 	 * @return Direction
 	 */
    private Direction getDropperDirection (final int sourceId){
  	
    	Direction dropperDirection = null;
    	
    	 /* get the MovementCommand events */
        List<Event> movementEvents = getEntityManager().getEvents(MovementCommandEvent.class);
        
        /* checks if there is a MovementCommandEvent in game */
        if (movementEvents != null){
        	
        	/* for each event of the type */
	        for (Event event : movementEvents) {
	        	
	        	MovementCommandEvent currentMovementEvent = (MovementCommandEvent) event;
	        	
	        	int entityId = currentMovementEvent.getEntityId();
	        	
	        	/* if the entityId is the same as the dropper, get the direction */
	        	if (entityId == sourceId){
	        		
	        		if (currentMovementEvent.getType() == MovementType.MOVE_UP) {
        				dropperDirection = Direction.UP;
        			}
	        		
        			if (currentMovementEvent.getType() == MovementType.MOVE_DOWN) {
        				dropperDirection = Direction.DOWN;
        			}
        			
        			if (currentMovementEvent.getType() == MovementType.MOVE_RIGHT) {
        				dropperDirection = Direction.RIGHT;
        			}
        			
        			if (currentMovementEvent.getType() == MovementType.MOVE_LEFT) {
        				dropperDirection = Direction.LEFT;
        			}
	        			
	        	}
	        	
	        }	
	        
        }	//end of MovementEvents
       
    	return dropperDirection;	//returns the direction component of the current dropper
    	
    }
    
    
    /**
     * Creates a new MovedEntityEvent to kick the bomb (targetId) towards a direction.
     * The speed doesn't influence on the kick of the bomb.
     * @param targetId, direction
     */
    private void kickBomb (final int targetId, final Direction direction) {

    	MovedEntityEvent kickStarted = new MovedEntityEvent();

        kickStarted.setId(targetId);	//setting the entity that will be the target of the kick
        kickStarted.setDirection(direction);	//setting the direction of the kick

    	getEntityManager().addEvent(kickStarted);	//updating the event to the entity manager
     	
    }
    
}	// end of KickSystem