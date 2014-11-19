package br.unb.unbomber.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;

public class EntityManagerTestCase {

	EntityManager entityManager;

	int insertedEtityId;

	@Before
	public void setUp() throws Exception {
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();

		Entity anEntity = entityManager.createEntity();

		// Create Placement
		CellPlacement placement = new CellPlacement();
		placement.setCellX(42);
		placement.setCellY(44);

		// Add components
		anEntity.addComponent(placement);

		entityManager.update(anEntity);
		insertedEtityId = anEntity.getEntityId();
	}

	@Test
	public void lookUpComponentTest() {

		CellPlacement placement = (CellPlacement) entityManager.getComponent(
				CellPlacement.class, insertedEtityId);
		
		assertEquals(42, placement.getCellX());
	}
	
	@Test
	public void removeEntityTest() {

		entityManager.removeEntityById(insertedEtityId);
		
		CellPlacement placement = (CellPlacement) entityManager.getComponent(
				CellPlacement.class, insertedEtityId);
		
		assertNull("Should be null", placement);
	}
	

}
