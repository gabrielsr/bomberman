package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;

public class ExplosionSystem extends BaseSystem {

    /* List of already treated events */

    List<ExplosionStartedEvent> treatedEvents;

    public ExplosionSystem() {
	
	super();
    
    }

    public ExplosionSystem(EntityManager entityManager) {
	
	super(entityManager);
    
    }

    @Override
    public void start() {
	
	treatedEvents = new ArrayList<ExplosionStartedEvent>();
	super.start();
    
    }

    @Override
    public void update() {

	/* flag to see if an event was already treated */
	int flag;

	/* Get all ExplosionStartedEvents from EntityManager */

	List<Event> events = getEntityManager().getEvents(
		ExplosionStartedEvent.class);

	/* treat all non treated events */
	for (Event event : events) {

	    /*
	     * check if this event was already treated
	     */

	    flag = 0;
	    for (int i = 0; i < treatedEvents.size(); i++) {
		
		if (treatedEvents.get(i).getEventId() == event.getEventId()) {
		    
		    flag = 1;
		    break;
		
		}
	    }
	    
	    /* if event was not treated */
	    if (flag == 0) {
		
		/* typecasting to use the specific event */
		ExplosionStartedEvent explosionStartedEvent = (ExplosionStartedEvent) event;
		/* creating explosion */
		createExplosion(explosionStartedEvent.getInitialPosition(),
			explosionStartedEvent.getExplosionRange());
		/* put the treated event on the treatedEvents list */
		treatedEvents.add(explosionStartedEvent);
	    }

	    /* checks if someone entered an explosion */
	    enteredExplosion();
	
	}

    }

    public void createExplosion(Component initialPosition, int explosionRange) {

	/* create inicial explosion and send to entityManager */

	// for each direction{
	// do{
	// if(detectExplosionCollision() == true){
	// propagate();
	// }
	// else{
	// break;
	// }
	// }while(explosionRange > 0);
	// }

    }

    public void propagateExplosion(CellPlacement cellPlacement,
	    int explosionRange, int timeLeft) {

    }

    /* returns true if the explosion should propagate and false otherwise */
    public boolean detectExplosionCollision(Explosion explosion,
	    					CellPlacement explosionPlacement) {
	
	Direction direction = explosion.getPropagationDirection();
	CellPlacement nextSpace = new CellPlacement();
	if (direction == Direction.LEFT) {
	    
	    nextSpace.setCellY(explosionPlacement.getCellY());
	    nextSpace.setCellX(explosionPlacement.getCellX() - 1);
	    
	} else if (direction == Direction.RIGHT) {
	    
	    nextSpace.setCellY(explosionPlacement.getCellY());
	    nextSpace.setCellX(explosionPlacement.getCellX() + 1);
	  
	} else if (direction == Direction.UP) {
	    
	    nextSpace.setCellY(explosionPlacement.getCellY() + 1);
	    nextSpace.setCellX(explosionPlacement.getCellX());
	    
	} else {
	    
	    nextSpace.setCellY(explosionPlacement.getCellY() - 1);
	    nextSpace.setCellX(explosionPlacement.getCellX());
	
	}

	List<Component> components = getEntityManager().getComponents(
		CellPlacement.class);

	CellPlacement cellPlacement;

	for (Component component : components) {
	    
	    cellPlacement = (CellPlacement) component;
	    if (nextSpace.getCellX() == cellPlacement.getCellX()
		    && nextSpace.getCellY() == cellPlacement.getCellY()) {
		
		return processExplosionCollision(component.getEntityId(),
						explosion);
		
	    }
	}

	return true;
    }

    /*
     * creates InAnExplosion event when an powerUp, character, monster, bomb or
     * softBlock collides with the explosion. Returns true if the explosion
     * should propagate and false otherwise
     */
    public boolean processExplosionCollision(int entityId, Explosion explosion) {

	ExplosionBarrier explosionBarrier = (ExplosionBarrier) getEntityManager()
		.getComponent(ExplosionBarrier.class, entityId);

	if (explosionBarrier.getType() == ExplosionBarrierType.BLOCKER) {
	    
	    return false;
	    
	} else if (explosionBarrier.getType() == ExplosionBarrierType.STOPPER) {
	    
	    InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
	    inAnExplosionEvent.setIdHit(entityId);
	    inAnExplosionEvent.setOwnerId(explosion.getEntityId());
	    getEntityManager().addEvent(inAnExplosionEvent);
	    return false;
	    
	} else {
	    
	    InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
	    inAnExplosionEvent.setIdHit(entityId);
	    inAnExplosionEvent.setOwnerId(explosion.getEntityId());
	    getEntityManager().addEvent(inAnExplosionEvent);
	    return true;
	    
	}
    }

    /* checks if someone entered an explosion */
    private void enteredExplosion() {

	List<Component> cellPlacements = getEntityManager().getComponents(
		CellPlacement.class);

	List<Component> explosions = getEntityManager().getComponents(
		Explosion.class);

	CellPlacement cellPlacement;

	Explosion explosion;

	CellPlacement explosionPlacement;

	for (Component componentExplosion : explosions) {
	    
	    for (Component componentCellPlacement : cellPlacements) {
		
		cellPlacement = (CellPlacement) componentCellPlacement;
		explosion = (Explosion) componentExplosion;
		explosionPlacement = (CellPlacement) getEntityManager()
			.getComponent(CellPlacement.class,
				explosion.getEntityId());
		if (explosionPlacement.getCellX() == cellPlacement.getCellX()
			&& explosionPlacement.getCellY() == cellPlacement
				.getCellY()) {

		    InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
		    inAnExplosionEvent.setOwnerId(explosion.getEntityId());
		    inAnExplosionEvent.setIdHit(cellPlacement.getEntityId());
		    getEntityManager().addEvent(inAnExplosionEvent);

		}
	    }
	}

    }

}
