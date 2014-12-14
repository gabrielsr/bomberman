package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.EntityBuilder;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.Updatable;
import br.unb.unbomber.core.UpdateRunner;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;
import br.unb.unbomber.systems.query.Get;

@SuppressWarnings("deprecation")
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
		final Entity forrest = EntityBuilder.create(entityManager)
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
		movementSystem.update();

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
		movementSystem.update();

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
		movementSystem.update();

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