/**
 * @author <img src="https://avatars2.githubusercontent.com/u/8586137?v=2&s=30" width="30" height="30"/> <a href="https://github.com/JeffVFA" target="_blank">JeffVFA</a>
 * @author <img src="https://avatars1.githubusercontent.com/u/4968411?v=2&s=30" width="30" height="30"/> <a href="https://github.com/zidenis" target="_blank"> zidenis </a>
 * @author <img src="https://avatars2.githubusercontent.com/u/3778188?v=2&s=30" width="30" height="30"/> <a href="https://github.com/DRA2840" target="_blank"> DRA2840 </a>
 * @author <img src="https://avatars2.githubusercontent.com/u/7716247?v=2&s=30" width="30" height="30"/> <a href="https://github.com/brenoxp2008" target="_blank"> brenoxp2008</a>
 */

package br.unb.unbomber.component;

import com.artemis.Component;


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
