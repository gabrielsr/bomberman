package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.MovedEntityEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class KickSystemTestCase {

	EntityManager entityManager;
	BombSystem bombSystem;
	KickSystem kickSystem;
	CollisionSystem collisionSystem;
	
	
	@Before
	public void setUp() throws Exception {
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem = new BombSystem(entityManager);	
	}
	
	@Test
	public void testConstructor() {
		kickSystem = new KickSystem();
		
		kickSystem.update();
		
		assert(true);
	}
	
	@Test
	public void kickBombTestAndStopCase() {
		
		/* creating a dropper entity */
		Entity anEntity = createDropperEntity();
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		kickSystem = new KickSystem();
		bombSystem = new BombSystem(entityManager);
		
		entityManager.update(anEntity);
		
		bombSystem.verifyAndDropBomb(bombDropper);
		
		kickSystem.update(); // ?? 
		
		CellPlacement dropperPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, 
																					bombDropper.getEntityId());
		
		assertEquals(dropperPlacement.getCellX(), dropperPlacement.getCellX() + 4); // +4 ??
	}
	
	/**
	 * Checks if a KickStartedEvent was created when there is a collision between a bomb and a dropper.
	 */
	@Test
	public void kickTest(){
		
		kickSystem = new KickSystem();
		bombSystem = new BombSystem();
		collisionSystem = new CollisionSystem();
		
		/* creating a dropper entity */
		Entity anEntity = createDropperEntity();
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		/* creating a bomb on the next cell */
		createBombOnGrid(1, 0, bombDropper);
		
		/* fake powerup */
		PowerUp powerUp = new PowerUp(PowerType.KICKACQUIRED);
		anEntity.addComponent(powerUp);
		
		/* now we will move the dropper in order to create a collision */
		moveDropperEntity(bombDropper);
		
		kickSystem.update();
		bombSystem.update();
		collisionSystem.update();
		
		CellPlacement newDropperPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, 
																					   bombDropper.getEntityId());
		
		/* checks if the dropper moved to the cell where the bomb previously was */
		assertEquals(newDropperPlacement.getCellX(), 1);
		assertEquals(newDropperPlacement.getCellY(), 0);
		
		/* checks if a moved entity event was created */
		List<Event> movedEntityEvents = entityManager.getEvents(MovedEntityEvent.class);
		assertNotNull(movedEntityEvents);
		
	}
	
	private  void moveDropperEntity(BombDropper dropper){
		
		/* creating a MovementCommandEvent for the current dropper to the cell where the bomb is */
		MovementCommandEvent event = new MovementCommandEvent(MovementType.MOVE_RIGHT, dropper.getEntityId());
		entityManager.addEvent(event);
		
	}
	
	private Entity createDropperEntity(){
		
		Entity anEntity = entityManager.createEntity();
		
		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		
		//Create Placement
		CellPlacement placement = new CellPlacement();
		placement.setCellX(0);
		placement.setCellY(0);
		
		//Add components
		anEntity.addComponent(bombDropper);
		anEntity.addComponent(placement);
		
		entityManager.update(anEntity);
		
		return anEntity;
	}
	
	private void createBombOnGrid (int x, int y, BombDropper bombDropper){
		
		CellPlacement placement = (CellPlacement) entityManager.getComponent(CellPlacement.class, bombDropper.getEntityId());
		
		placement.setCellX(x);
		placement.setCellY(y);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		bombSystem.update();
	}
}
