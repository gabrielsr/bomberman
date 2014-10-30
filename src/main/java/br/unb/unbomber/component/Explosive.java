/**
 * @author JeffVFA <img src="https://avatars2.githubusercontent.com/u/8586137?v=2&s=460"/> 
 * @author zidenis <img src="https://avatars1.githubusercontent.com/u/4968411?v=2&s=460"/> 
 * @author DRA2840 <img src="https://avatars2.githubusercontent.com/u/3778188?v=2&s=460"/> 
 * @author brenoxp2008 <img src="https://avatars2.githubusercontent.com/u/7716247?v=2&s=460"/>
 */

package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;


public class Explosive extends Component {

	private int ownerId;
	
	private int explosionRange;
	/** 
	 * Getter for the OwnerID 
	 * @return ownerID one integer, represents who owns the bomb
	 */
	public int getOwnerId() {
		return ownerId;
	}
	
	/** 
	 * Setter for the OwnerID 
	 * @param ownerID one integer who will be the value of the ownerID
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	/** 
	 * Getter for the ExplosionRange 
	 * @return explosionRange one integer, represents whats the range of an explosion
	 */
	public int getExplosionRange() {
		return explosionRange;
	}
	
	/** 
	 * Getter for the ExplosionRange 
	 * @param explosionRange ne integer who will be the value of the explosionRange
	 */
	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}
	
	
}
