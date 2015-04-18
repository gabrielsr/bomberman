package br.unb.unbomber.systems;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntitySystemImpl;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;

public class ExplosionSystemTestCase {

	EntityManager entityManager;
	ExplosionSystem system;

	@Before
	public void setUp() {

		/* init a new system for each test case */
		EntitySystemImpl.init();
		entityManager = EntitySystemImpl.getInstance();
		system = new ExplosionSystem(entityManager);

	}

	@Test
	public void detectExplosionCollisionTest() {

		/* create an explosion entity with components: explosion placement */
		Entity explosionEntity = new Entity();
		explosionEntity.setEntityId(1);
		entityManager.addEntity(explosionEntity);

		Explosion explosion = new Explosion();
		explosion.setEntityId(explosionEntity.getEntityId());
		explosion.setExplosionRange(3);
		explosion.setPropagationDirection(Direction.UP);
		entityManager.addComponent(explosion);

		Position explosionPlacement = new Position();
		explosionPlacement.setEntityId(explosionEntity.getEntityId());
		explosionPlacement.setCellX(5);
		explosionPlacement.setCellY(5);
		entityManager.addComponent(explosionPlacement);

		/*
		 * create a hard block with components to collide with explosion:
		 * explosionBarrier placement
		 */

		Entity hardBlock = new Entity();
		hardBlock.setEntityId(2);
		entityManager.addEntity(hardBlock);

		ExplosionBarrier explosionBarrier = new ExplosionBarrier();
		explosionBarrier.setEntityId(hardBlock.getEntityId());
		explosionBarrier.setType(ExplosionBarrierType.BLOCKER);
		entityManager.addComponent(explosionBarrier);

		Position hardBlockPlacement = new Position();
		hardBlockPlacement.setEntityId(hardBlock.getEntityId());
		hardBlockPlacement.setCellX(explosionPlacement.getCellX());
		hardBlockPlacement.setCellY(explosionPlacement.getCellY() + 1);
		entityManager.addComponent(hardBlockPlacement);

		/* checking if function is right for hardBlock */
		assertFalse(system.detectExplosionCollision(explosion,
				explosionPlacement));

		/* removing the hard block from the grid */
		entityManager.remove(hardBlock);

		/*
		 * create a soft block with components to collide with explosion: *
		 * explosionBarrier * placement
		 */
		Entity softBlock = new Entity();
		softBlock.setEntityId(4);
		entityManager.addEntity(softBlock);

		explosionBarrier = new ExplosionBarrier();
		explosionBarrier.setEntityId(softBlock.getEntityId());
		explosionBarrier.setType(ExplosionBarrierType.STOPPER);
		entityManager.addComponent(explosionBarrier);

		Position softBlockPlacement = new Position();
		softBlockPlacement.setEntityId(softBlock.getEntityId());
		softBlockPlacement.setCellX(explosionPlacement.getCellX());
		softBlockPlacement.setCellY(explosionPlacement.getCellY() + 1);
		entityManager.addComponent(softBlockPlacement);

		/* checking if function is right for softBlock */
		assertFalse(system.detectExplosionCollision(explosion, explosionPlacement));

		/* removing the softBlock from the grid */
		entityManager.remove(softBlock);

		/*
		 * create a character with components to collide with explosion: *
		 * explosionBarrier * placement
		 */
		Entity character = new Entity();
		character.setEntityId(5);
		entityManager.addEntity(character);

		explosionBarrier = new ExplosionBarrier();
		explosionBarrier.setEntityId(character.getEntityId());
		explosionBarrier.setType(ExplosionBarrierType.PASS_THROUGH);
		entityManager.addComponent(explosionBarrier);

		Position characterPlacement = new Position();
		characterPlacement.setEntityId(character.getEntityId());
		characterPlacement.setCellX(explosionPlacement.getCellX());
		characterPlacement.setCellY(explosionPlacement.getCellY() + 1);
		entityManager.addComponent(characterPlacement);

		/* checking if function is right for character */
		assertTrue(!system.detectExplosionCollision(explosion, explosionPlacement));


		/* removing the character from the grid */
		entityManager.remove(character);

		/* if none failed, test is succesfull */
		assertTrue(true);
	}

}
