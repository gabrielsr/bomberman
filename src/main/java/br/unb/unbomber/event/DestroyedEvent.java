package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class DestroyedEvent extends Event {
	int sourceId;
	
	public DestroyedEvent(int sourceId, int targetId){
		this.sourceId = sourceId;
	}

	//get the id of an entity which was destroyed
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which was destroyed
	public void  setSourceId(int id){
		sourceId = id;
	}
	
}