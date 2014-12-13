package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;


public class BombExplosionTestCase {

    EntityManager entityManager;
    ExplosionSystem explosionSystem;
    BombSystem bombSystem;
    TimeSystem timeSystem;

	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem = new BombSystem(entityManager);
		explosionSystem = new ExplosionSystem(entityManager);
		explosionSystem.start();
		timeSystem = new TimeSystem(entityManager);
	}
	
	@Test
	public void twoSimutaneousBombExplosionTest(){
		
		// create a entity with components:
		// * bombDropper
		// * placement
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		
		updateSystems(1);
		
		//put another bomb on grid
		pubBombOnGrid(0,0,bombDropper);
		
		//runs 88 game iterations
		updateSystems(88);
		// testa se nos primeiros 89 turnos não foi criado ExplosionStartedEvent.
		assertTrue(entityManager.getEvents(ExplosionStartedEvent.class).isEmpty());
		
		//one more time (90 times total)
		updateSystems(1);
		//first BOOM!!!
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 1);
		
		//one more time (91 times total)
		updateSystems(1);
		//second BOOM!!!
		assertEquals(entityManager.getEvents(ExplosionStartedEvent.class).size(), 2);
		updateSystems(2);
		assertEquals(2, entityManager.getComponents(Explosion.class).size());
		
	}
	
	
	private void updateSystems(int numberOfInteractions) {
		for (int i=0; i<numberOfInteractions; i++) {
			timeSystem.update();
			bombSystem.update();
			explosionSystem.update();
		}
	}
	
	private Entity createDropperEntity() {
		
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
	
	private void pubBombOnGrid(int x, int y, BombDropper bombDropper) {
		
		CellPlacement placement = (CellPlacement) entityManager.getComponent(CellPlacement.class, bombDropper.getEntityId());
		
		placement.setCellX(x);
		placement.setCellY(y);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system
		bombSystem.update();
	}
	
}
