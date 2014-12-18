package br.unb.unbomber.core;

import java.util.ArrayList;
import java.util.List;

/**
 * An Entity in the Entity Component System (ECS).
 *
 * @author Gabriel Rodrigues <gabrielsr@gmail.com>
 */
public final class Entity {

	/** The entity id. */
	private int entityId;

	/** The onwner id. */
	private int ownerId;

	/** The components. */
	private List<Component> components;

	/**
	 * This method is deprecated. You should use EntityManager.createEntity()
	 */
	@Deprecated
	public Entity() {
	}

	/**
	 * Instantiates a new entity.
	 *
	 * @param uniqueId
	 *            the unique id
	 */
	public Entity(int uniqueId) {
		this.entityId = uniqueId;
	}

	/**
	 * Adds the component.
	 *
	 * @param component
	 *            the component
	 */
	public void addComponent(Component component) {
		component.setEntityId(entityId);
		getComponents().add(component);
	}

	/**
	 * Gets the components.
	 *
	 * @return the components
	 */
	public List<Component> getComponents() {
		if (this.components == null) {
			this.components = new ArrayList<Component>();
		}
		return this.components;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public int getEntityId() {
		return this.entityId;
	}

	/**
	 * ID should be setted in the constructor.
	 *
	 * @param entityId
	 *            the new entity id
	 * @see EntityManager#createEntity()
	 */
	@Deprecated
	public void setEntityId(int entityId) {
		this.entityId = entityId;
		if(this.components != null){
			for(Component component : components){
				component.setEntityId(this.entityId);
			}
		}
	}

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
	 * @param ownerId
	 *            the new owner id
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
