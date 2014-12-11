package br.unb.unbomber.systems;

/**
* @file BombSystemTestCaseG10.java
* @brief Teste de BombSystem realizado pelo grupo 10
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 09/12/2014
* @version 1.1
* Tests for {@link systems.BombSystemTestCaseG10}.
*/

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

	/** TESTE 1 */
	/*  Teste do construtor */
	@Test
	public void testeConstrutor() {
		
		BombSystem bombSystem = new BombSystem();
		bombSystem.update();
		
		assertNotNull(bombSystem);
		assertEquals(bombSystem.getClass(), BombSystem.class);
	}
	
	/** TESTE 2 */
	/*  Teste do construtor */
	@Test
	public void testeConstrutor2() {
		
		BombSystem bombSystem = new BombSystem(entityManager);
		bombSystem.update();
		
		assertNotNull(bombSystem);
		assertEquals(bombSystem.getClass(), BombSystem.class);
	}
	
	/** TESTE 3 */
	/*  Teste de criação de uma bomba genérica */
	@Test
	public void testeCreateGenericBomb() {
		
		Entity destroyer = entityManager.createEntity();
		BombDropper dropper = new BombDropper();
		CellPlacement dropperPlacement = new CellPlacement();
		
		dropperPlacement.setCellX(1);
		dropperPlacement.setCellY(1);
		
		destroyer.addComponent(dropperPlacement);
		destroyer.addComponent(dropper);
		entityManager.update(destroyer);
		
		List<Component> explosives = entityManager.getComponents(Explosive.class);
		
		BombSystem bombSystem = new BombSystem();
		Entity bomb = entityManager.createEntity();
		bomb = bombSystem.createGenericBomb(dropper);
		entityManager.update(bomb);
		
		/** Assert (foi criado 1 explosivo?)*/
		assertTrue( explosives.size() == 1 );
		/** Assert (o owner de dropper e de bomb é o mesmo?)*/
		assertEquals( dropper.getEntityId(), bomb.getOwnerId() );
	}
	
	/** TESTE 4 */
	/*  @brief Teste de verifyAndDropBomb
	 *  Análise do comportamento deste método quando se tenta
	 *  dropar uma bomba além do limite estabelecido */
	@Test
	public void testeVerifyAndDropBomb() {

		final int MAX_NUM_BOMBS = 2;
		
		CellPlacement dropperPlacement = new CellPlacement();
		Entity destroyer = entityManager.createEntity();
		BombDropper dropper = new BombDropper();
		BombSystem bombSystem = new BombSystem();
		
		dropper.setPermittedSimultaneousBombs(MAX_NUM_BOMBS);
		dropperPlacement.setCellX(1);
		dropperPlacement.setCellY(1);
		
		destroyer.addComponent(dropperPlacement);
		destroyer.addComponent(dropper);
		entityManager.update(destroyer);
		
		List<Component> explosives = (List<Component>) entityManager.getComponents(Explosive.class);
		
		/* 1ª bomba dropada */
		bombSystem.verifyAndDropBomb(dropper);
		/** Assert */
		assertEquals( 1, explosives.size());
		
		/* 2ª bomba dropada */
		bombSystem.verifyAndDropBomb(dropper);
		/** Assert */
		assertEquals( MAX_NUM_BOMBS, explosives.size()); //< Limite máximo permitido
		
		/* Tentativa de dropar 3ª bomba */
		bombSystem.verifyAndDropBomb(dropper);
		/** Assert */
		/* Não deve dropar mais bombas visto que atingiu o limite */
		assertEquals( MAX_NUM_BOMBS, explosives.size());
		
	}
}
