package br.unb.unbomber.entity;


/**
 * {@link http
 * ://www.gamedev.net/page/resources/_/technical/game-programming/case
 * -study-bomberman-mechanics-in-an-entity-component-system-r3159}
 * 
 * @author gabrielsr@gmail.com
 *
 */
public class Explosion extends Entity implements Ticks{

	boolean isPassThrough; // Does it pass through soft blocks?
	boolean isHardPassThrough; // Does it pass through hard blocks?
	int range;				 // How much further will it propagate?
	Direction propagationDirection;
	float countdown; 			// How long does it last?
	float propagationCountdown; // How long does it take to propagate to the
								// next square?
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
