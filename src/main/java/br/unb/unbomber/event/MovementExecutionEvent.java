//Classe: MovementExecutionEvent
//Versão: 1.00
//Feito pelo Grupo 7

package br.unb.unbomber.event;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.core.Event;

public class MovementExecutionEvent extends Event {
	
	private final CellPlacement initialCell;
	
	private final CellPlacement finalCell;
	
	private final int entityId;

	/**
	 * Method MovementExecutionEvent constroi o evento com a posição inicial
	 * e final, junto com o id da entidade em movimento.
	 * @param initialCell posição inicial da entidade
	 * @param finalCell posição final da entidade
	 * @param entityId id da entidade
	 */
	public MovementExecutionEvent(CellPlacement initialCell,
			CellPlacement finalCell, int entityId) {
		super();
		this.initialCell = initialCell;
		this.finalCell = finalCell;
		this.entityId = entityId;
	}

	/**
	 * Method getInitialCell resgata a posição inicial da entidade
	 * no evento.
	 * @return initialCell
	 */
	public CellPlacement getInitialCell() {
		return initialCell;
	}

	/**
	 * Method getFinalCell resgata a posição final da entidade
	 * no evento.
	 * @return finalCell
	 */
	public CellPlacement getFinalCell() {
		return finalCell;
	}

	/**
	 * Method getEntityId resgata o ID da entidade que se moveu no evento
	 * @return int (entityId)
	 */
	public int getEntityId() {
		return entityId;
	}
	
}
