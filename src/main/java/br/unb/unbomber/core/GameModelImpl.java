package br.unb.unbomber.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameModelImpl implements GameModel {

	private Map<Class<?>, List<Event>> events;
	
	private Map<Class<?>, List<Component>> components;
	
	private static GameModel instance;
	
	public static void init(){
		instance = new GameModelImpl(
				new HashMap<Class<?>, List<Event>>(),
				new HashMap<Class<?>, List<Component>>());
		
	}
	
	private GameModelImpl(Map<Class<?>, List<Event>> events, Map<Class<?>,List<Component>> components){
		this.events = events;
		this.components = components;
	}
	
	public static GameModel getInstance(){
		if(instance==null){
			init();
		}
		return instance;
	}
	
	@Override
	public List<Event> getEvents(Class<?> type) {
		List<Event> result = events.get(type);
		return result;
	}

	@Override
	public List<Component> getComponents(Class<?> componentType) {
		return this.components.get(componentType);
	}

//	@Override
//	public Component getAssociatedComponent(Component component,
//			Class<Component> componentType) {
//		List<Component> componentsOfType = this.components.get(componentType);
//		
//		for(Component component: componentsOfType){
//			//TODO test if is associated and return it
//		}
//	}

	@Override
	public void addEvent(Event event) {
		List<Event> eventList = this.events.get(event.getClass());
		if(eventList == null){
			eventList = new ArrayList<Event>();
			this.events.put(event.getClass(), eventList);
		}
		eventList.add(event);
	}

	@Override
	public void addComponents(Component component) {
		List<Component> componentList = this.components.get(component.getClass());
		if(componentList == null){
			componentList = new ArrayList<Component>();
			this.components.put(component.getClass(), componentList);
		}
		this.components.put(component.getClass(), componentList);
	}

}
