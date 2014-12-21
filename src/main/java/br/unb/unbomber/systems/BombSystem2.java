/**
 * BombSystem2
 *
 * UnB
 *
 * Handles the way bombs are treated in the Bomberman game.
 * @author Paulo, William, Yuri.
 * @since 30/10/14
 * @version 3.0 
 */
 
package br.unb.unbomber.systems;
 
import java.util.List;
 


import br.unb.entitysystem.BaseSystem;
import br.unb.entitysystem.Component;
import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.Event;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
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
     */
    public BombSystem2() {
        super();
    }
     
    /**
     * receives a parameter of type EntityManager
     * @param model
     */
    public BombSystem2 (EntityManager model) {
        super(model);
    }
     
     
    /**
     * methods of the class
     */
     
     
    /**
     * This method is for keeping track of the status of each bomb in the game. It is responsible for 
     * everything involving the bombs. Triggering, starting an explosion, triggering a remote bomb.
     */
    @Override
    public void update() {
    	
    	/* one instance of the EntityManager */
    	EntityManager entityManager = getEntityManager();
    	
    	
        /* get the ActionCommandEvent events */
        List<Event> actionEvents = entityManager.getEvents(ActionCommandEvent.class);
         
        if (actionEvents != null){
        	
	        /* for each event of the type */
	        for (Event event : actionEvents) {
	             
	            ActionCommandEvent actionCommand = (ActionCommandEvent) event;
	             
	            /* verify if it is a DROP_BOMB command */
	            if (actionCommand.getType() == ActionType.DROP_BOMB) {
	            	
	            	/* getting the BombDropper that started this event */
	            	BombDropper dropper = (BombDropper) entityManager.getComponent(BombDropper.class, 
	            																	actionCommand.getEntityId());
					verifyAndDropBomb(dropper);             
	            }
	             
	            else {
	             
	                /* verify if it is a EXPLODE_REMOTE_BOMB command
	                 the dropper wants to explode the remote bomb */
	                if (actionCommand.getType() == ActionType.EXPLODE_REMOTE_BOMB) {
	                     
	                    BombDropper dropper = (BombDropper) entityManager.getComponent(BombDropper.class, 
	                                                                                   actionCommand.getEntityId());
	                    createRemoteBombExplosion (dropper); 
	                         
	                }
	            }
	        } 
        } 	// end of update of ActionCommandEvents
        
        /* get the TimeOverEvents of bombs that will explode in this turn */
        List<Event> timerOvers = entityManager.getEvents(TimeOverEvent.class);
		
        if (timerOvers != null) {
        	
        	/* for each event of the type */
        	for (Event event : timerOvers) {

        		TimeOverEvent timeOver = (TimeOverEvent) event;
        		
        		/* the bomb has to explode in this turn */
        		if ((timeOver.getAction().equals(TRIGGERED_BOMB_ACTION))){
        			
        			/* getting the ID of the bomb that has to explode in this turn */
        			int bombID =  timeOver.getOwnerId(); 
        			
        			 /* getting the initial bomb position */
    	            CellPlacement bombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, bombID);
    	             
    	            /* getting the explosive component to obtain the bombPower */
    	            Explosive bombPower = (Explosive) entityManager.getComponent(Explosive.class, bombID);     
        			
    	            /* creating the ExplosionStartedEvent of the bomb */               
	                ExplosionStartedEvent explodeNow = new ExplosionStartedEvent();
	                explodeNow.setInitialPosition(bombPlacement);
	                explodeNow.setExplosionRange(bombPower.getExplosionRange());
	                explodeNow.setEventId(entityManager.getUniqueId());
	        		explodeNow.setOwnerId(bombID); 
	                 
	                /* adding the event to the entity manager */
	                entityManager.addEvent(explodeNow);
        		}
        	}
        } 	// end of update of TimeOverEvents
	      
  
        /* get the InAnExplosionEvents of bombs that are in the same range of other bombs.*/
        List<Event> InAnExplosionEvents = entityManager.getEvents(InAnExplosionEvent.class);
         
        if (InAnExplosionEvents!= null) {
        	
	        /*for each event of the type*/
	        for (Event event : InAnExplosionEvents) {
	             
	            InAnExplosionEvent inAnExpEv = (InAnExplosionEvent) event;
	             
	            /* get the id of the bomb to explode */
	            int idBomb = inAnExpEv.getIdHit();
	             
	            /* get the placement (component) of the bomb from the entity manager */
	            CellPlacement bombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, idBomb);
	             
	            /* get the explosive (component) of the bomb from the entity manager */
	            Explosive bombExplosive = (Explosive) entityManager.getComponent(Explosive.class, idBomb);
	            
	            if (bombExplosive != null){
	            	
	            	/* creating the ExplosionStartedEvent of the bomb */  
		            ExplosionStartedEvent explosionStartedEv = new ExplosionStartedEvent();
		            explosionStartedEv.setInitialPosition(bombPlacement); 
		            explosionStartedEv.setExplosionRange(bombExplosive.getExplosionRange());
		            explosionStartedEv.setEventId(entityManager.getUniqueId());
	        		explosionStartedEv.setOwnerId(idBomb); 
		             
		            entityManager.addEvent(explosionStartedEv);
	            
	            }
	        }
	        
        }	 // end of update of InAnExplosionStartedEvent 
        
    }       //end of bombSystem update
     
    /**
     * method to drop a bomb; but first we verify if the character has not dropped too many bombs.
     * @param dropper (link to dropper trying to drop the bomb)
     */
    public void verifyAndDropBomb (BombDropper dropper) {
         
    	EntityManager entityManager = getEntityManager();
    	
        /* get explosive components from the entity manager */
        List<Component> explosivesInGame = (List<Component>) entityManager.getComponents(Explosive.class);
         
        int numberOfBombsDroppedByDropper = 0; /* bomb counter*/
         
        /* check if there are bombs in game */
        if (explosivesInGame != null) {
             
            /* check every bomb in game */
            for (Component component : explosivesInGame) {
                     
                Explosive bombInGame = (Explosive) component;
                 
                /* check if the current bomb was dropped by the current dropper */
                if (bombInGame.getOwnerId() == dropper.getEntityId()) {
                     
                        numberOfBombsDroppedByDropper++;
         
                }
                
             }
                 
          }
         
        /* if the dropper has not dropped too many bombs, it drops one more bomb */
        if (dropper.getPermittedSimultaneousBombs() > numberOfBombsDroppedByDropper) {
                 
            Entity bomb = createTimeBomb(dropper);  // creating the new bomb entity 
            entityManager.update(bomb);	// updating bomb entity
            
        }
         
    }	// end of verifyAndDropBomb method
     
     
    /**
     * method to explode a remote bomb when the player decides to
     *  @param dropper (link to dropper that is drooping the bomb)
     */
    public void createRemoteBombExplosion (BombDropper dropper) {
         
        List<Component> explosivesInGame = (List<Component>) getEntityManager().getComponents(Explosive.class);
         
        /* check if there are bombs in game */
        if (explosivesInGame != null) {
             
            /* check every bomb in game */
            for (Component component : explosivesInGame){
                     
                Explosive bombInGame = (Explosive) component;
                 
                /* check if the current bomb was dropped by the current dropper */
                if (bombInGame.getOwnerId() == dropper.getEntityId()) {
                     
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
         
    }       // end of createRemoteBombExplosion method
     
    /**
     * method to create a bomb (time and remote). 
     * a bomb is made of three components: Explosive, Placement and Timer.
     * @param dropper (link to dropper that is drooping the bomb)
     * @return bomb (return a new entity "bomb")
     */
    private Entity createTimeBomb (BombDropper dropper) {
         
    	EntityManager entityManager = getEntityManager();
    	
        /* find dropper placement */
        CellPlacement dropperPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class,
                																		dropper.getEntityId());
         
        Entity bomb = entityManager.createEntity();
         
        /* the bomb is owned by its dropper */
        bomb.setOwnerId(dropper.getEntityId());
 
        /* create the placement component */
        CellPlacement bombPlacement = new CellPlacement();
         
        /* the bomb has the same placement of its dropper */
        bombPlacement.setCellX(dropperPlacement.getCellX());
        bombPlacement.setCellY(dropperPlacement.getCellY());
         
        /* create explosive component */
        Explosive bombExplosive = new Explosive();
         
        /*the bomb should have the same power of its dropper */
        bombExplosive.setExplosionRange(dropper.getExplosionRange());
        bombExplosive.setOwnerId(dropper.getEntityId());       //add the bomb id to the component bombExplosive
 
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
        bomb.addComponent(new Draw("bomb"));

        return bomb;
        
    }       //end of createTimeBomb method
     
}       //end of BombSystem 