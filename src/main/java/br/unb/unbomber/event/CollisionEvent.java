/********************************************************************************************************************************
*Grupo 2:
*Maxwell Moura Fernandes     - 10/0116175
*João Paulo Araujo           -
*Alexandre Magno             -
*Marcelo Giordano            -
*********************************************************************************************************************************/
package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;
public class CollisionEvent  implements Event  {
	private int sourceId;
	private int targetId;
	private boolean isTargetId;
	
	public CollisionEvent(int sourceId, int targetId){
		setSourceId( sourceId );
		setTargetId( targetId );
	}
	//get the id of an entity which collided
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which collided
	public void setSourceId(int sourceId){
		this.sourceId = sourceId;
	}
	
	public int getTargetId(){
		return targetId;
	}
	
	public void setTargetId(int targetId){
		this.targetId = targetId;
	}
	
	public boolean getIsTargetId(){
		return isTargetId;
	}
	
	public void setIsTargetId(boolean isTargetId){
		this.isTargetId = isTargetId;
	}
	

}
