package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;

public class ThrowSystemTestCase {

	EntityManager entityManager;
	BombSystem bombSystem;
	ThrowSystem throwSystem;
	TimeSystem timeSystem;
	
	@Before
	public void setUp() throws Exception {
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem = new BombSystem(entityManager);
		timeSystem = new TimeSystem(entityManager);
		
	}
	
	//@Test
	public void testConstrutor() {
		throwSystem = new ThrowSystem();
		
		throwSystem.update();
		
		assert(true);
	}
	
	@Test
	public void throwBombTest () {
		
		Entity anEntity = entityManager.createEntity(); 
		anEntity.addComponent(new BombDropper());
		anEntity.addComponent(new CellPlacement());
		anEntity.addComponent(new PowerUp(PowerType.BOXINGGLOVEACQUIRED));
		entityManager.update(anEntity);
		((CellPlacement) entityManager.getComponent(CellPlacement.class, anEntity.getEntityId())).setCellX(10);
		((CellPlacement) entityManager.getComponent(CellPlacement.class, anEntity.getEntityId())).setCellY(15);
		
		throwSystem = new ThrowSystem();
		
		bombSystem = new BombSystem(entityManager);
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		entityManager.update(anEntity);
		
		bombDropper.setAreBombsHardPassThrough(true);
		bombDropper.setAreBombsPassThrough(true);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		throwSystem.update(); //jogou a bomba em qual direção;
		
		assertEquals(((CellPlacement) entityManager.getComponent(CellPlacement.class, anEntity.getEntityId())).getCellX(), 15);
	}
	

}
