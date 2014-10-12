package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;


/**
 * {@link http
 * ://www.gamedev.net/page/resources/_/technical/game-programming/case
 * -study-bomberman-mechanics-in-an-entity-component-system-r3159}
 * 
 * @author gabrielsr@gmail.com
 *
 */
public class Explosion extends Component{

	boolean isPassThrough; // Does it pass through soft blocks?
	boolean isHardPassThrough; // Does it pass through hard blocks?
	int range;				 // How much further will it propagate?
	Direction propagationDirection;
	float countdown; 			// How long does it last?
	float propagationCountdown; // How long does it take to propagate to the
								// next square?


}
