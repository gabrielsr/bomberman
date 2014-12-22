package br.unb.unbomber.event;

import net.mostlyoriginal.api.event.common.Event;

public class TimeOverEvent implements Event {

	
	private String action;
	
	public TimeOverEvent(){
		
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
}
