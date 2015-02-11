package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import com.artemis.World;
import com.artemis.Entity;

//import br.unb.entitysystem.Entity;
//import br.unb.entitysystem.EntityManager;
//import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Position;

public class ThrowSystemTestCase {

	Entity entityManager;
	BombSystem bombSystem;
	ThrowSystem throwSystem;
	TimeSystem timeSystem;
	World world;
	
	@Before
	public void setUp() throws Exception {
		/*
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem = new BombSystem(entityManager);
		timeSystem = new TimeSystem(entityManager);
		*/
		
		world = new World();
		world.setSystem(throwSystem);
		world.setSystem(timeSystem);
		world.setSystem(bombSystem);
		world.setManager(new EventManager());
	}
	
	//@Test
	/*public void testConstrutor() {
		throwSystem = new ThrowSystem();
		
		throwSystem.update();
		
		assert(true);
	}*/
	
	@Test
	public void throwBombTest () {
		
		Entity anEntity = entityManager.createEntity(); 
		anEntity.addComponent(new BombDropper());
		anEntity.addComponent(new Position());
		((Position) anEntity.getComponents()).setCellX(10);
		((Position) anEntity.getComponents()).setCellY(15);
		entityManager.update(anEntity);
		
		throwSystem = new ThrowSystem();
		
		bombSystem = new BombSystem(entityManager);
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		entityManager.update(anEntity);
		
		bombDropper.setAreBombsHardPassThrough(true);
		bombDropper.setAreBombsPassThrough(true);
		
		bombSystem.verifyAndDropBomb(bombDropper);
		
		throwSystem.update(); //jogou a bomba em qual direção;
		
		world.initialize();
		
		assertEquals(((Position) anEntity.getComponents()).getCellX(), ((Position) anEntity.getComponents()).getCellX() + 5);
	}
	

}
