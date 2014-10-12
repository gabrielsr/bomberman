package br.unb.unbomber.core;

/**
 * A Component in the Entity Component System (ECS)
 * 
 * @author grodrigues
 *
 */
public class Component {
	
	private int entityId = 0;

	/**
	 * Get the id of the Entity that this component is part of. 
	 * 
	 * @return id
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * Set the id of the Entity that this component is part of. The Id is setted
	 * when you add the component to a entity. 
	 * 
	 * @see Entity#addComponent(Component)
	 * @return id
	 */
	protected void setEntityId(int entityId) {
		this.entityId = entityId;
	}

}
