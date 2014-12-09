/**
* @file BombSystemTestCaseG10.java
* @brief Teste de BombSystem realizado pelo grupo 10
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 09/12/2014
* @version 1.0
*/

//////////////////////////////////////////////////////
/// É necessário que na classe BombSystem os métodos
/// a serem testados tenham todos acesso public
//////////////////////////////////////////////////////

package br.unb.unbomber.systems;

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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;

public class BombSystemTestCaseG10 {

	EntityManager entityManager;
	BombSystem bombSystem;
	
	@Before
	public void setUp() throws Exception{

		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem = new BombSystem(entityManager);
	}
	
	@Test
	public void testeCreateGenericBomb() {

		Entity destroyer = entityManager.createEntity();
		BombDropper dropper = new BombDropper();
		CellPlacement dropperPlacement = new CellPlacement();
		
		dropperPlacement.setCellX(-1); //< Posicao invalida
		dropperPlacement.setCellY(-1);
		
		destroyer.addComponent(dropperPlacement);
		destroyer.addComponent(dropper);
		entityManager.update(destroyer);
		
		BombSystem bombSystem = new BombSystem();
		Entity entity = bombSystem.createGenericBomb(dropper);
		
		assertNull(entity);
	}

	@Test
	public void testeCreateGenericBomb2() {
		
		Entity destroyer = entityManager.createEntity();
		BombDropper dropper = new BombDropper();
		CellPlacement dropperPlacement = new CellPlacement();
		
		dropperPlacement.setCellX(5);
		dropperPlacement.setCellY(10);
		
		destroyer.addComponent(dropperPlacement);
		destroyer.addComponent(dropper);
		entityManager.update(destroyer);
		
		BombSystem bombSystem = new BombSystem();
		Entity entity = bombSystem.createGenericBomb(dropper);
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		
		Explosive explosive = (Explosive) explosives.get(0);
		CellPlacement bombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, explosive.getEntityId());
		assertEquals(bombPlacement.getCellX(), dropperPlacement.getCellX());
		assertEquals(bombPlacement.getCellY(), dropperPlacement.getCellY());
		
		assertNotNull(entity);
	}
	
	@Test
	public void testeVerifyAndDropBomb() {

		CellPlacement dropperPlacement = new CellPlacement();
		Entity destroyer = entityManager.createEntity();
		Explosive explosive = new Explosive();
		BombDropper dropper = new BombDropper();
		
		explosive.setExplosionRange(2);
		explosive.setOwnerId(dropper.getEntityId());
		dropperPlacement.setCellX(0);
		dropperPlacement.setCellY(0);
		
		destroyer.addComponent(dropperPlacement);
		destroyer.addComponent(dropper);
		destroyer.addComponent(explosive);
		entityManager.update(destroyer);
		
		BombSystem bombSystem = new BombSystem();
		bombSystem.verifyAndDropBomb(dropper);
	
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		assertNotNull( explosives );
	}

	@Test
	public void testeVerifyAndDropBomb2() {
		
		final int MAX_NUM_BOMBS = 5;

		CellPlacement dropperPlacement = new CellPlacement();
		Entity destroyer = entityManager.createEntity();
		Explosive explosive1 = new Explosive();
		Explosive explosive2 = new Explosive();
		Explosive explosive3 = new Explosive();
		Explosive explosive4 = new Explosive();
		Explosive explosive5 = new Explosive();
		BombDropper dropper  = new BombDropper();
		
		dropper.setPermittedSimultaneousBombs(MAX_NUM_BOMBS);
		
		explosive1.setExplosionRange(2);
		explosive1.setOwnerId(dropper.getEntityId());
		explosive2.setExplosionRange(2);
		explosive2.setOwnerId(dropper.getEntityId());
		explosive3.setExplosionRange(2);
		explosive3.setOwnerId(dropper.getEntityId());
		explosive4.setExplosionRange(2);
		explosive4.setOwnerId(dropper.getEntityId());
		explosive5.setExplosionRange(2);
		explosive5.setOwnerId(dropper.getEntityId());
		dropperPlacement.setCellX(5);
		dropperPlacement.setCellY(10);
		
		destroyer.addComponent(dropperPlacement);
		destroyer.addComponent(dropper);
		destroyer.addComponent(explosive1);
		destroyer.addComponent(explosive2);
		destroyer.addComponent(explosive3);
		destroyer.addComponent(explosive4);
		destroyer.addComponent(explosive5);
		entityManager.update(destroyer);
		
		List<Component> explosives = entityManager.getComponents(Explosive.class);
		int bombCounter = 0;
		
		if (explosives != null) {
			for (Component component : explosives) {
				Explosive explosive = (Explosive) component;
				if (explosive.getOwnerId() == dropper.getEntityId()) {
						bombCounter++;

				}
			}
		}
		
		BombSystem bombSystem = new BombSystem();		
		
		assertTrue( bombCounter == dropper.getPermittedSimultaneousBombs() );
	}
	
}
