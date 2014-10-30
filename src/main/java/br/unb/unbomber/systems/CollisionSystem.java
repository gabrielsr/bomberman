/********************************************************************************************************************************
 *Grupo 2:
 *Maxwell Moura Fernandes     - 10/0116175
 *João Paulo Araujo           -
 *Alexandre Magno             -
 *Marcelo Giordano            -
 *********************************************************************************************************************************/
package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovedEntityEvent;

/* Classe que controla os eventos de colis�o do jogo.
 * */
public class CollisionSystem extends BaseSystem {

	/// M�todo construtor da classe.
	public CollisionSystem() {
		super();
	}

	/* M�todo construtor.
	 * @param entityManager � uma entidade do sistema principal.
	 * */
	public CollisionSystem(EntityManager entityManager) {
		super(entityManager);
	}

	// @ override
	/// M�todo principal da classe, executado para identificar as colisoes. 
	public void update() {
		testOfCollision();

	}

	/* Funcao que verifica se houve colisao. Para cada colisao encontrada, um
	 * evento (CollisionEvent) com o
	 * id da entidade fonte e o id da entidade alvo e adicionado ao conjuto de
	 * eventos. 
	 */
	public void testOfCollision() {
		
		List<Component> placeComponents = getEntityManager().getComponents(
				CellPlacement.class); /**< lista que contem o posicionamento das entidades no ultimo tick. */
		
		List<Event> movedEntityEvents = getEntityManager().getEvents(
				MovedEntityEvent.class); /**< lista das entidades que se moveram no ultimo tick. */

		int collMade[][] = new int[movedEntityEvents.size()][2]; /**< Cria um array que contera as colisoes ja feitas para nao 
																		gerar dois eventos para a mesma colisao. O primeiro indice
																		refere-se ao source e o segundo ao target. */
		// Receberá a dupla de id's das entidades que colidiram.
		// int coll[] = new int[2];
		int aux; /**< variavel auxiliar. */

		MovedEntityEvent movedEntityEvent; /**< variavel usada no for a seguir. */

		// itera pela lista de entidades que se moveram. O movimento é indicado
		// pela existência de um evento
		// do tipo MovedEntityEvents.
		for (Event event : movedEntityEvents) {
			CellPlacement cellPlacement1;

			// observe que movedEntityEvent não é uma lista.
			movedEntityEvent = (MovedEntityEvent) event;
			// obtém o CellPlacement de uma entidade que se moveu. Onde o id é
			// obtido a partir de
			// movedEntityEvent.
			// cellPlacement1 não é necessário de verdade já que é possível
			// obter (x,y) de MovedEntityEvent.
			cellPlacement1 = (CellPlacement) getEntityManager().getComponent(
					CellPlacement.class, movedEntityEvent.getEntityId());

			// TODO testar se a colisão se dá entre softblock (bomba, powerup) e
			// personagem.

			// compara as posições de uma das entidades que se moveram com todos
			// os elementos da lista
			// de CellPlacement, i.e, todas as entidades do jogo.
			for (Component component : placeComponents) {
				// uma componente específica da lista de componentes placements.
				// Observe que placeComponents
				// contém todas as entidades (hardblock, softblock, personagem,
				// etc).
				CellPlacement cellPlacement2;
				cellPlacement2 = (CellPlacement) component;

				// Se os ids forem diferentes verifica se houve colisão.
				if (movedEntityEvent.getEntityId() != cellPlacement2
						.getEntityId()) {
					// se o if for verdadeiro significa que (x1,y1) = (x2,y2),
					// i.e, houve colisão entre alguém que se
					// moveu e alguma outra entidade, podendo esta ter se movido
					// ou não.
					// Se ninguém se moveu não pode ter havido nenhuma colisão.
					if ((cellPlacement1.getCellX() == cellPlacement2.getCellX())
							&& (cellPlacement1.getCellY() == cellPlacement2
									.getCellY())) {

						// Testa se a colisão do if acima já ocorreu.
						// Caso a colisão em questão já tenha ocorrido itera
						// mais uma vez.
						boolean test = false;
						test = collisionMade(collMade,
								movedEntityEvent.getEntityId(),
								cellPlacement2.getEntityId());
						if (test == true) {
							continue;
						}

						// Passa o id de uma entidade e
						// recebe um evento relacionado ao movimento de uma
						// entidade.
						// Essa entidade pode ou não ter se movido. Caso não
						// tenha o resultado será NULL.
						// MovedEntityEvent tempMEntityE =
						// getEntityManager().getEvent(MovedEntityEvent.class,
						// cellPlacement2.getEntityId() );
						MovedEntityEvent tempMEntityE = getEvent(
								movedEntityEvents, cellPlacement2.getEntityId());

						// TODO verificar para outras direções.
						/*
						 * Se duas entidades estão se movendo na mesma direção,
						 * por exemplo > >, a colisão(quando ocorre) se dá da
						 * mais rápida na mais lenta. Agora quando a colisão
						 * ocorre e as entidades têm sentidos diferente(> <, >
						 * ^), a colisão é dupla, independentemente da
						 * velocidade. Portanto, para este último caso, dois
						 * eventos são criados, uma para cada choque. Ex.:
						 * eventoX, A colide com B e eventoY, B colide com A.
						 */
						// O problema dos duplos eventos de colisão foi
						// corrigido. Despreze essa parte do comentário acima.
						if (tempMEntityE != null
								&& (movedEntityEvent.getDirection() == tempMEntityE
										.getDirection()))
							if (movedEntityEvent.getSpeed() > tempMEntityE
									.getSpeed()) {
								// o primeiro argumento é quem colide e o
								// segundo em que se colide.
								makeCollisionEvent(
										movedEntityEvent.getEntityId(),
										tempMEntityE.getEntityId());
								// adiciona a colisão ao array de controle de
								// colisões.
								aux = collMade.length;
								collMade[aux][0] = movedEntityEvent
										.getEntityId();
								collMade[aux][1] = tempMEntityE.getEntityId();
							} else {
								// Se as velocidades forem iguais o casamento de
								// coordenadas é impossível.
								// Portando neste laço a entidade indicada por
								// movedEntityEvent tem velocidade menor
								// que a outra entidade.
								makeCollisionEvent(tempMEntityE.getEntityId(),
										movedEntityEvent.getEntityId());
								aux = collMade.length;
								collMade[aux][0] = tempMEntityE.getEntityId();
								collMade[aux][1] = movedEntityEvent
										.getEntityId();
							}
						else {
							// Um de duas coisa aconteceram:
							// 1)Houve uma colisão entre uma entidade que se
							// moveu e outra que ficou parada.
							// 2)As duas entidades se moveram e suas direções
							// são diferentes.
							makeCollisionEvent(movedEntityEvent.getEntityId(),
									cellPlacement2.getEntityId());
							aux = collMade.length;
							collMade[aux][0] = movedEntityEvent.getEntityId();
							collMade[aux][1] = cellPlacement2.getEntityId();
						}

						/*
						 * Ignore esse comentário. A situação a que ele se
						 * refere foi tratada no else acima.
						 * 
						 * Passa para a função que cria o collisionEvent o id d
						 * entidade que é a fonte da colisão e o id da entidade
						 * que é o alvo da colisão. Se essa chamada ocorreu
						 * então se deu entre alguma entidade que se moveu e
						 * outra que ficou parada.
						 * makeCollisionEvent(movedEntityEvent
						 * .getEntityId(),cellPlacement2.getEntityId());
						 */

					}// fim do if onde se testa o casamento de coordenadas.
				}// fim do if onde se testa a diferença de id's.
			}// fim do segundo for. Compara quem se moveu(entidade) com todas as
				// entidades do "grid".

		}// fim do primeiro for. Percorrimento de todos os eventos relacinados
			// ao movimento de entidades.
	}// fim do testOfCollision

