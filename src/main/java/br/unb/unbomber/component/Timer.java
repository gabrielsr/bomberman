package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Event;


/**
 * The Timer Component.
 */
public class Timer extends Component {

	/** The elapsed time. */
	private long elapsedTime;
	
	/** The active. */
	private boolean active = true;
	
	/** The event. */
	private Event event;

	/**
	 * Instantiates a new timer.
	 *
	 * @param elapsedTime the elapsed time
	 * @param event the event
	 */
	public Timer(long elapsedTime, Event event) {
		this.elapsedTime = elapsedTime;
		this.event = event;
	}

	/**
	 * Tick.
	 */
	public void tick() {
		--elapsedTime;
	}
	

	/**
	 * Checks if is over.
	 *
	 * @return true, if is over
	 */
	public boolean isOver() {
		return elapsedTime <= 0;
	}
	
	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Gets the event.
	 *
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.Component#setEntityId(int)
	 */
	@Override
	public void setEntityId(int entityId) {
		super.setEntityId(entityId);
		this.event.setOwnerId(entityId);
	}
}
