package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;

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
		/* inicializa um novo sistema pra cada teste */
		EntitySystemImpl.init();
		entityManager = EntitySystemImpl.getInstance();
		system = new MovimentSystem();
	}

	@Test
	public void MoveUpTest() {
		Entity anEntity = new Entity();
		/* recebe o atual posicionamento da entidade */
		CellPlacement originalPlacement = new CellPlacement();

		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;

		/* atribui um posicionamento valido para a entidade */
		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);

		/* atribui uma velocidade valida para entidade */
		Movable movable = new Movable();
		movable.setSpeed(SPEED);

		/* adicona os componentes de posicionamento e mobilidade para a entidade */
		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		/* insere a entidade definida anteriormente no jogo */
		entityManager.addEntity(anEntity);

		/* cria um evento valido de movimento do tipo MovementType.Mouve_UP */
		MovementCommandEvent actionCommand = new MovementCommandEvent(
				MovementType.MOVE_UP, movable.getEntityId());

		/* adiciona o evento criado anteriormente a lista de eventos */
		entityManager.addEvent(actionCommand);

		/* roda as atualizacoes feitas */
		system.update();

		/* cria uma lista de componentes do tipo Movable */
		List<Component> moves = (List<Component>) entityManager
				.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(
				MovementType.MOVE_UP, actionCommand.getEntityId());
		entityManager.addEvent(event);

		Component move = moves.get(0);
		CellPlacement createEntityPlacement = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, move.getEntityId());

		/* verifica se o movimento feito foi realizado com sucesso */
		assertEquals(CELL_X, createEntityPlacement.getCellX());
		assertEquals(CELL_Y + SPEED, createEntityPlacement.getCellY());

	}

	@Test
	public void MoveDownTest() {
		Entity anEntity = new Entity();
		/* recebe o atual posicionamento da entidade */
		CellPlacement originalPlacement = new CellPlacement();

		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;

		/* atribui um posicionamento valido para a entidade */
		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);

		/* atribui uma velocidade valida para entidade */
		Movable movable = new Movable();
		movable.setSpeed(SPEED);

		/* adicona os componentes de posicionamento e mobilidade para a entidade */
		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		/* insere a entidade definida anteriormente no jogo */
		entityManager.addEntity(anEntity);

		/* cria um evento valido de movimento do tipo MovementType.Mouve_DOWN */
		MovementCommandEvent actionCommand = new MovementCommandEvent(
				MovementType.MOVE_DOWN, movable.getEntityId());

		/* adiciona o evento criado anteriormente a lista de eventos */
		entityManager.addEvent(actionCommand);

		/* roda as atualizacoes feitas */
		system.update();

		/* cria uma lista de componentes do tipo Movable */
		List<Component> moves = (List<Component>) entityManager
				.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(
				MovementType.MOVE_DOWN, actionCommand.getEntityId());
		entityManager.addEvent(event);

		Component move = moves.get(0);
		CellPlacement createEntityPlacement = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, move.getEntityId());

		/* verifica se o movimento feito foi realizado com sucesso */
		assertEquals(CELL_X, createEntityPlacement.getCellX());
		assertEquals(CELL_Y - SPEED, createEntityPlacement.getCellY());

	}

	@Test
	public void MoveRightTest() {
		Entity anEntity = new Entity();
		/* recebe o atual posicionamento da entidade */
		CellPlacement originalPlacement = new CellPlacement();

		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;

		/* atribui um posicionamento valido para a entidade */
		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);

		/* atribui uma velocidade valida para entidade */
		Movable movable = new Movable();
		movable.setSpeed(SPEED);

		/* adicona os componentes de posicionamento e mobilidade para a entidade */
		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		/* insere a entidade definida anteriormente no jogo */
		entityManager.addEntity(anEntity);

		/* cria um evento valido de movimento do tipo MovementType.Mouve_RIGTH */
		MovementCommandEvent actionCommand = new MovementCommandEvent(
				MovementType.MOVE_RIGHT, movable.getEntityId());

		/* adiciona o evento criado anteriormente a lista de eventos */
		entityManager.addEvent(actionCommand);

		/* roda as atualizacoes feitas */
		system.update();

		/* cria uma lista de componentes do tipo Movable */
		List<Component> moves = (List<Component>) entityManager
				.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(
				MovementType.MOVE_DOWN, actionCommand.getEntityId());
		entityManager.addEvent(event);

		Component move = moves.get(0);
		CellPlacement createEntityPlacement = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, move.getEntityId());

		/* verifica se o movimento feito foi realizado com sucesso */
		assertEquals(CELL_X + SPEED, createEntityPlacement.getCellX());
		assertEquals(CELL_Y, createEntityPlacement.getCellY());

	}

	@Test
	public void MoveLeftTest() {
		Entity anEntity = new Entity();
		/* recebe o atual posicionamento da entidade */
		CellPlacement originalPlacement = new CellPlacement();

		int CELL_X = 5;
		int CELL_Y = 5;
		int SPEED = 3;

		/* atribui um posicionamento valido para a entidade */
		originalPlacement.setCellX(CELL_X);
		originalPlacement.setCellY(CELL_Y);

		/* atribui uma velocidade valida para entidade */
		Movable movable = new Movable();
		movable.setSpeed(SPEED);

		/* adicona os componentes de posicionamento e mobilidade para a entidade */
		anEntity.addComponent(movable);
		anEntity.addComponent(originalPlacement);

		/* insere a entidade definida anteriormente no jogo */
		entityManager.addEntity(anEntity);

		/* cria um evento valido de movimento do tipo MovementType.Mouve_LEFT */
		MovementCommandEvent actionCommand = new MovementCommandEvent(
				MovementType.MOVE_LEFT, movable.getEntityId());

		/* adiciona o evento criado anteriormente a lista de eventos */
		entityManager.addEvent(actionCommand);

		/* roda as atualizacoes feitas */
		system.update();

		/* cria uma lista de componentes do tipo Movable */
		List<Component> moves = (List<Component>) entityManager
				.getComponents(Movable.class);

		MovementCommandEvent event = new MovementCommandEvent(
				MovementType.MOVE_DOWN, actionCommand.getEntityId());
		entityManager.addEvent(event);

		Component move = moves.get(0);
		CellPlacement createEntityPlacement = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, move.getEntityId());

		/* verifica se o movimento feito foi realizado com sucesso */
		assertEquals(CELL_X - SPEED, createEntityPlacement.getCellX());
		assertEquals(CELL_Y, createEntityPlacement.getCellY());

	}

}