package br.unb.unbomber.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Simple implementation of a Entity Managar. 
 * 
 * @author Gabriel Rodrigues <gabrielsr@gmail.com>
 *
 */
public class EntityManagerImpl implements EntityManager {

	/** The events. */
	private Map<Class<?>, List<Event>> events;
	
	/** The components. */
	private Map<Class<?>, List<Component>> components;
	
	/** The instance. */
	private static EntityManager instance;
	
	/**
	 * First id number. 
	 */
	private final int ID_START = 1;
	
	/** The unique id sequence. */
	private int uniqueIdSequence = ID_START;
	
	/**
	 * Inits the EntityManager.
	 */
	public static void init(){
		instance = new EntityManagerImpl(
				new HashMap<Class<?>, List<Event>>(),
				new HashMap<Class<?>, List<Component>>());
	}
	
	/**
	 * Instantiates a new entity system impl.
	 *
	 * @param events the events
	 * @param components the components
	 */
	private EntityManagerImpl(Map<Class<?>, List<Event>> events, Map<Class<?>,List<Component>> components){
		this.events = events;
		this.components = components;
	}
	
	/**
	 * Gets the single instance of EntitySystemImpl.
	 *
	 * @return single instance of EntitySystemImpl
	 */
	public static EntityManager getInstance(){
		if(instance==null){
			init();
		}
		return instance;
	}
	

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#createEntity()
	 */
	@Override
	public Entity createEntity() {
		Entity entity = new Entity(getUniqueId());
		return entity;
	}
	

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#update(br.unb.unbomber.core.Entity)
	 */
	@Override
	public void update(Entity entity) {
		//add the components to the model 
		for(Component component:entity.getComponents()){
			if(component.getEntityId()==0){
				throw new IllegalArgumentException("Entity not properly initialize. A Component without EntityId");
			}
			addComponent(component);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#getEvents(java.lang.Class)
	 */
	@Override
	public List<Event> getEvents(Class<?> type) {
		List<Event> result = events.get(type);
		if(result==null){
			result = new ArrayList<Event>();
			events.put(type, result);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#getComponents(java.lang.Class)
	 */
	@Override
	public List<Component> getComponents(Class<?> componentType) {
		List<Component> result = components.get(componentType);
		if(result==null){
			result = new ArrayList<Component>();
			components.put(componentType, result);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#getComponent(java.lang.Class, int)
	 */
	@Override
	public Component getComponent(Class<?> componentType,
			int entityId) {
		List<Component> componentsOfType = this.components.get(componentType);
		
		//Should it throw an Exception??
		if(componentsOfType==null){
			return null;
		}
		
		for(Component component: componentsOfType){
			//return the first with the required id
			if(component.getEntityId() == entityId){
				return component;
			}
		}
		//if none return null
		return null;
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#addEvent(br.unb.unbomber.core.Event)
	 */
	@Override
	public void addEvent(Event event) {
		List<Event> eventList = this.events.get(event.getClass());
		if(eventList == null){
			eventList = new ArrayList<Event>();
			this.events.put(event.getClass(), eventList);
		}
		eventList.add(event);
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#addComponent(br.unb.unbomber.core.Component)
	 */
	@Override
	public void addComponent(Component component) {
		List<Component> componentList = this.components.get(component.getClass());
		if(componentList == null){
			componentList = new ArrayList<Component>();
			this.components.put(component.getClass(), componentList);
		}
		componentList.add(component);
	}

	/**
	 *  This method is deprecated and should be removed in next versions.
	 *  
	 *  Use createEntity() / update() instead
	 *
	 * @param entity the entity
	 */
	@Override
	@Deprecated
	public void addEntity(Entity entity) {
		// create a entity using the right 
		Entity newEntity = createEntity();
		
		// set the generated key in the old entity
		entity.setEntityId(newEntity.getEntityId());
		
		//add the components to the model 
		for(Component component:entity.getComponents()){
			component.setEntityId(newEntity.getEntityId());
			addComponent(component);
		}
	}

	/**
	 * Return a Unique Id each time it's called.
	 *
	 * @return uniqueId
	 */
	@Override
	public int getUniqueId(){
		return this.uniqueIdSequence++;
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#remove(br.unb.unbomber.core.Component)
	 */
	@Override
	public void remove(Component component) {
		List<Component> componentList = this.components.get(component.getClass());
		if(componentList == null){
			throw new IllegalArgumentException("Type not in entity manager. It was not added so it can't be removed");
		}
		componentList.remove(component);
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#remove(br.unb.unbomber.core.Entity)
	 */
	@Override
	public void remove(Entity entity) {
		for(Component component:entity.getComponents()){
			remove(component);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#removeEntityById(int)
	 */
	@Override
	public void removeEntityById(int entityId) {
		for(Class<?> type:components.keySet()){
			removeComponentByEntityId(type, entityId);
		}
	}
	
	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#removeComponentByEntityId(java.lang.Class, int)
	 */
	@Override
	public void removeComponentByEntityId(Class<?> componentType, int entityId) {
		List<Component> componentsOfType = this.components.get(componentType);
		
		//Should it throw an Exception??
		if(componentsOfType==null){
			return;
		}
		
		List<Component> componentsToRemoove = new ArrayList<Component>(); 
		for(Component component: componentsOfType){
			//remove if it has the expected entityId
			if(component.getEntityId() == entityId){
				componentsToRemoove.add(component);
			}
		}
		componentsOfType.removeAll(componentsToRemoove);
	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.EntityManager#remove(br.unb.unbomber.core.Event)
	 */
	@Override
	public void remove(Event event) {
		List<Event> eventList = this.events.get(event.getClass());
		if(eventList == null){
			throw new IllegalArgumentException("Type not in entity manager. It was not added so it can't be removed");
		}
		eventList.remove(event);		
	}


}
