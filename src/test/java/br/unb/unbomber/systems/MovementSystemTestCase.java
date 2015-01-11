package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.misc.EntityBuilder2;
import br.unb.unbomber.misc.UpdateRunner;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.EntityBuilder;

public class MovementSystemTestCase {
	
	World world;
	
	GridSystem gridSystem;

	MovementSystem movementSystem;
	
	Entity forest;
	
	@Before
	public void setUp() throws Exception {
		gridSystem = new GridSystem();
		movementSystem = new MovementSystem();
		
		world = new World();
		world.setSystem(gridSystem);
		world.setSystem(movementSystem);
		
		world.setManager(new EventManager());
		
		/** 
		 * do not call world.initialize();
		 *  it will be called by each test 
		 *  after finish the world preparation
		 */
	}


	@Test
	public void unrestrictedMovementTest() {


		// a system that create movement commands
		world.setSystem(new VoidEntitySystem() {

			@Wire
			EventManager em;

			@Override
			protected void processSystem() {
				final MovementCommandEvent actionCommand = new MovementCommandEvent(
						Direction.RIGHT, forest.getUuid());
				em.dispatch(actionCommand);

			}
		});
		
		world.initialize();
		

		/** 
		 * There is Forest in a complete empty world 
		 * with position 0,0
		 * with speed of 1/8 cells / tick 
		 */

		forest = new EntityBuilder(world)
			.with(new Position(0, 0), new Movable(0.125f))
			.build();
		
		//run Forest, RUN!!!

		UpdateRunner
			.update()
			.forThis(world)
			.repeat(160)
			.times();
	

		/** How far went it? */

		ComponentMapper<Position> cmPosition = world.getMapper(Position.class);
		Position forrestPosition = cmPosition.get(forest);

		assertEquals("Forrest position", 20 , forrestPosition.getCellX());
	}
	


	@Test
	public void lookAheadRightAndThereIsABlockTest() {
	
		Vector2D<Integer> actualPosition = new Vector2D<Integer>(0, 4);
		Vector2D<Integer> rightDisplacement = new Vector2D<Integer>(1, 0);
		
		world.initialize();
		
		/** create block */
		new EntityBuilder(world)
		 .with(new Position(1,4), 
				 new MovementBarrier(MovementBarrierType.BLOCKER))
		 .build();
		
		MovementSystem movementSystem = world.getSystem(MovementSystem.class);
		
		/** call to create a map in GridSystem */
		world.process();
		
		/** look */
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


		// a system that create movement commands
		world.setSystem(new VoidEntitySystem() {

			@Wire
			EventManager em;

			@Override
			protected void processSystem() {
				final MovementCommandEvent actionCommand = new MovementCommandEvent(
						Direction.RIGHT, forest.getUuid());
				em.dispatch(actionCommand);

			}
		});
		
		world.initialize();
		
		new EntityBuilder(world)
		 .with(new Position(2,0), 
				 new MovementBarrier(MovementBarrierType.BLOCKER))
		 .build();
		
		forest = new EntityBuilder(world)
				.with(new Position(0,0), 
					  new Movable(0.125f))
				.build();
		
		//RUN Forrest, RUN!!!
		UpdateRunner
				.update()
				.forThis(world)
				.repeat(160)
				.times();
		

		/** How far should it go? */

		ComponentMapper<Position> cmPosition = world.getMapper(Position.class);
		Position forrestPosition = cmPosition.get(forest);

		
		/** where should it be? */
		assertEquals("Forrest position", 1 , forrestPosition.getCellX());

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
		

		// a system that create movement commands
		world.setSystem(new VoidEntitySystem() {

			@Wire
			EventManager em;

			@Override
			protected void processSystem() {
				final MovementCommandEvent actionCommand = new MovementCommandEvent(
						Direction.LEFT, forest.getUuid());
				em.dispatch(actionCommand);

			}
		});
		
		world.initialize();
		
		EntityBuilder2.create(world)
				.withPosition(2, 0)
				.withMovementBarrier(MovementBarrierType.BLOCKER)
				.build();
		
		forest = EntityBuilder2.create(world)
				.withPosition(4, 0)
				.withMovable(0.125f)
				.build();
		
		
		//RUN Forrest, RUN!!!
		UpdateRunner
				.update()
				.forThis(world)
				.repeat(160)
				.times();
		

		/** How far should it go? */

		ComponentMapper<Position> cmPosition = world.getMapper(Position.class);
		Position forrestPosition = cmPosition.get(forest);
		
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

		world.initialize();
		
		Vector2D<Float> displacementResult = movementSystem.restrictDisplacementTowardABlockedCell(origPosition,
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

		Vector2D<Float> displacementResult = movementSystem.restrictDisplacementTowardABlockedCell(origPosition,
				displacement, barriers);

		assertEquals("Restricted displacement",  -0.125f, displacementResult.getY());
		assertEquals("Unrestricted displacement",  0.2f, displacementResult.getX());
	}


	/** And it moved RIGHT for 150 turns */ 		
	class DoCreateGoRightCommand extends VoidEntitySystem {
		
		Entity toMove;
		
		public DoCreateGoRightCommand(Entity toMove){
			this.toMove = toMove;
		}
		
		@Wire
		EventManager em;
	
		@Override
		protected void processSystem() {
			final MovementCommandEvent actionCommand = new MovementCommandEvent(
					Direction.RIGHT, toMove.getUuid());

			/* adiciona o evento criado anteriormente a lista de eventos */
			em.dispatch(actionCommand);
			
		}
	};
	
}