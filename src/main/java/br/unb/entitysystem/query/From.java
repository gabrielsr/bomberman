package br.unb.entitysystem.query;

import br.unb.entitysystem.Entity;

public class From {
	private Entity entity;

	public From(Entity entity) {
		this.entity = entity;
	}

	public <E> QueryExecutorComponenetOfType<E> component(Class<E> componentType) {
		return new QueryExecutorComponenetOfType<E>(entity, componentType);
	}
}
