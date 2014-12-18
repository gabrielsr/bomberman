package br.unb.unbomber.core;

import java.util.List;

/**
 * Entity Manager keep the Entities, Components and Events of a running game.
 * 
 * Entity Manager is used for access the entities, componentes and events of
 * the running game
 * 
 *
 * @author Gabriel Rodrigues <gabrielsr@gmail.com>
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
	Entity createEntity();

	/**
	 * Include added components.
	 *
	 * @param entity the entity
	 */
	void update(Entity entity);

	/**
	 * Get all Event's of type 'eventType' Return null if there is no such a
	 * event.
	 *
	 * @param type the type
	 * @return the events
	 */
	List<Event> getEvents(Class<?> type);

	/**
	 * Get all Component's of type 'componentType'. Return null if there is no
	 * such a component.
	 *
	 * @param componentType the component type
	 * @return the components
	 */
	List<Component> getComponents(Class<?> componentType);

	/**
	 * Get a Component of 'componentType' associated with 'component' (both from
	 * the same Entity) if any. Return null if there is no such a component
	 * associated
	 *
	 * @param componentType the component type
	 * @param entityId the entity id
	 * @return the component
	 */
	// public Component getAssociatedComponent(Component component, int
	// entityId);

	/**
	 * Get a Component of 'componentType' associated with 'entityId' if any.
	 * Return null if there is no such a component associated
	 * 
	 * @return the component
	 */
	Component getComponent(Class<?> componentType, int entityId);

	/**
	 * Get entity by ID.
	 * @param entityId
	 * @return Entity
	 */
	Entity getEntity(int entityId);
	
	/**
	 * Add a new event to the model.
	 *
	 * @param event the event
	 */
	void addEvent(Event event);

	/**
	 * Add a new Component to the model and set in it a uniqueId.
	 *
	 * @param newComponent the new component
	 */
	void addComponent(Component newComponent);

	/**
	 * Add a new Entity to the model
	 * 
	 * 
	 * This method is deprecated, use createEntity() and update() instead.
	 *
	 * @param entity the entity
	 * @see #createEntity
	 */
	@Deprecated void addEntity(Entity entity);

	/**
	 * Add a new Component associated with the provided entity id.
	 *
	 * @param component the component
	 */
	// public void addComponents(Component newComponent, String entityId);

	/**
	 * Remove a component from model
	 */
	void remove(Component component);

	/**
	 * Remove a entity from model.
	 *
	 * @param entity the entity
	 */
	void remove(Entity entity);

	/**
	 * Remove a event from model.
	 *
	 * @param event the event
	 */
	void remove(Event event);

	/**
	 * Remove a entity by its Id.
	 *
	 * @param entityId the entity id
	 */
	void removeEntityById(int entityId);

	/**
	 * Remove a component by its Entity Id.
	 *
	 * @param componentType the component type
	 * @param entityId the entity id
	 */
	void removeComponentByEntityId(Class<?> componentType, int entityId);

	/**
	 * Return a Unique Id each time it's called.
	 *
	 * @return uniqueId
	 */
	int getUniqueId();
}
