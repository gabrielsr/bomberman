/********************************************************************************************************************************
*Grupo 2:
*Maxwell Moura Fernandes     - 10/0116175
*João Paulo Araujo           -
*Alexandre Magno             -
*Marcelo Giordano            -
*********************************************************************************************************************************/
package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;
public class CollisionEvent extends Event {
<<<<<<< HEAD
	private int sourceId;
	private int targetId;
=======
	int sourceId;
	int targetId;
	boolean isTargetId;
	
>>>>>>> 0879c3ecb0358aa243da410e0ccdc3bc3bf7a77b
	
	public CollisionEvent(int sourceId, int targetId){
		setSourceId( sourceId );
		setTargetId( targetId );
	}
	
	//get the id of an entity which collided
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which collided
<<<<<<< HEAD
	private void setSourceId(int id){
		sourceId = id;
=======
	public void setSourceId(int id){
		this.sourceId = id;
>>>>>>> 0879c3ecb0358aa243da410e0ccdc3bc3bf7a77b
	}
	
	public int getTargetId(){
		return targetId;
	}
	
<<<<<<< HEAD
	private void setTargetId(int id){
		targetId = id;
=======
	public void setTargetId(int id){
		this.targetId = id;
	}
	
	public boolean getIsTargetId(){
		return isTargetId;
	}
	
	public void setIsTargetId(boolean isTargetId){
		this.isTargetId = isTargetId;
>>>>>>> 0879c3ecb0358aa243da410e0ccdc3bc3bf7a77b
	}
	
}
