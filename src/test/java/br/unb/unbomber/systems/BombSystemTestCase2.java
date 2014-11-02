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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.TimeOverEvent;

@Ignore
public class BombSystemTestCase2 {
	
	EntityManager entityManager;
	BombSystem2 system;
	TimeSystem timeSystem;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		system = new BombSystem2(entityManager);
		
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
	
	
	/*
	 * The character must not drop bombs if it exceeded the 
	 * number of permitted simultaneous bombs.
	 * 
	 */
	
	@Test
	public void dropBombTooManySimultaneousBombsTest(){
		
		//adding a bombDropper entity 
		Entity bombDropper = entityManager.createEntity();
		
		BombDropper dropper = new BombDropper();
		
		//setting a static value to the permitted simultaneous bombs the character can drop
		int PermittedSimultaneousBombs = 5; 
		
		dropper.setPermittedSimultaneousBombs(PermittedSimultaneousBombs);
	
		// adding the dropper component to the bombDropper entity
		bombDropper.addComponent(dropper);
		
		//creating the DROP_BOMB command
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, dropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system after command
		system.update();
		
		//get the list of explosives in game
		List<Component> explosivesInGame = (List<Component>) entityManager.getComponents(Explosive.class);
		int numberOfBombsDroppedByDropper = 0; // bomb counter
		
		if (explosivesInGame != null){
			for (Component component : explosivesInGame){
					
				Explosive bombInGame = (Explosive) component;
				
				// check if the current bomb was dropped by the current dropper
				if(bombInGame.getOwnerId() == dropper.getEntityId()){
					
					// we have to consider only the bombs that are still ticking
					Timer bombTimer = (Timer) entityManager.getComponent(Timer.class, bombInGame.getEntityId()); 
		
					// if timer higher than 0, the bomb is still ticking increment the counter.
					if(!bombTimer.isOver()){
						numberOfBombsDroppedByDropper++;
					}				
				}
			}
		}
		
		assertEquals(dropper.getPermittedSimultaneousBombs(), numberOfBombsDroppedByDropper);
		
	}

	
	/*
	 * testa se nos primeiros 89 turnos n�o foi criado um ExplosionStartedEvent
	 */
	@Test
	public void waitTimeExplodeTest(){
		
		Entity dropperEntity = entityManager.createEntity();

		//adding the dropper to the entity
		BombDropper dropper = new BombDropper();
		
		//setting dropper placement
		CellPlacement dropperPlacement = new CellPlacement();
		dropperPlacement.setCellX(1);
		dropperPlacement.setCellY(1);
		
		//adding components to dropper entity
		dropperEntity.addComponent(dropper);
		dropperEntity.addComponent(dropperPlacement);
		
		//now we will create a bomb entity
		CellPlacement bombPosition = new CellPlacement();
		bombPosition.setCellX(1);
		bombPosition.setCellY(1);
		
		BombDropper theDropper = (BombDropper) entityManager.getComponent(BombDropper.class, dropperEntity.getEntityId());
	
		createBomb(bombPosition, theDropper);
		
		//updating the system
		int i = 0;
		while (i < 89){
			system.update();
			i++;
		}

		//testando se um ExplosionStartedEvent foi criado nos primeiros 89 turnos
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 1);
		
	}
	
	/*
	 * testa se um ExplosionStartedEvent � criado ap�s 90 turnos.
	 */
	@Test
	public void triggeredAfterTimeToExplodeTest(){
		
		Entity dropperEntity = entityManager.createEntity();
	
		//adding the dropper to the entity
		BombDropper dropper = new BombDropper();
		
		//setting dropper placement
		CellPlacement dropperPlacement = new CellPlacement();
		dropperPlacement.setCellX(1);
		dropperPlacement.setCellY(1);
		
		//adding components to dropper entity
		dropperEntity.addComponent(dropper);
		dropperEntity.addComponent(dropperPlacement);
			
		//now we will create a bomb entity
		CellPlacement bombPosition = new CellPlacement();
		bombPosition.setCellX(1);
		bombPosition.setCellY(1);
		
		BombDropper theDropper = (BombDropper) entityManager.getComponent(BombDropper.class, dropperEntity.getEntityId());
	
		createBomb(bombPosition, theDropper);
		
		//updating the system
		int i = 0;
		while (i < 90){
			system.update();
			i++;
		}

		//checando se um evento ExplosionStartedEvent foi criado
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 1);
	}
	
	private void createBomb(CellPlacement bombPosition, BombDropper dropper){
	
		Entity bomb = entityManager.createEntity();
		
		
		CellPlacement dropperPlacement = new CellPlacement();
		
		dropperPlacement = bombPosition;
		
		//creating the time event
		TimeOverEvent triggeredBombEvent = new TimeOverEvent(); 
		triggeredBombEvent.setAction("BOMB_TRIGGERED");
		Timer timer = new Timer(90, triggeredBombEvent );
		
		//creating the range component
		Explosive bombRange = new Explosive();
		bombRange.setExplosionRange(dropper.getExplosionRange());
		
		// add the components to the bomb entity
		bomb.addComponent(dropperPlacement);
		bomb.addComponent(timer);
		bomb.addComponent(bombRange);
	
		system.update();
		
	}

}	// end of tests
		
	
