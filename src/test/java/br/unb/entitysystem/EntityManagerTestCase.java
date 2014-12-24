package br.unb.entitysystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.entitysystem.query.Get;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.misc.EntityBuilder;

public class EntityManagerTestCase {

	EntityManager entityManager;

	int insertedEtityId;

	@Before
	public void setUp() throws Exception {
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();

		Entity anEntity = entityManager.createEntity();

		// Create Placement
		Position placement = new Position();
		placement.setCellX(42);
		placement.setCellY(44);

		// Add components
		anEntity.addComponent(placement);

		entityManager.update(anEntity);
		insertedEtityId = anEntity.getEntityId();
	}

	@Test
	public void lookUpComponentTest() {

		Position placement = (Position) entityManager.getComponent(
				Position.class, insertedEtityId);
		
		assertEquals(42, placement.getCellX());
	}
	
	@Test
	public void removeEntityTest() {

		entityManager.removeEntityById(insertedEtityId);
		
		Position placement = (Position) entityManager.getComponent(
				Position.class, insertedEtityId);
		
		assertNull("Should be null", placement);
	}
	
	@Test
	public void builderAndSelectTest(){
		Entity bomber1 = EntityBuilder.create(entityManager)
				.withPosition(314, 273)
				.withMovable(0.1f)
				.build();
		
		Position cell = Get.from(bomber1).component(Position.class).now();

		
		assertEquals(314, cell.getCellX());
		assertEquals(273, cell.getCellY());
		
		
//		
//			.select(CellPlacement.class)
//			.from(bomber1);

	}

}
