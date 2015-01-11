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

	private UUID sourceUuid;
	
	private UUID targetUuid;
	
	public CollisionEvent(){
		
	}
	
	public CollisionEvent(UUID sourceId, UUID targetId){
		setSourceUuid( sourceId );
		setTargetUuid( targetId );
	}
	
	//get the id of an entity which collided
	public UUID getSourceUuid(){
		return sourceUuid;
	}
	
	//set the id of an entity which collided
	public void setSourceUuid(UUID sourceUuid){
		this.sourceUuid = sourceUuid;
	}
	
	public UUID getTargetUuid(){
		return targetUuid;
	}
	
	public void setTargetUuid(UUID targetId){
		this.targetUuid = targetId;
	}

}
