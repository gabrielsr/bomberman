package br.unb.unbomber.core;

/**
 * A Component in the Entity Component System (ECS).
 *
 * @author grodrigues
 */
public class Component {
	
	/** The entity id. */
	private int entityId = 0;

	/**
	 * Get the id of the Entity that this component is part of. 
	 * 
	 * @return id
	 */
	public final int getEntityId() {
		return entityId;
	}

	/**
	 * Set the id of the Entity that this component is part of.
	 * 
	 * The Id is setted when you add the component to a entity. 
	 * 
	 * TODO change visibility to protect
	 * 
	 * @param entityId the new entity id
	 * @see Entity#addComponent(Component)
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

}
