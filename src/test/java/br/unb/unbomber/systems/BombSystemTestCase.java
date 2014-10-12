package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntitySystemImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;

public class BombSystemTestCase {
	
	EntityManager entityManager;
	BombSystem bombSystem;
	TimeSystem timeSystem;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntitySystemImpl.init();
		entityManager = EntitySystemImpl.getInstance();
		bombSystem = new BombSystem(entityManager);
	}
	

	@Test
	public void dropBombTest() {
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = entityManager.createEntity();
		

		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(5);
				
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		
		
		entityManager.update(anEntity);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system
		bombSystem.update();
		
		//verify if a new explosive (a bomb component) was created
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		assertNotNull(explosives);
		assertFalse(explosives.isEmpty());
	}
	
	/**
	 * A dropped bomb should be created into the same place of its dropper
	 */
	@Test
	public void dropBombAtSamePlaceTest() {
		
		int CELL_X = 10;
		int CELL_Y = 15;
		
		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(5);
		//Put bomb on grid
		pubBombOnGrid(CELL_X,CELL_Y,bombDropper);
		
		//get the position of the first explosive created: first get the explosive than get the associated position
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		Component explosive = explosives.get(0);
		CellPlacement createBombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, explosive.getEntityId());
		
		//verify if its the correct value
		assertEquals(CELL_X, createBombPlacement.getCellX());
		assertEquals(CELL_Y, createBombPlacement.getCellY());
		
	}
	
	@Test
	public void maxNumberOfBombsTests(){
		
		int max_number_of_bombs = 2;
		
		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(max_number_of_bombs);
		
		//put 2 bombs on grid
		pubBombOnGrid(0,0, bombDropper);
		pubBombOnGrid(0,0, bombDropper);
		
		assertEquals(entityManager.getComponents(Explosive.class).size() , max_number_of_bombs);
		
		//extra bomb will be ignored
		pubBombOnGrid(0,0, bombDropper);
		
		assertEquals(entityManager.getComponents(Explosive.class).size() , max_number_of_bombs);
		
		//pseudo power-up
		max_number_of_bombs = 5;
		bombDropper.setPermittedSimultaneousBombs(max_number_of_bombs);
		
		//add more 4 bombs (6total)
		pubBombOnGrid(0,0, bombDropper);
		pubBombOnGrid(0,0, bombDropper);
		pubBombOnGrid(0,0, bombDropper);
		pubBombOnGrid(0,0, bombDropper);

		assertEquals(entityManager.getComponents(Explosive.class).size() , max_number_of_bombs);
		
	}
	
	// testa se um ExplosionStartedEvent é criado após 90 turnos
	@Test
	public void triggeredAfterTimeToExplodeTest() {
		
		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(5);
		
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		
		//initialize time system
		timeSystem = new TimeSystem(entityManager);
		
		updateSystems(1);
		
		//put another bomb on grid
		pubBombOnGrid(0,0,bombDropper);
		
		//runs 88 game iterations
		updateSystems(88);
		// testa se nos primeiros 89 turnos não foi criado ExplosionStartedEvent.
		assertNull(entityManager.getEvents(ExplosionStartedEvent.class));
		
		//one more time (90 times total)
		updateSystems(1);
		//first BOOM!!!
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 1);
		
		// TODO find why the test bellow is failing
		
		////one more time (91 times total)
		updateSystems(1);
		////second BOOM!!!
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 2);
		
		//runs more 30 iterations
		updateSystems(30);
		//no more explosions
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 2);
	}
	
	private void updateSystems(int numberOfInteractions){
		for (int i=0; i<numberOfInteractions; i++) {
			timeSystem.update();
			bombSystem.update();
		}
	}
	
	private void pubBombOnGrid(int x, int y, BombDropper bombDropper){
		// create a entity
		// SO it get an entityId (needed as the new bomb dropped will need it as its ownerId)
		Entity anEntity = entityManager.createEntity();
		
		//Create the placement component
		CellPlacement dropperPlacement = new CellPlacement();
		
		//set the dropper position
		dropperPlacement.setCellX(x);
		dropperPlacement.setCellY(y);
		
		// add the components
		anEntity.addComponent(bombDropper);
		anEntity.addComponent(dropperPlacement);
		
		// add the dropper to the model
		entityManager.update(anEntity);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system
		bombSystem.update();
	}

}
