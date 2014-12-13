package br.unb.unbomber.systems.query;

import br.unb.unbomber.core.Entity;

public class Get<E> {

	public static From from(Entity entity){
		return new From( entity);
	}


}
