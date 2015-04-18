package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;



import com.artemis.utils.EntityBuilder;





import br.unb.gridphysics.Vector2D;
//import br.unb.entitysystem.Entity;
//import br.unb.entitysystem.EntityManager;
//import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.MovementCommandEvent;

public class ThrowSystemTestCase {

	Entity entity;
	BombSystem bombSystem;
	ThrowSystem throwSystem;
	TimeSystem timeSystem;
	World world;
	
	@Before
	public void setUp() throws Exception {
		/*
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		bombSystem = new BombSystem(entityManager);
		timeSystem = new TimeSystem(entityManager);
		*/
		
		world = new World();
		world.setSystem(throwSystem);
		world.setSystem(timeSystem);
		world.setSystem(bombSystem);
		world.setManager(new EventManager());
		world.setManager(new UuidEntityManager());
	}
	
	//@Test
	/*public void testConstrutor() {
		throwSystem = new ThrowSystem();
		
		throwSystem.update();
		
		assert(true);
	}*/
	
	/**
	 * Checks if the position of the bomb thrown in an empty space
	 * is the expected one.
	 * 
	 * */
	
	@Test
	public void throwBombTest () {
		/*
		Entity anEntity = entityManager.createEntity(); 
		anEntity.addComponent(new BombDropper());
		anEntity.addComponent(new Position());
		((Position) anEntity.getComponents()).setCellX(10);
		((Position) anEntity.getComponents()).setCellY(15);
		entityManager.update(anEntity);
		
		throwSystem = new ThrowSystem();
		
		bombSystem = new BombSystem(entityManager);
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		
		entityManager.update(anEntity);
		
		bombDropper.setAreBombsHardPassThrough(true);
		bombDropper.setAreBombsPassThrough(true);
		
		bombSystem.verifyAndDropBomb(bombDropper);
		
		throwSystem.update(); *///jogou a bomba em qual direção;
		
		
		/** A system that drops bombs */
		world.setSystem(new VoidEntitySystem() {

			@Wire
			EventManager em;

			@Override
			protected void processSystem() {
				final ActionCommandEvent actionCommand = new ActionCommandEvent(
						ActionType.DROP_BOMB, entity.getUuid());
				em.dispatch(actionCommand);

			}
		});
		
		
		world.initialize();
		
		/** Character in position (0,0) in an empty world */
		entity = new EntityBuilder(world)
		.with(new Position(0, 0))
		.build();
		
		/** Sets direction */
		ComponentMapper<Movable> cmMovable = world.getMapper(Movable.class);
		Movable movable = cmMovable.getSafe(entity);
		movable.setFaceDirection(Direction.UP);
		
		/** Actually throws the bomb */
		world.setSystem(new VoidEntitySystem() {

			@Wire
			EventManager em;

			@Override
			protected void processSystem() {
				final ActionCommandEvent throwBomb = new ActionCommandEvent(ActionType.THROW,entity.getUuid());
				em.dispatch(throwBomb);

			}
		});
		world.process();
		
		/** Gets entity position */
		ComponentMapper<Position> cmPosition = world.getMapper(Position.class);
		Position entityPosition = cmPosition.get(entity);
		
		
		//throwSystem.throwEntity(entityManager, new Vector2D<Integer>(0,0),Direction.UP);
		/** Asserts that position of the thrown entity is as expected*/
		assertEquals("Forrest position", 3 , entityPosition.getCellX());
		//assertEquals(((Position) entity.getComponents()).getCellX(), ((Position) entity.getComponents()).getCellX() + 3);
	}
	
	public void thrownInASoftBlock (){
		/** A system that drops bombs */
		world.setSystem(new VoidEntitySystem() {

			@Wire
			EventManager em;

			@Override
			protected void processSystem() {
				final ActionCommandEvent actionCommand = new ActionCommandEvent(
						ActionType.DROP_BOMB, entity.getUuid());
				em.dispatch(actionCommand);

			}
		});
		
		
		world.initialize();
		
		/** Character in position (0,0) in an empty world */
		entity = new EntityBuilder(world)
		.with(new Position(0, 0))
		.build();
		
		/** Sets direction */
		ComponentMapper<Movable> cmMovable = world.getMapper(Movable.class);
		Movable movable = cmMovable.getSafe(entity);
		movable.setFaceDirection(Direction.UP);
		
		/** Creates a block*/
		new EntityBuilder(world).with(new Position(3, 0),
				new MovementBarrier(MovementBarrierType.BLOCKER)).build();
		
		/** Actually throws the bomb */
		world.setSystem(new VoidEntitySystem() {

			@Wire
			EventManager em;

			@Override
			protected void processSystem() {
				final ActionCommandEvent throwBomb = new ActionCommandEvent(ActionType.THROW,entity.getUuid());
				em.dispatch(throwBomb);

			}
		});
		world.process();
		
		/** Gets entity position */
		ComponentMapper<Position> cmPosition = world.getMapper(Position.class);
		Position entityPosition = cmPosition.get(entity);
		
		
		//throwSystem.throwEntity(entityManager, new Vector2D<Integer>(0,0),Direction.UP);
		/** Asserts that position of the thrown entity is as expected*/
		assertEquals("Forrest position", 4 , entityPosition.getCellX());
		//assertEquals(((Position) entity.getComponents()).getCellX(), ((Position) entity.getComponents()).getCellX() + 3);
	}
	

}
