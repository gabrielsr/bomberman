//GameOverEvent
//Quando uma entidade não possuir mais health ou availableTries.

package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class GameOverEvent extends Event {
	int setSourceId;
	int getSourceId;
	int health;
	int availableTries;

	public GameOverEvent(int sourceId, int health){
		this.sourceId = sourceId;
		this.health = health;
		this.availableTries = availableTries;
	}
	//get the id of an entity which ended the life
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which ended the life
	public void  setSourceId(int id){
		sourceId = id;
	}
	
	//analizes if has available tries
	public int availableTries(){
		return availableTries;
	}
	
	
}