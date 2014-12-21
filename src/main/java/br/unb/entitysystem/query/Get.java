package br.unb.entitysystem.query;

import br.unb.entitysystem.Entity;

public class Get<E> {

	public static From from(Entity entity){
		return new From( entity);
	}


}
