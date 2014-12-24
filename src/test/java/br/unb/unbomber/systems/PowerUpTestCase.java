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

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.InAnExplosionEvent;

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
		LifeType newType = new LifeType(Type.POWER_UP); /**< instancia um novo lifetype */
		
		Entity entity = entityManager.createEntity(); /**< instacia uma nova entidade */
		entity.addComponent(newType); /**< adiciona a componente newtype para a entidade instacniada previamente */
		entityManager.update(entity); /**< adiciona a entidade para as entidades do entityManager */
		
		InAnExplosionEvent explosion = new InAnExplosionEvent(); /**< instancia um novo inAnExplosionEvent */
		explosion.setIdHit(entity.getEntityId()); /**< seta o idhit do explosion */
		entityManager.addEvent(explosion); /**< adiciona o evento explosion no entityManager */
			
		assertTrue(powerUpSystem.isPowerUpExplosion(explosion)); /**< realiza a acertiva */
	}
	
	@Test
	public void isBlockExplosionTest(){
		LifeType newType = new LifeType(Type.SOFT_BLOCK); /**< instancia um novo lifetype */
		
		Entity entity = entityManager.createEntity();; /**< instacia uma nova entidade */
		entity.addComponent(newType); /**< adiciona a componente newtype para a entidade instacniada previamente */
		entityManager.update(entity); /**< adiciona a entidade para as entidades do entityManager */
		
		InAnExplosionEvent explosion = new InAnExplosionEvent(); /**< instancia um novo inAnExplosionEvent */
		explosion.setIdHit(entity.getEntityId()); /**< seta o idhit do explosion */
		entityManager.addEvent(explosion); /**< adiciona o evento explosion no entityManager */
			
		assertTrue(powerUpSystem.isBlockExplosion(explosion)); /**< realiza a acertiva */
	}
	
	@Test
	public void checkCollisionTest(){

		int x = 5;
		int y = 5;
		
		LifeType lifePower = new LifeType(Type.POWER_UP);
		
		LifeType lifeChar = new LifeType(Type.CHAR);
		
		Entity newEntityChar = entityManager.createEntity();
		Position charPlacement = new Position();
		charPlacement.setCellX(x);
		charPlacement.setCellY(y);
		
		newEntityChar.addComponent(charPlacement);
		newEntityChar.addComponent(lifeChar);
		
		entityManager.update(newEntityChar);
		
		Entity newEntityPower = entityManager.createEntity();
		Position powerPlacement = new Position();
		powerPlacement.setCellX(x);
		powerPlacement.setCellY(y);

		newEntityPower.addComponent(powerPlacement);
		newEntityPower.addComponent(lifePower);
		
		entityManager.update(newEntityPower);
		
		CollisionEvent collision = new CollisionEvent(newEntityChar.getEntityId(), 
				newEntityPower.getEntityId());
		
		entityManager.addEvent(collision);
		
		assertTrue(powerUpSystem.checkCollision(collision));
	}

	
	
	
}
