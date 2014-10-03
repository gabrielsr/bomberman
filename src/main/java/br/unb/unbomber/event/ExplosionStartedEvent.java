package br.unb.unbomber.event;

/**
 * A bomb explosion event on a Bomberman game.
 * 
 * @author Grupo 1 PS 
 * @version 1.0
 * 
 */

import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.EntitySystemImpl;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;

public class ExplosionStartedEvent extends Event {
	
	private int bombId;
	private int bombRange;
	private CellPlacement bombPosition;
	
	public ExplosionStartedEvent(Explosive bomb) {
		this.setOwnerId(bomb.getOnwnerId());
		bombId = bomb.getEntityId();
		bombRange =  bomb.getPower();	
		bombPosition = (CellPlacement) EntitySystemImpl.getInstance().getComponent(CellPlacement.class, bombId);
	}
	
	public ExplosionStartedEvent(Explosive bomb, CellPlacement bombPosition) {
		this.setOwnerId(bomb.getOnwnerId());
		bombId = bomb.getEntityId();
		bombRange =  bomb.getPower();	
		this.bombPosition = bombPosition;
	}
	
	/** 
	 * @return bomb id
	 */
	public int getBombId() {
		return bombId;
	}
	
	/** 
	 * @return bomb range
	 */	
	public int getBombRange() {
		return bombRange;
	}
	
	/** 
	 * @return bomb's position on grid
	 */
	public CellPlacement getBombPosition() {
		return bombPosition;
	}
	
}
