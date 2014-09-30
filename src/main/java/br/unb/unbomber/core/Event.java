package br.unb.unbomber.core;

/**
 * An Entity in the Entity Component System (ECS)
 * 
 * An Event is a way of Systems exchange messages
 * without know it other
 * 
 * @author grodrigues
 *
 */
public class Event {


	private int ownerId;
	

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
}
