package br.unb.unbomber.systems;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntitySystemImpl;

@Ignore
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
    public boolean detectExplosionCollisionTest() {

	/* create an explosion entity with components: explosion placement */
	Entity explosionEntity = new Entity();
	explosionEntity.setEntityId(1);
	entityManager.addEntity(explosionEntity);

	Explosion explosion = new Explosion();
	explosion.setEntityId(explosionEntity.getEntityId());
	explosion.setExplosionRange(3);
	explosion.setPropagationDirection(Direction.UP);
	entityManager.addComponent(explosion);

	CellPlacement explosionPlacement = new CellPlacement();
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

	CellPlacement hardBlockPlacement = new CellPlacement();
	hardBlockPlacement.setEntityId(hardBlock.getEntityId());
	hardBlockPlacement.setCellX(explosionPlacement.getCellX());
	hardBlockPlacement.setCellY(explosionPlacement.getCellY() + 1);
	entityManager.addComponent(hardBlockPlacement);

	/* checking if function is right for hardBlock */
	if (system.detectExplosionCollision(explosion, explosionPlacement) != false){
	    
	    return false;
	    
	}
	/* removing the hard block from the grid */
	entityManager.remove(hardBlock);

	/*
	 *  create a soft block with components to collide with explosion:
	 * * explosionBarrier
	 * * placement
	 */
	Entity softBlock = new Entity();
	softBlock.setEntityId(4);
	entityManager.addEntity(softBlock);

	explosionBarrier = new ExplosionBarrier();
	explosionBarrier.setEntityId(softBlock.getEntityId());
	explosionBarrier.setType(ExplosionBarrierType.STOPPER);
	entityManager.addComponent(explosionBarrier);

	CellPlacement softBlockPlacement = new CellPlacement();
	softBlockPlacement.setEntityId(softBlock.getEntityId());
	softBlockPlacement.setCellX(explosionPlacement.getCellX());
	softBlockPlacement.setCellY(explosionPlacement.getCellY() + 1);
	entityManager.addComponent(softBlockPlacement);

	/* checking if function is right for softBlock */
	if (system.detectExplosionCollision(explosion, explosionPlacement) != false){
	    
	    return false;
	}
	
	/* removing the softBlock from the grid */
	entityManager.remove(softBlock);

	/*
	 *  create a character with components to collide with explosion:
	 * * explosionBarrier
	 * * placement
	 */
	Entity character = new Entity();
	character.setEntityId(5);
	entityManager.addEntity(character);

	explosionBarrier = new ExplosionBarrier();
	explosionBarrier.setEntityId(character.getEntityId());
	explosionBarrier.setType(ExplosionBarrierType.PASS_THROUGH);
	entityManager.addComponent(explosionBarrier);

	CellPlacement characterPlacement = new CellPlacement();
	characterPlacement.setEntityId(character.getEntityId());
	characterPlacement.setCellX(explosionPlacement.getCellX());
	characterPlacement.setCellY(explosionPlacement.getCellY() + 1);
	entityManager.addComponent(characterPlacement);

	/* checking if function is right for character */
	if (system.detectExplosionCollision(explosion, explosionPlacement) != true)
	    return false;

	/* removing the character from the grid */
	entityManager.remove(character);

	/* if none failed, test is succesfull */
	return true;
    }

}
