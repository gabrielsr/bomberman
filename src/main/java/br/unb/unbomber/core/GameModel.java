package br.unb.unbomber.core;

import java.util.List;

public interface GameModel {


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
	//public Component getAssociatedComponent(Component component, String entityId);
	
	/**
	* Add a new event to the Game Model 
	*/
	public void addEvent(Event event);
	
	/**
	* Add a new Component
	*/
	public void addComponents(Component newComponent);
	
	
	/**
	* Add a new Component associated with the provided entity id
	*/
	//public void addComponents(Component newComponent, String entityId);

}
