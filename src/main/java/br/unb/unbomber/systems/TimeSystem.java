package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.GameModel;

public class TimeSystem extends BaseSystem {

	public TimeSystem() {
		super();
	}
	
	public TimeSystem(GameModel model) {
		super(model);
		
	}
	
	@Override
	public void update() {
		List<Component> timedEffects = getModel().getComponents(Timer.class);
		
		for (Component component : timedEffects) {
			Timer timeEffect = (Timer) component;
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
