package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;

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
	
	@Test
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
		((CellPlacement) anEntity.getComponents()).setCellX(10);
		((CellPlacement) anEntity.getComponents()).setCellY(15);
		entityManager.update(anEntity);
		
		throwSystem = new ThrowSystem();
		
		bombSystem = new BombSystem(entityManager);
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		entityManager.update(anEntity);
		
		bombDropper.setAreBombsHardPassThrough(true);
		bombDropper.setAreBombsPassThrough(true);
		
		bombSystem.verifyAndDropBomb(bombDropper);
		
		throwSystem.update(); //jogou a bomba em qual direção;
		
		assertEquals(((CellPlacement) anEntity.getComponents()).getCellX(), ((CellPlacement) anEntity.getComponents()).getCellX() + 5);
	}
	

}
