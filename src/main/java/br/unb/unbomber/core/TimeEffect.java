package br.unb.unbomber.core;


public class TimeEffect extends Component {

	private long elapsedTime;
	
	private Event event;

	public TimeEffect(long elapsedTime, Event event) {
		this.elapsedTime = elapsedTime;
		this.event = event;
	}

	public void tick() {
		elapsedTime--;
	}

	public boolean isOver() {
		return (elapsedTime <= 0);
	}
	
	public Event getEvent() {
		return event;
	}

}
