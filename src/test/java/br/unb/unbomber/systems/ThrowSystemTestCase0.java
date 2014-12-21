/**
 * @file ThrowSystemTestCase0.java 
 * @brief Este modulo dos testes primarios do mudolo ThrowSystem,  
 * criado pelo grupo 6 da turma de programacao sistematica 2-2014 ministrada pela professora genaina
 *
 * @author Igor Chaves Sodre
 * @author Pedro Borges Pio
 * @author Kilmer Luiz Aleluia
 * @since 07/12/2014
 * @version 1.0
 */

package br.unb.unbomber.systems;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class ThrowSystemTestCase0 {

	EntityManager entityManager;
	ThrowSystem throwsystem;
	BombSystem bombsystem;
	MovimentSystem movimentsystem;
	CollisionSystem collisionsystem;

	@Before
	public void setUp() throws Exception {
		// init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		throwsystem = new ThrowSystem();
		bombsystem = new BombSystem();
		movimentsystem = new MovimentSystem();
		collisionsystem = new CollisionSystem();
	}

	/**
	 * @brief :verifica se o throw esta realizando o lancamento da bomba
	 *        devidamente
	 * 
	 */
	@Test
	public void trhowTest() {
		int ENTITY_ID_CHAR = entityManager.getUniqueId();
		/** < adiciona novos ids para as entidades */
		int ENTITY_ID_BOMB = entityManager.getUniqueId();
		int x = 5;
		/** < adiciona valores validos para uma posicao no grid */
		int y = 5;
		Entity anEntityChar = new Entity(ENTITY_ID_CHAR);
		/** < cria uma nova entidades do tipo caracter */
		Entity anEntityBomb = new Entity(ENTITY_ID_BOMB);
		/** < cria uma nova entidade do tipo bomba */

		PowerUp powerup = new PowerUp(PowerType.BOXINGGLOVEACQUIRED);
		/** < instacia um novo powerUp do tipo BOXINGGLOVEACQUIRED */
		/**
		 * < instancia um novo CellPlacement e seta novas posicoes validas para
		 * o caracter
		 */
		CellPlacement charPlacement = new CellPlacement();
		charPlacement.setEntityId(ENTITY_ID_CHAR);
		charPlacement.setCellX(x);
		charPlacement.setCellY(y);
		/**
		 * < instacia um novo CellPlacement e seta posicoes validas para a bomba
		 */
		CellPlacement bombPlacement = new CellPlacement();
		bombPlacement.setEntityId(ENTITY_ID_BOMB);
		bombPlacement.setCellX(x);
		bombPlacement.setCellY(y + 3);

		/** < cria um novo componente do tipo Movable e seta valores validos */
		Movable charMovable = new Movable();
		charMovable.setEntityId(ENTITY_ID_CHAR);
		charMovable.setSpeed(3);
		/** < cria um novo componente do tipo explosion */
		Explosion explosion = new Explosion();
		anEntityBomb.addComponent(explosion);
		anEntityBomb.addComponent(bombPlacement);
		/** < adiciona os componentes do caracter */
		anEntityChar.addComponent(powerup);
		anEntityChar.addComponent(charPlacement);
		anEntityChar.addComponent(charMovable);

		/** < adiciona as entidades criadas anteriormente para o entity manager */
		entityManager.addEntity(anEntityChar);
		entityManager.addEntity(anEntityBomb);

		/** < seta o tipo de movimento realizado como MOVE_UP */
		final MovementCommandEvent movementCommand = new MovementCommandEvent(
				MovementType.MOVE_UP, charMovable.getEntityId());
		/** < forca a a realizacao do comando de acao */
		final ActionCommandEvent commandEvent = new ActionCommandEvent(
				ActionType.DROP_BOMB, ENTITY_ID_CHAR);

		/** < adiciona o evento de acao criado anteriomente ao entity manager */
		entityManager.addEvent(movementCommand);

		movimentsystem.update();
		/** < executa o movimentsystem */
		// collisionsystem.update();
		throwsystem.update();
		/** < eecuta o throwsystem */

		/** < recebe a localizacao da bomba apos a execucao do throwsystem */
		final CellPlacement newBombPlacement = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, ENTITY_ID_BOMB);

		/** < realiza a assertiva */
		assertEquals(y + 3, newBombPlacement.getCellY());// y +3 + 5

	}
}
