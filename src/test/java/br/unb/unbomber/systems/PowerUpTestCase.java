/**
 * @file PowerUpCase.java 
 * @brief Este modulo dos testes primarios do mudolo ThrowSystem,  
 * criado pelo grupo 6 da turma de programacao sistematica 2-2014 ministrada pela professora genaina
 *
 * @author Igor Chaves Sodre
 * @author Pedro Borges Pio
 * @author Kilmer Luiz Aleluia
 * @since 01/10/2014
 * @version 1.0
 */

package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.EntitySystemImpl;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class PowerUpTestCase {
	
	EntityManager entityManager;
	PowerUpSystem powerUpSystem;
	CollisionSystem collisionSystem;
	
	@Before
	public void setUp() throws Exception {
		//init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		collisionSystem = new CollisionSystem();
		powerUpSystem = new PowerUpSystem(entityManager);
	}
	
	/** @brief testa se ele esta verificando o evento inAnExpolsion corretamente 
	 * 
	 *  */
	@Test
	public void isPowerUpExplosionTest(){
		int BLOCK_ID = entityManager.getUniqueId(); /**< pega novos id para as entidades */
		int entityId = entityManager.getUniqueId(); 
		
		LifeType newType = new LifeType(Type.POWER_UP); /**< instancia um novo lifetype */
		
		Entity entity = new Entity(entityId); /**< instacia uma nova entidade */
		entity.addComponent(newType); /**< adiciona a componente newtype para a entidade instacniada previamente */
		entityManager.addEntity(entity); /**< adiciona a entidade para as entidades do entityManager */
		
		InAnExplosionEvent explosion = new InAnExplosionEvent(); /**< instancia um novo inAnExplosionEvent */
		explosion.setIdHit(entityId); /**< seta o idhit do explosion */
		entityManager.addEvent(explosion); /**< adiciona o evento explosion no entityManager */
			
		assertTrue(powerUpSystem.isPowerUpExplosion(explosion)); /**< realiza a acertiva */
	}
	

	
	
	
}
