package br.unb.unbomber.core;

import java.util.List;

/**
 * Entity Manager keep the Entities, Components and Events of a running game
 * 
 * @author grodrigues
 *
 */
public interface EntityManager {


	/**
	 * Create a new Entity and set its unique ID
	 * 
	 * you should call update(entity) after include the components to the
	 * Entity.
	 * 
	 * Example: 
	 * 
	 * 		Entity e = entityManager.createEntity(); 
	 * 		e.addComponent(new CellPlacement(200,400));
	 *  	e.addComponent(new Health());
	 *   	e.addComponent(new BombDropper());
	 * 		entityManager.update(e)
	 * 
	 * @return a new Entity
	 */
	public Entity createEntity();

	/**
	 * Include added components
	 * 
	 * @param entity
	 */
	public void update(Entity entity);

	/**
	 * Get all Event's of type 'eventType' Return null if there is no such a
	 * event
	 */
	public List<Event> getEvents(Class<?> type);

	/**
	 * Get all Component's of type 'componentType'. Return null if there is no
	 * such a component.
	 */
	public List<Component> getComponents(Class<?> componentType);

	/**
	 * Get a Component of 'componentType' associated with 'component' (both from
	 * the same Entity) if any. Return null if there is no such a component
	 * associated
	 */
	// public Component getAssociatedComponent(Component component, int
	// entityId);

	/**
	 * Get a Component of 'componentType' associated with 'entityId' if any.
	 * Return null if there is no such a component associated
	 */
	public Component getComponent(Class<?> componentType, int entityId);

	/**
	 * Add a new event to the model
	 */
	public void addEvent(Event event);

	/**
	 * Add a new Component to the model and set in it a uniqueId
	 */
	public void addComponent(Component newComponent);

	/**
	 * Add a new Entity to the model
	 * 
	 * 
	 * This method is deprecated, use createEntity() and update() instead
	 * @see #createEntity
	 */
	@Deprecated
	public void addEntity(Entity entity);

	/**
	 * Add a new Component associated with the provided entity id
	 */
	// public void addComponents(Component newComponent, String entityId);

	/**
	 * Remove a component from model
	 */
	public void remove(Component component);

	/**
	 * Remove a entity from model
	 */
	public void remove(Entity entity);

	/**
	 * Remove a event from model
	 */
	public void remove(Event event);

	/**
	 * Remove a entity by its Id
	 */
	public void removeEntityById(int entityId);

	/**
	 * Remove a component by its Entity Id
	 */
	public void removeComponentByEntityId(Class<?> componentType, int entityId);

	/**
	 * Return a Unique Id each time it's called
	 * 
	 * @return uniqueId
	 */
	public int getUniqueId();
}
