/**
 * @file TrowSystem.java 
 * @brief Este modulo trata dos Movimentos Relacionados ao Arremeco de bombas de jogo bomberman, criado pelo grupo 6 da turma de programacao sistematica 2-2014 ministrada pela professora janaina
 *
 * @author Igor Chaves Sodre
 * @author Pedro Borges Pio
 * @author Kilmer Luiz Aleluia
 * @since 3/11/2014
 * @version 1.0
 */

package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class ThrowSystem extends BaseSystem {

	private int TRHOW_CONSTAT = 5;

	/**
	 * @brief Construtor Throw
	 */
	public ThrowSystem() {
		super();
	}

	/**
	 * @brief medotodo que verifica se o player possui o blue glove, esta em
	 *        contado com uma bomba e apertou o botao de acao e realiza o
	 *        movimento adequado
	 */
	@Override
	public void update() {
		/** < criando uma instancia do entityManager */
		EntityManager entityManager = getEntityManager();
		/** < cria lista de eventos do tipo actionCommandEvents */
		List<Event> actionCommandEvents = getEntityManager().getEvents(
				ActionCommandEvent.class);
		/** < cria lista de eventos do tipo CollisionEvent */
		List<Event> collisionEvents = getEntityManager().getEvents(
				CollisionEvent.class);
		/** < cria lista de eventos do tipo MovementCommandEvent */
		List<Event> movementCommand = getEntityManager().getEvents(
				MovementCommandEvent.class);

		/**
		 * < laco de repeticao que utiliza os ids dos eventos do tipo action
		 * commadCommandEvent para poder realizar os movimentos das bombas
		 */
		for (Event event : actionCommandEvents) {

			/** < retira um evento da lista */
			ActionCommandEvent actionCommand = (ActionCommandEvent) event;
			/** < recebe o id da entidade atual */
			int id = actionCommand.getEntityId();
			// fizemos isso pois ainda nao foi implementado o power up
			PowerUp powerup = (PowerUp)entityManager.getComponent(PowerUp.class, id);

			/** < recebe o tipo do movimento realizado */
			ActionType type = actionCommand.getType();

			/**
			 * < condicao que verifica se a blue glove esta ativo na entidade
			 * verificada
			 */
			if ( powerup.getTypes().contains(PowerType.BOXINGGLOVEACQUIRED) ) {
				
				/**
				 * < condicao que verifica se o movimento realizado eh de jogar
				 * a bomba
				 */
				if (type == ActionType.DROP_BOMB) {
					/**
					 * < laco de repeticao que filtra os eventos de colisao para
					 * encotrar as colisoes com bobmas
					 */
					for (Event colEvent : collisionEvents) {
						/** < variavel que recebe a colisao */
						CollisionEvent collision = (CollisionEvent) colEvent;
						/** < variavel que recebe o id de quem gerou a colisao */
						int sourceId = collision.getSourceId();
						/**
						 * < condicao que verifica se o id de quem gerou a
						 * colisao eh a mesma entidade tratada pelo evento de
						 * acao
						 */
						if (sourceId == id) {
							/** < recebe o id de quem sofreu a colisao */
							int targetId = collision.getTargetId();
							/**
							 * < variavel que recebe uma componente do tipo
							 * explosion
							 */
							Component explosion = entityManager.getComponent(
									Explosion.class, targetId);
							/**
							 * < verifica se quem sofreu a colisao possui a
							 * component explosion (isso sigifica que o traget
							 * eh uma bomba
							 */
							if (explosion != null) {
								/**
								 * < varivel que recebe uam componente do tipo
								 * CellPlacement
								 */
								CellPlacement Coord = (CellPlacement) getEntityManager()
										.getComponent(CellPlacement.class,
												targetId);

								/**
								 * < variaveis que guardam a coordenada atual da
								 * bomba
								 */
								int x = Coord.getCellX();
								int y = Coord.getCellY();

								/**
								 * < laco de repeticao que verifica a direcao do
								 * movimento feito
								 */
								for (Event placement : movementCommand) {
									MovementCommandEvent move = (MovementCommandEvent) placement;
									/**
									 * < verifica se o movimento realizado
									 * pertence a entidade tratada no laco
									 * superior
									 */
									if (move.getEntityId() == sourceId) {
										/**
										 * < recebe o tipo de movimento
										 * realizado
										 */
										MovementType moveType = move.getType();

										/**
										 * < verifica qual foi a direcao do
										 * movimento e faz o dslocamento da
										 * bomba de acordo com ele
										 */
										if (moveType == MovementType.MOVE_UP) {
											Coord.setCellY(y + TRHOW_CONSTAT);
										}
										if (moveType == MovementType.MOVE_DOWN) {
											Coord.setCellY(y - TRHOW_CONSTAT);
										}
										if (moveType == MovementType.MOVE_RIGHT) {
											Coord.setCellX(x + TRHOW_CONSTAT);
										}
										if (moveType == MovementType.MOVE_LEFT) {
											Coord.setCellX(x - TRHOW_CONSTAT);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}