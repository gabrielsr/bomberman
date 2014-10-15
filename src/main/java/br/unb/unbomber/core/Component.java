package br.unb.unbomber.core;

/**
 * A Component in the Entity Component System (ECS)
 * 
 * @author grodrigues
 *
 */
public class Component {
	
	private int entityId;

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

}
