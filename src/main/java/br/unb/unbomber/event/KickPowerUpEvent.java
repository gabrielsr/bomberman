package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;


/**
 * Verifies the occorrence of the Kick power up.
 * 
 * @version 20/11/2014
 * @author Grupo 9
 */


public class KickPowerUpEvent extends Event {
	
	int sourceId;
	
	public KickPowerUpEvent(int sourceId) {
		this.sourceId = sourceId;
	}
	
	/* get the id of an entity which has collected the power up */
	public int getSourceId() {
		return sourceId;
	}

	/* set the id of an entity which has collected the power up */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

}