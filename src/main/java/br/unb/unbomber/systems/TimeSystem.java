package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.EntityManager;

/**
 * The Class TimeSystem.
 */
public class TimeSystem extends BaseSystem {

	/**
	 * Instantiates a new time system.
	 */
	public TimeSystem() {
		super();
	}

	/**
	 * Instantiates a new time system.
	 *
	 * @param model the model
	 */
	public TimeSystem(EntityManager model) {
		super(model);

	}

	/* (non-Javadoc)
	 * @see br.unb.unbomber.core.BaseSystem#update()
	 */
	@Override
	public final void update() {
		List<Component> timedEffects = getEntityManager().getComponents(
				Timer.class);

		// list of
		List<Timer> toRemoveList = new ArrayList<Timer>();
		if(timedEffects != null){
			for (Component component : timedEffects) {
				Timer timeEffect = (Timer) component;
				timeEffect.tick();
				/*
				 * Add a Event when the the time is up
				 */
				if (timeEffect.isOver()) {
					if(timedEffects != null && timeEffect.getEvent() != null){
						getEntityManager().addEvent(timeEffect.getEvent());
					}
					// add to a list of components to remove, as we can't remove
					// because we can't change a list when we are iterating in it.
					toRemoveList.add(timeEffect);
				}
			}

			for (Component component : toRemoveList) {
				getEntityManager().remove(component);
			}
		}
		

	}

}
