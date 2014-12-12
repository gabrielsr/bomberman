package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;

public class KickSystemTestCase {

	EntityManager entityManager;
	BombSystem bombSystem;
	KickSystem kickSystem;
	
	
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
		
		Entity anEntity = entityManager.createEntity();
		anEntity.addComponent(new BombDropper());
		anEntity.addComponent(new CellPlacement());
		((CellPlacement) anEntity.getComponents()).setCellX(10);
		((CellPlacement) anEntity.getComponents()).setCellY(15);
		
		kickSystem = new KickSystem();
		bombSystem = new BombSystem(entityManager);
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		entityManager.update(anEntity);
		
		bombSystem.verifyAndDropBomb(bombDropper);
		
		kickSystem.update();
		
		assertEquals(((CellPlacement) anEntity.getComponents()).getCellX(), ((CellPlacement) anEntity.getComponents()).getCellX() + 4);
	}
	
	@Test
	public void testCheckIfBomb1()	{
	
		
		kickSystem = new KickSystem();
			
		assertFalse(kickSystem.checkIfBomb(5));
	}
	
	@Test
	public void testCheckIfBomb2()	{
		Explosive explosive = new Explosive();
		explosive.setEntityId(10);
		entityManager.addComponent(explosive);
		assert(kickSystem.checkIfBomb(5));
	}
	
	@Test
	public void testCheckIfCanBombDrop1()	{
		BombDropper dropper = new BombDropper();
		assert(kickSystem.checkIfCanKickBombs(dropper));
	}
	
	
	@Test
	public void testCheckIfCanKickBombs()	{
		BombDropper bomber = new BombDropper();
		bomber.setEntityId(42);
		KickPowerUpEvent evento = new KickPowerUpEvent(42);
		entityManager.addEvent(evento);
		assert(kickSystem.checkIfCanKickBombs(bomber));
	}
	
	
	
}

}
