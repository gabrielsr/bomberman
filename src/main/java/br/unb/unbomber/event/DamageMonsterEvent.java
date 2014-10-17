//Testa contagem de vidas: decremento dos monsters
package br.unb.unbomber.event;

import br.unb.unbomber.core.Event;

public class DamageMonsterEvent extends Event {
	int sourceId;
	int targetId;
	int health;
	
	public DamageMonsterEvent(int sourceId, int targetId, int health){
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

	//decrement life of monsters
	public void setHealth(int health){
		this.health = health;
	}
}