package br.unb.unbomber.component;

import com.artemis.Component;


/**
 * {@link http
 * ://www.gamedev.net/page/resources/_/technical/game-programming/case
 * -study-bomberman-mechanics-in-an-entity-component-system-r3159}.
 * 
 * @author gabrielsr@gmail.com
 *
 */
public class Explosion extends Component {

	private boolean isPassThrough; // Does it pass through soft blocks?
	private boolean isHardPassThrough; // Does it pass through hard blocks?
	private int explosionRange;				 // How much further will it propagate?
	private Direction propagationDirection;
	private float countdown; 			// How long does it last?
	private float propagationCountdown; // How long does it take to propagate to the
								// next square?
	private int ownerId; // id of the char who put the bomb that created the explosion

	public boolean isPassThrough() {
		return isPassThrough;
	}
	public void setPassThrough(boolean isPassThrough) {
		this.isPassThrough = isPassThrough;
	}
	public boolean isHardPassThrough() {
		return isHardPassThrough;
	}
	public void setHardPassThrough(boolean isHardPassThrough) {
		this.isHardPassThrough = isHardPassThrough;
	}
	public int getExplosionRange() {
		return explosionRange;
	}
	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}
	public Direction getPropagationDirection() {
		return propagationDirection;
	}
	public void setPropagationDirection(Direction propagationDirection) {
		this.propagationDirection = propagationDirection;
	}
	public float getCountdown() {
		return countdown;
	}
	public void setCountdown(float countdown) {
		this.countdown = countdown;
	}
	public float getPropagationCountdown() {
		return propagationCountdown;
	}
	public void setPropagationCountdown(float propagationCountdown) {
		this.propagationCountdown = propagationCountdown;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
}
