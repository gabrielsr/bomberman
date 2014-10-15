/*
 * BombSystemTestCase
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

package br.unb.unbomber.systems;

import static junit.framework.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;

public class BombSystemTestCase {
	
	
	EntityManager entityManager;
	BombSystem bombSystem;
	TimeSystem timeSystem;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem = new BombSystem(entityManager);
		timeSystem = new TimeSystem(entityManager);
	}
	
	@Test
	public void testConstructor(){
		bombSystem = new BombSystem();
		
		//If EntityManager was not set internally, will create NullPointerException
		bombSystem.update();
		
		//Won't get here if NPE was created
		assertTrue(true);
	}

	@Test
	public void dropBombTest() {
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());

		entityManager.update(anEntity);
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		
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
		
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
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
		
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
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
		
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
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
		
		////one more time (91 times total)
		updateSystems(1);
		////second BOOM!!!
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 2);
		
		//runs more 30 iterations
		updateSystems(30);
		//no more explosions
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 2);
	}
	
	@Test
	public void testIfAnExplosionEventTriggersAnotherBomb(){
		//Create bombDropper
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		entityManager.update(anEntity);
		
		List<Component> explosives = entityManager.getComponents(Explosive.class);
		
		//send a event telling that this bomb have to explode 
		InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
		inAnExplosionEvent.setIdHit(explosives.get(0).getEntityId());
		entityManager.addEvent(inAnExplosionEvent);
		
		//update bombSystem to check the events. After that, the BombSystem have  
		//to create an event of the explosion of the early created bomb
		this.bombSystem.update();
		
		assertEquals( entityManager.getEvents(ExplosionStartedEvent.class).size() , 1);
	}
	
	@Test
	public void createRemotelyControledBombTest(){
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		//Now the dropper can create remote bombs
		bombDropper.setCanRemoteTrigger(true);
		
		entityManager.update(anEntity);
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		
		//run the system
		bombSystem.update();
		
		//verify if a new explosive (a bomb component) was created
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		assertNotNull(explosives);
		assertFalse(explosives.isEmpty());
	}
	
	@Test
	public void triggersARemotelyControledBombTest(){
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		//Now the dropper can create remote bombs
		bombDropper.setCanRemoteTrigger(true);
		
		entityManager.update(anEntity);
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		
		//run the system
		bombSystem.update();
		
		assertNull(entityManager.getEvents(ExplosionStartedEvent.class));

		//create an TRIGGERS_REMOTE_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.TRIGGERS_REMOTE_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		updateSystems(1);
		//BOOM!!!
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 1);
	}
	
	
	
	
	
	//Methods used by other tests
	
	private void updateSystems(int numberOfInteractions){
		for (int i=0; i<numberOfInteractions; i++) {
			timeSystem.update();
			bombSystem.update();
		}
	}
	
	private void pubBombOnGrid(int x, int y, BombDropper bombDropper){
		
		CellPlacement placement = (CellPlacement) entityManager.getComponent(CellPlacement.class, bombDropper.getEntityId());
		
		placement.setCellX(x);
		placement.setCellY(y);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system
		bombSystem.update();
	}
	
	private Entity createDropperEntity(){
		
		Entity anEntity = entityManager.createEntity();
		
		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(5);
		
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

}
