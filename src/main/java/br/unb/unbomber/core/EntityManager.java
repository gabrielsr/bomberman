package br.unb.unbomber.core;

import java.util.List;

/**
 * Entity Manager keep the Entities, Components and Events of a 
 * running game
 * 
 * @author grodrigues
 *
 */
public interface EntityManager {


	/**
	* Get all Event's of type 'eventType'
	* Return null if there is no such a event
	*/
	public List<Event> getEvents(Class<?> type);
	

	/**
	* Get all Component's of type 'componentType'. 
	* Return null if there is no such a component.
	*/
	public List<Component> getComponents(Class<?> componentType);
	
	/**
	* Get a Component of 'componentType' associated with 'component' (both from the same Entity)
	* if any. Return null if there is no such a component associated
	*/
	//public Component getAssociatedComponent(Component component, int entityId);

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
	*/
	void addEntity(Entity entity);

	/**
	* Add a new Component associated with the provided entity id
	*/
	//public void addComponents(Component newComponent, String entityId);

}
