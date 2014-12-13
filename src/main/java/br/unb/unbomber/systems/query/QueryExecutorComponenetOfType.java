package br.unb.unbomber.systems.query;

import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;

public class QueryExecutorComponenetOfType<E> {

	Entity from;

	Class<E> typeToQuery;

	private List<E> result;

	QueryExecutorComponenetOfType(Entity entity, Class<E> typeToQuery) {
		this.from = entity;
		this.typeToQuery = typeToQuery;
	}

	public List<E> alist() {
		query();
		return result;
	}

	public E now() {
		query();
		if (result != null) {
			return result.get(0);
		}
		return null;
	}

	private void acumulate(E itComp) {
		if (result == null) {
			result = new ArrayList<E>();
		}
		result.add(itComp);
	}

	private void query() {
		for (Component itComp : from.getComponents()) {
			if (typeToQuery.isAssignableFrom(itComp.getClass())) {
				acumulate(typeToQuery.cast(itComp));
			}
		}
	}

}