package br.unb.unbomber.systems;

import net.mostlyoriginal.api.event.common.EventManager;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.event.BallisticMovementCompleted;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;

/**
 * The Class TimeSystem.
 */
@Wire
public class TimeSystem extends EntitySystem {

	/** used to dispatch events */
	private EventManager em;

	private ComponentMapper<Timer> mTimer;

	public TimeSystem() {
		super(Aspect.getAspectForAll(Timer.class));
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (Entity entity : entities) {
			process(entity);
		}
	}

	public void process(Entity entity) {
		Timer timer = mTimer.get(entity);
		timer.tick();
		
		/** Dispatch event and remove timer when the the time is up */
		if (timer.isOver()) {
			if (timer.getEvent() != null) {
				if(timer.getEvent().getClass() == (new BallisticMovementCompleted()).getClass())
					em.dispatch(timer.getEvent());
				em.dispatch(timer.getEvent());
			}
			entity.edit().remove(timer);
		}
	}

}
