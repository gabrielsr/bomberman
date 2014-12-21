/**
 * BombSystemTestCase2
 *
 * Version 1
 *
 * UnB
 *
 * Handles tests from the BombSystem2
 * @author Paulo, William, Yure.
 * @since 30/10/14
 * @version 3.0 
 */
 
package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Component;
import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;

public class BombSystemTestCase2 {
	
	EntityManager entityManager;
	BombSystem2 bombSystem2;
	TimeSystem timeSystem;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem2 = new BombSystem2(entityManager);
		timeSystem = new TimeSystem(entityManager);
		
	}
	
	/**
	 * A dropped bomb should be created and added to the grid
	 */
	@Test
	public void dropBombTest() {
	
		//create new bombDropper 
		Entity anEntity = createDropperEntity();
			
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		//updating the entity on the entity manager
		entityManager.update(anEntity);
		
		//put one bomb on grid
		createBombOnGrid(0, 0, bombDropper);
		
		//run the system
		bombSystem2.update();
		
		//verify if a new explosive bomb was created
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		
		// there should be explosives
		assertNotNull(explosives);
		assertFalse(explosives.isEmpty());
	
	} // end of dropBombTest
	
	/**
	 * A dropped bomb should be created into the same place of its dropper
	 */
	@Test
	public void dropBombAtSamePlaceTest() {
		
		// create an entity
		Entity anEntity = createDropperEntity();
		
		int CELL_X = 5;
		int CELL_Y = 5;
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		//updating the entity on the entity manager
		entityManager.update(anEntity);
		
		// create bomb on grid 
		createBombOnGrid (CELL_X, CELL_Y, bombDropper);
		
		//update the system with the event
		bombSystem2.update();
		
		//1 - get the list of explosives in game
		//2 - create a component with the most recent explosive in game
		//3 - get the CellPlacement from the 
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		Component explosive = explosives.get(0);
		CellPlacement explosiveBombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, 
																						explosive.getEntityId());
		
		//verify if its the correct placement for both positions
		assertEquals(CELL_X, explosiveBombPlacement.getCellX());
		assertEquals(CELL_Y, explosiveBombPlacement.getCellY());
		
	} // end of dropBombAtSamePlaceTest
	
	
	/**
	 * The character must not drop bombs if it exceeded the 
	 * number of permitted simultaneous bombs.
	 * 
	 */
	@Test
	public void dropBombTooManySimultaneousBombsTest(){
		
		//value of permitted simultaneous bombs that the character can drop
		//fake powerup 
		int PermittedSimultaneousBombs = 3; 
		
		//creating a bombDropper entity 
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		bombDropper.setPermittedSimultaneousBombs(PermittedSimultaneousBombs); 
				
		//add bombs 
		createBombOnGrid(0,0, bombDropper);

		//the number of PermittedSimultaneousBombs should be higher or equal the number of bombs dropped
		assertTrue(entityManager.getComponents(Explosive.class).size() <= PermittedSimultaneousBombs);
	
	}

	
	/**
	 * Checks if in the first 89 turns an ExplosionStartedEvent was created
	 * 
	 */
	@Test
	public void waitTimeToExplodeTest(){
		
		//creating a bombDropper entity
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		//updating the entity on the entity manager
		entityManager.update(anEntity);
		
		createBombOnGrid(0,0, bombDropper);
		
		//init the time system and update it 
		timeSystem = new TimeSystem(entityManager);
		timeSystem.update();
		
		//updating the system
		int i = 0;
		while (i < 88){
			
			bombSystem2.update();
			timeSystem.update();
			i++;
			
		}
		
		//there shouldn't be an ExplosionStartedEvent in the first 89 turns
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(),0);
		
	} // end of waitTimeToExplodeTest
	
	/**
	 * Checks if a ExplosionStartedEvent was created after 90 turns
	 */
	@Test
	public void triggeredAfterTimeToExplodeTest(){
		
		//creating a bombDropper entity
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
				
		createBombOnGrid(0,0, bombDropper); //updates the bombSystem2 once
		
		//init the time system and update it 
		timeSystem = new TimeSystem(entityManager);
		timeSystem.update();
		
		//updating the system
		int i = 0;
		while (i < 89) {
			timeSystem.update();
			bombSystem2.update();
			i++;
		}
		
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 1);
		
	}
	
	private Entity createDropperEntity(){
		
		Entity anEntity = entityManager.createEntity();
		
		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(3);
		
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
		
		bombSystem2.update();
	}
	 	
}	// end of tests
		
	
