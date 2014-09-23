package br.unb.unbomber.core;

import java.util.List;

public class TimeSystem extends BaseSystem {

	public TimeSystem() {
		super();
	}
	
	public TimeSystem(GameModel model) {
		super(model);
		
	}
	
	@Override
	public void update() {
		List<Component> timedEffects = getModel().getComponents(TimeEffect.class);
		
		for (Component component : timedEffects) {
			TimeEffect timeEffect = (TimeEffect) component;
			timeEffect.tick();
			/*
			 * Add a Event when the the time is up
			 */
			if(timeEffect.isOver()){
				getModel().addEvent(timeEffect.getEvent());
			}
		}

	}



}
