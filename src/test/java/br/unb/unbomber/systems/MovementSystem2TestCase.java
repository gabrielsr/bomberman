package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.EntityBuilder;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.Updatable;
import br.unb.unbomber.core.UpdateRunner;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;
import br.unb.unbomber.gridphysics.Vector2D;

public class MovementSystem2TestCase extends MovimentSystem {
	/** Gerenciador das entidades. */
	EntityManager entityManager;

	/** Sistema de controle do Modulo Movement. */
	MovementSystem2 movementSystem;
	
	/** Sistema de controle do Modulo Collision. */
	CollisionSystem collisionSystem;

	@Before
	public void setUp() throws Exception {

		/** Inicia um sistema para cada caso de teste. */
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		movementSystem = new MovementSystem2(entityManager);
		collisionSystem = new CollisionSystem(entityManager);
	}


	@Test
	public void moveUp1CellAndCollideTest() {
		/** OK, lets do some physics */
		
		
		/** There was an entity in a complete empty world 
		 * with position 0,0
		 * with velocity  of 0.1 cells / tick, right direction*/			
		Entity forrest = EntityBuilder.create(entityManager)
				.withPosition(0, 0)
				.withMovable(0.1f)
				.build();

		/** And it moved RIGHT for 150 turns */ 
		
		
		// like a system just to do right commands
		Updatable doCreateGoRightCommand = new Updatable() {
			
			@Override
			public void update() {
				final MovementCommandEvent actionCommand = new MovementCommandEvent(
						MovementType.MOVE_RIGHT, forrest.getEntityId());

				/* adiciona o evento criado anteriormente a lista de eventos */
				entityManager.addEvent(actionCommand);
				
			}
		};
		
		//RUN forrest, RUN!!!
		UpdateRunner
				.update()
				.forThis(movementSystem)
				.forThis(doCreateGoRightCommand)
				.repeat(150)
				.times();
	
		/** How far should it go? */

		CellPlacement forrestPosition = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, forrest.getEntityId());
		
		/** where should it be? */
		
		assertEquals("Forrest position", 150*0.1f, forrestPosition.getCellX());
		/* verifica se o movimento feito foi realizado com sucesso */

	}
	


	@Test
	public void lookAheadRightAndThereIsABlockTest() {
	
		Vector2D<Integer> actualPosition = new Vector2D<Integer>(0, 0);
		Vector2D<Integer> rightDisplacement = new Vector2D<Integer>(1, 0);
		
		
		EntityBuilder.create(entityManager)
				.withPosition(1, 0)
				.withMovementBarrier(MovementBarrierType.BLOCKER)
				.build();
		
		Vector2D<Integer> barrier = movementSystem.lookAhead(actualPosition, rightDisplacement);
		
		//assertEquals("Barrier", 1, barrier.getX());
	}

	@Test
	public void rightMovementConstraintTest(){
		/**  In the middle of the road there was a stone,
		 *  there was a stone in the middle of the road...
		 * 
		 *  entity with position 0,0
		 * with velocity  of 0.1 cells / tick, right direction
		 * 
		 * A block at position 2,0 */
		
		EntityBuilder.create(entityManager)
				.withPosition(2, 0)
				.withMovementBarrier(MovementBarrierType.BLOCKER)
				.build();
		
		Entity forrest = EntityBuilder.create(entityManager)
				.withPosition(0, 0)
				.withMovable(0.1f)
				.build();
		
		// like a system just to do right commands
		Updatable stepForrestToTheRightCommandProduer = new Updatable() {
			
			@Override
			public void update() {
				final MovementCommandEvent actionCommand = new MovementCommandEvent(
						MovementType.MOVE_RIGHT, forrest.getEntityId());

				entityManager.addEvent(actionCommand);
				
			}
		};
		
		//RUN Forrest, RUN!!!
		UpdateRunner
				.update()
				.forThis(movementSystem)
				.forThis(stepForrestToTheRightCommandProduer)
				.repeat(150)
				.times();
		

		/** How far should it go? */

		CellPlacement forrestPosition = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, forrest.getEntityId());
		
		/** where should it be? */
		assertEquals("Forrest position", 1, forrestPosition.getCellX());

	}
}