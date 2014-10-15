package br.unb.unbomber.core;

/**
 * A Component in the Entity Component System (ECS)
 * 
 * @author grodrigues
 *
 */

public class Component {
	
	/* Id da entidade associada a algum Component. */
	private int entityId;

	/* Metodo para coletar o Id de algum Component associado. */
	public int getEntityId() {
		return entityId;
	}

	/* Metodo para atribuir algum Id a algum Component associado. */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

}
