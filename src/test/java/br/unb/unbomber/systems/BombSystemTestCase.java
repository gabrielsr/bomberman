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
import br.unb.unbomber.event.ActionCommandEvent.ActionType;

public class BombSystemTestCase {
	
	EntityManager entityManager;
	BombSystem system;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntitySystemImpl.init();
		entityManager = EntitySystemImpl.getInstance();
		system = new BombSystem(entityManager);
	}
	

	@Test
	public void dropBombTest() {
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = entityManager.createEntity();
		
		//Create the placement component
		CellPlacement dropperPlacement = new CellPlacement();
	
			
		// add the dropper to the model
		// SO it get an entityId (needed as the new bomb dropped will need it as its ownerId)
		BombDropper bombDropper = new BombDropper();
		
		anEntity.addComponent(bombDropper);
		anEntity.addComponent(dropperPlacement);
		
		entityManager.update(anEntity);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system
		system.update();
		
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
		
		// create a entity
		// SO it get an entityId (needed as the new bomb dropped will need it as its ownerId)
		Entity anEntity = entityManager.createEntity();
		
		//Create the placement component
		CellPlacement dropperPlacement = new CellPlacement();
		
		int CELL_X = 10;
		int CELL_Y = 15;
		
		//set the dropper position
		dropperPlacement.setCellX(CELL_X);
		dropperPlacement.setCellY(CELL_Y);
		
		BombDropper bombDropper = new BombDropper();
		
		// add the components
		anEntity.addComponent(bombDropper);
		anEntity.addComponent(dropperPlacement);
		
		// add the dropper to the model
		entityManager.update(anEntity);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system
		system.update();
		
		//get the position of the first explosive created: first get the explosive than get the associated position
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		Component explosive = explosives.get(0);
		CellPlacement createBombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, explosive.getEntityId());
		
		//verify if its the correct value
		assertEquals(CELL_X, createBombPlacement.getCellX());
		assertEquals(CELL_Y, createBombPlacement.getCellY());
		
	}
	

}
