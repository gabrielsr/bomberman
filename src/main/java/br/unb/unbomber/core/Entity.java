package br.unb.unbomber.core;

import java.util.ArrayList;
import java.util.List;


/**
 * An Entity in the Entity Component System (ECS)
 * 
 * @author grodrigues
 *
 */
public class Entity {

	private int entityId;
	
	private int onwnerId;
	
	private List<Component> components;

	/**
	 * This method is deprecated. You should use EntityManager.createEntity()
	 */
	@Deprecated
	public Entity() {
	}
	
	
	public Entity(int uniqueId) {
		this.entityId = uniqueId;
	}

	public void addComponent(Component component){
		component.setEntityId(entityId);
		getComponents().add(component);
	}
	
	public List<Component> getComponents(){
		if(this.components==null){
			this.components = new ArrayList<Component>();
		}
		return this.components;
	}
	
	public int getEntityId(){
		return this.entityId;
	}

	/**
	 * ID should be setted in the constructor
	 * 
	 * @see EntityManager#createEntity()
	 * @param entityId
	 */
	@Deprecated
	public void setEntityId(int entityId){
		// update components entityId
		this.entityId = entityId;
	}

	
	public int getOnwnerId() {
		return onwnerId;
	}

	public void setOnwnerId(int onwnerId) {
		this.onwnerId = onwnerId;
	}


}