	/* prepara o objeto para ser adicionado ao conjunto de eventos de
	 * colisao.
	 * @param sourceId e o objeto que ocasionou a colisao.
	 * @param targetId e o objeto que colidiu passivamente.
	 * */
	private void makeCollisionEvent(int sourceId, int targetId) {
		
		CollisionEvent collisionEvent = new CollisionEvent(sourceId, targetId);

		// adiciona o novo evento de colisão
		getEntityManager().addEvent(collisionEvent);
	}

	/* Funcao provisoria. O ideal era que o EntityManager tivesse um metodo que
	 * retornasse um evento especifico
	 * como ele tem para componente.
	 * @param movedEntityList lista de entidades que se moveram no ultimo tick do relogio.
	 * @param id id da entidade que se quer saber se colidiu.
	 * @return o evento de colisao especifico da entidade com id indicado.
	 */
	private MovedEntityEvent getEvent(List<Event> movedEntityList, int id) {
		MovedEntityEvent mee;
		for (Event evt : movedEntityList) {
			mee = (MovedEntityEvent) evt;
			if (mee.getEntityId() == id)
				return mee;
		}
		return null;
	}

	/* Verifica se ja aconteceu alguma colisao entre id1 e id2. Se sim retorna
	 * true.
	 * @param collmade lista de colisoes que ocorreram no ultimo tick do jogo.
	 * @param id1 id da entidade que ocasionou a colisao.
	 * @param id2 id da entidade que sofreu a colisao.
	 * @return True se id1 e id2 colidiram.
	 */
	private boolean collisionMade(int[][] collMade, int id1, int id2) {

		for (int[] aux : collMade) {
			if ((aux[0] == id1 && aux[1] == id2)
					|| (aux[1] == id1 && aux[0] == id2))
				return true;
		}
		return false;
	}

}// fim da classe CollisionSystem
