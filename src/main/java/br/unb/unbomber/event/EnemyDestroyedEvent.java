package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class EnemyDestroyedEvent extends Event {
	int sourceId;
	
	public EnemyDestroyedEvent(int sourceId, int targetId){
		this.sourceId = sourceId;
	}

	//get the id of an entity monster which was destroyed
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity monster which was destroyed
	public void  setSourceId(int id){
		sourceId = id;
	}
	
}