package br.unb.unbomber.systems.query;

import br.unb.unbomber.core.Entity;

public class From {
	private Entity entity;

	public From(Entity entity) {
		this.entity = entity;
	}

	public <E> QueryExecutorComponenetOfType<E> component(Class<E> componentType) {
		return new QueryExecutorComponenetOfType<E>(entity, componentType);
	}
}
