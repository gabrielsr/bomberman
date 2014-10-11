package br.unb.unbomber.event; 
import br.unb.unbomber.component.CellPlacement; 
import br.unb.unbomber.core.Event; 

public class MovementExecutionEvent extends Event { 
	private final CellPlacement initialCell; 
	private final CellPlacement finalCell;
	private final int entityId;

	public MovementExecutionEvent(CellPlacement initialCell, CellPlacement finalCell, int entityId) { 
		super(); this.initialCell = initialCell; this.finalCell = finalCell; this.entityId = entityId; 
	}

	public CellPlacement getInitialCell() { 
		return initialCell; 
	}

	public CellPlacement getFinalCell() { 
		return finalCell; 
	}

	public int getEntityId() { 
		return entityId; 
	} 
}