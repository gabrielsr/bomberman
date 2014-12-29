/********************************************************************************************************************************
*Grupo 2:
*Maxwell Moura Fernandes     - 10/0116175
*João Paulo Araujo           -
*Alexandre Magno             -
*Marcelo Giordano            -
*********************************************************************************************************************************/
package br.unb.unbomber.event;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;
public class CollisionEvent  implements Event  {
	private UUID sourceId;
	private UUID targetId;
	private boolean isTargetId;
	
	public CollisionEvent(){
		
	}
	
	public CollisionEvent(UUID sourceId, UUID targetId){
		setSourceId( sourceId );
		setTargetId( targetId );
	}
	
	//get the id of an entity which collided
	public UUID getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which collided
	public void setSourceId(UUID sourceId){
		this.sourceId = sourceId;
	}
	
	public UUID getTargetId(){
		return targetId;
	}
	
	public void setTargetId(UUID targetId){
		this.targetId = targetId;
	}
	
	public boolean getIsTargetId(){
		return isTargetId;
	}
	
	public void setIsTargetId(boolean isTargetId){
		this.isTargetId = isTargetId;
	}
	

}
