package br.unb.unbomber.event;

import br.unb.entitysystem.Event;

public class TimeOverEvent extends Event{

	
	private String action;
	
	public TimeOverEvent(){
		
	}
	
	public TimeOverEvent(int ownerId, String action){
		setOwnerId(ownerId);
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	public void setAction(String action){
		this.action = action;
	}
}
