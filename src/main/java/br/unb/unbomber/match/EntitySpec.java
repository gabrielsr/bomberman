package br.unb.unbomber.match;

import java.util.List;

import com.artemis.Component;

/**
 * Spec of an entity. A list of components.
 * Its easy the process of creating a spec of an entity without
 * include it in a specific world.
 * 
 * 
 * @author grodrigues
 *
 */
public class EntitySpec {

	public List<Component> components;

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}
}