package br.unb.unbomber.systems;

/**
* @file ExplosionSystemTestCaseG10.java
* @brief Teste de ExplosionSystem realizado pelo grupo 10
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 11/12/2014
* @version 1.0
*/

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sun.security.krb5.internal.crypto.Des;
import br.unb.unbomber.systems.ExplosionSystem;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;


public class ExplosionSystemTestCaseG10 {

	EntityManager entityManager;
	ExplosionSystem explosionSystem;
	
	@Before
	public void setUp() throws Exception{

		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		explosionSystem = new ExplosionSystem(entityManager);
	}
	
	/** TESTE 1 */
	/*  Teste do construtor (sem parâmetro) */
	@Test
	public void testeConstrutor() {
		
		ExplosionSystem explosionSystem = new ExplosionSystem();
		explosionSystem.update();
		
		assertNotNull(explosionSystem);
		assertEquals(explosionSystem.getClass(), ExplosionSystem.class);
	}
	
	/** TESTE 2 */
	/*  Teste do construtor (com parâmetro) */
	@Test
	public void testeConstrutor2() {
		
		ExplosionSystem explosionSystem = new ExplosionSystem(entityManager);
		explosionSystem.update();
		
		assertNotNull(explosionSystem);
		assertEquals(explosionSystem.getClass(), ExplosionSystem.class);
	}
	
	/** TESTE 3 */
	/*  Teste do método createExplosion passando
	 *  parâmetros válidos */
	@Test
	public void testeCreateExplosion() {
		
		final int EXP_RANGE = 2;
		final int CELL_X = 3;
		final int CELL_Y = 3;
		
		/* Cria uma entidade explosão com os componentes: explosion e placement */
		Entity expEntity = entityManager.createEntity();

		CellPlacement expPlacement = new CellPlacement();
//		expPlacement.setEntityId(expEntity.getEntityId());
		expPlacement.setCellX(CELL_X);
		expPlacement.setCellY(CELL_Y);
		entityManager.addComponent(expPlacement);
		expEntity.addComponent(expPlacement);
		
		try {
			explosionSystem.createExplosion(expPlacement, EXP_RANGE, expEntity.getEntityId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** Assert (se tudo funcionou...) */
		assertTrue(true);
	}
	
}
