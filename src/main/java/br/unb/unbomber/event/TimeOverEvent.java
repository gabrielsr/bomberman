package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;

import com.artemis.Entity;

public class TimeOverEvent<E> implements Event {

	private String action;

	private Entity owner;
	
	private E payload;
	
	public TimeOverEvent(){
		
	}
	
	public TimeOverEvent(String action, E payload){
		this.action = action;
		this.payload = payload;
	}
	
	public TimeOverEvent(String action){
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	public void setAction(String action){
		this.action = action;
	}
	
	public Entity getOwner(){
		return this.owner;
	}

	public void setOwner(Entity owner){
		this.owner = owner;
	}
	
	public E getPayload(){
		return payload;
	}
	
	public void setPayload(E payload){
		this.payload = payload;
	}

}
