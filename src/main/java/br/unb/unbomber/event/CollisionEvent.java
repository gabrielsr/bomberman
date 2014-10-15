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
	private int sourceId;
	private int targetId;
	
	public CollisionEvent(int sourceId, int targetId){
		setSourceId( sourceId );
		setTargetId( targetId );
	}
	//get the id of an entity which collided
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which collided
	private void setSourceId(int id){
		sourceId = id;
	}
	
	public int getTargetId(){
		return targetId;
	}
	
	private void setTargetId(int id){
		targetId = id;
	}
	

}
