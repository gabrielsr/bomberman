package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class CollisionEvent extends Event {
	int sourceId;
	int targetId;
	
	public CollisionEvent(int sourceId, int targetId){
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	//get the id of an entity which collided
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which collided
	public void setSourceId(int id){
		sourceId = id;
	}
	
	public int getTargetId(){
		return targetId;
	}
	
	public void setTarget(int id){
		targetId = id;
	}
	

}
