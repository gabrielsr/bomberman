package br.unb.entitysystem;

/**
 * An Entity in the Entity Component System (ECS)
 * 
 * An Event is a way of Systems exchange messages
 * without know it other.
 *
 * @author Gabriel Rodrigues <gabrielsr@gmail.com>
 */
public class Event {

	/** The owner id. */
	private int ownerId;
	
	/** The event id. */
	private int eventId;

	/**
	 * Gets the owner id.
	 *
	 * @return the owner id
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * Sets the owner id.
	 *
	 * @param ownerId the new owner id
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public int getEventId() {
		return eventId;
	}
	
	/**
	 * Sets the event id.
	 *
	 * @param eventId the new event id
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
}
