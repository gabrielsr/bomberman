package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.entitysystem.Updatable;
import br.unb.entitysystem.UpdateRunner;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.EntityBuilder;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class MovementSystem2TestCase {
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
	public void unrestrictedMovementTest() {
		/** OK, lets do some physics */
		
		
		/** There was an entity in a complete empty world 
		 * with position 0,0
		 * with velocity  of 0.1 cells / tick, right direction*/			
		final Entity forrest = EntityBuilder.create(entityManager)
				.withPosition(0, 0)
				.withMovable(0.125f)
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
				.forThis(doCreateGoRightCommand)
				.forThis(movementSystem)
				.repeat(160)
				.times();
	
		/* execute the late command */
		movementSystem.update();
		
		/** How far should it go? */

		CellPlacement forrestPosition = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, forrest.getEntityId());
		
		/** where should it be? */
		
		assertEquals("Forrest position", 20 , forrestPosition.getCellX());
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
		
		assertEquals("Barrier", 1, barrier.getX().intValue());
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
				.withMovable(0.125f)
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


	@Test
	public void leftMovementConstraintTest(){
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
				.withPosition(4, 0)
				.withMovable(0.125f)
				.build();
		
		// like a system just to do right commands
		Updatable stepForrestToTheLeftCommandProducer = new Updatable() {
			
			@Override
			public void update() {
				final MovementCommandEvent actionCommand = new MovementCommandEvent(
						MovementType.MOVE_LEFT, forrest.getEntityId());

				entityManager.addEvent(actionCommand);
				
			}
		};
		
		//RUN Forrest, RUN!!!
		UpdateRunner
				.update()
				.forThis(movementSystem)
				.forThis(stepForrestToTheLeftCommandProducer)
				.repeat(150)
				.times();
		

		/** How far should it go? */

		CellPlacement forrestPosition = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, forrest.getEntityId());
		
		/** where should it be? */
		assertEquals("Forrest position", 3, forrestPosition.getCellX());

	}
	

	@Test
	public void restrictUpdateLeftRightTest(){

		/** 
		 *  Orig is (0.325 , 0). Displacement (0.25, 0)
		 *  If there is a block ahead the displacement should be 
		 *  limited to 0.125 so the entity don't pass the center
		 * */
		Vector2D<Float> origPosition = new Vector2D<Float>(0.5f - 0.125f, 0.0f);
		
		Vector2D<Float> displacement = new Vector2D<Float>(0.25f, 0.0f);

		Vector2D<Integer> barriers = new Vector2D<Integer>(1, 0);

		Vector2D<Float> displacementResult = movementSystem.restrictUpdate(origPosition,
				displacement, barriers);

		assertEquals("Restricted displacement",  0.125f, displacementResult.getX());
	}
	
	@Test
	public void restrictUpDownTest(){

		/** 
		 *  Orig is (0.4 , 0). Displacement (0.2, 0)
		 *  If there is a block ahead the displacement should be 
		 *  limited to 0.1 so the entity don't pass the center
		 * */
		
		Vector2D<Float> origPosition = new Vector2D<Float>(0.5f, 0.625f);
		
		Vector2D<Float> displacement = new Vector2D<Float>(0.2f, -0.25f);

		Vector2D<Integer> barriers = new Vector2D<Integer>(0, -1);

		Vector2D<Float> displacementResult = movementSystem.restrictUpdate(origPosition,
				displacement, barriers);

		assertEquals("Restricted displacement",  -0.125f, displacementResult.getY());
		assertEquals("Unrestricted displacement",  0.2f, displacementResult.getX());
	}

}