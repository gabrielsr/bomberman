package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Event;


public class Timer extends Component {

	private long elapsedTime;
	
	private boolean active = true;
	
	private Event event;

	public Timer(long elapsedTime, Event event) {
		this.elapsedTime = elapsedTime;
		this.event = event;
	}

	public void tick() {
		elapsedTime--;
	}
	

	public boolean isOver() {
		return (elapsedTime <= 0);
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	public Event getEvent() {
		return event;
	}

	@Override
	public void setEntityId(int entityId){
		super.setEntityId(entityId);
		this.event.setOwnerId(entityId);
	}
}
