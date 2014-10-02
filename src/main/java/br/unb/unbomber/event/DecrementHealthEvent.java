//Testa contagem de vidas: decremento
package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class DecrementHealthEvent extends Event {
	int sourceId;
	int targetId;
	int health;
	
	public CollisionEvent(int sourceId, int targetId, int setHealth){
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.health = health;
	}
	//get the id of an entity which cause the damage
	public int getSourceId(){
		return sourceId;
	}
	
	//set the id of an entity which cause the damage
	public void  setSourceId(int id){
		sourceId = id;
	}
	
	public int  getTargetId(){
		return targetId;
	}
	
	public void  setTarget(int id){
		targetId = id;
	}

	//decrement life
	public void setHealth(int health){
		return health;
	}

	public 
}