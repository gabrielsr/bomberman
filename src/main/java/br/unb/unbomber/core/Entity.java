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

	public void addComponent(Component component){
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
	
	public void setEntityId(int entityId){
		// update components entityId
		for(Component component: this.components){
			component.setEntityId(entityId);
		}
		this.entityId = entityId;
	}

	public int getOnwnerId() {
		return onwnerId;
	}

	public void setOnwnerId(int onwnerId) {
		this.onwnerId = onwnerId;
	}


}
