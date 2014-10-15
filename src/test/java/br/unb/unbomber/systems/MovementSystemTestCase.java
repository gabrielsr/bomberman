package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntitySystemImpl;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class MovementSystemTestCase extends MovimentSystem {

	EntityManager entityManager;
	MovimentSystem system;

	@Before
	public void setUp() throws Exception {


		EntitySystemImpl.init();
		entityManager = EntitySystemImpl.getInstance();
		system = new MovimentSystem();
	}


	@Test
	public void MoveUpTest(){
		Entity anEntity = new Entity();

		CellPlacement originalPlacement = new CellPlacement();


		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;

		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);



		Movable movable = new Movable();
		movable.setSpeed(SPEED);


		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		entityManager.addEntity(anEntity); 

		MovementCommandEvent actionCommand = new MovementCommandEvent(MovementType.MOVE_UP , movable.getEntityId() );

		entityManager.addEvent(actionCommand);

		system.update();

		List<Component> moves = (List<Component>) entityManager.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(MovementType.MOVE_UP, actionCommand.getEntityId());
		entityManager.addEvent(event);



		Component move = moves.get(0);
		CellPlacement createBombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, move.getEntityId());


		assertEquals(CELL_X, createBombPlacement.getCellX());
		assertEquals(CELL_Y+SPEED, createBombPlacement.getCellY());

	}



	@Test
	public void MoveDownTest(){
		Entity anEntity = new Entity();

		CellPlacement originalPlacement = new CellPlacement();


		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;

		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);



		Movable movable = new Movable();
		movable.setSpeed(SPEED);


		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		entityManager.addEntity(anEntity); 

		MovementCommandEvent actionCommand = new MovementCommandEvent(MovementType.MOVE_DOWN , movable.getEntityId() );

		entityManager.addEvent(actionCommand);

		system.update();

		List<Component> moves = (List<Component>) entityManager.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(MovementType.MOVE_DOWN, actionCommand.getEntityId());
		entityManager.addEvent(event);



		Component move = moves.get(0);
		CellPlacement createBombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, move.getEntityId());



		assertEquals(CELL_X, createBombPlacement.getCellX());
		assertEquals(CELL_Y-SPEED, createBombPlacement.getCellY());

	}


	@Test
	public void MoveRightTest(){
		Entity anEntity = new Entity();

		CellPlacement originalPlacement = new CellPlacement();


		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;


		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);



		Movable movable = new Movable();
		movable.setSpeed(SPEED);


		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		entityManager.addEntity(anEntity); 

		MovementCommandEvent actionCommand = new MovementCommandEvent(MovementType.MOVE_RIGHT , movable.getEntityId() );

		entityManager.addEvent(actionCommand);

		system.update();

		List<Component> moves = (List<Component>) entityManager.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(MovementType.MOVE_RIGHT, actionCommand.getEntityId());
		entityManager.addEvent(event);



		Component move = moves.get(0);
		CellPlacement createBombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, move.getEntityId());



		assertEquals(CELL_X+SPEED, createBombPlacement.getCellX());
		assertEquals(CELL_Y, createBombPlacement.getCellY());

	}



	@Test
	public void MoveLeftTest(){
		Entity anEntity = new Entity();

		CellPlacement originalPlacement = new CellPlacement();


		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;


		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);



		Movable movable = new Movable();
		movable.setSpeed(SPEED);


		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		entityManager.addEntity(anEntity); 

		MovementCommandEvent actionCommand = new MovementCommandEvent(MovementType.MOVE_LEFT , movable.getEntityId() );

		entityManager.addEvent(actionCommand);

		system.update();

		List<Component> moves = (List<Component>) entityManager.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(MovementType.MOVE_LEFT, actionCommand.getEntityId());
		entityManager.addEvent(event);



		Component move = moves.get(0);
		CellPlacement createBombPlacement = (CellPlacement) entityManager.getComponent(CellPlacement.class, move.getEntityId());



		assertEquals(CELL_X-SPEED, createBombPlacement.getCellX());
		assertEquals(CELL_Y, createBombPlacement.getCellY());

	}

}