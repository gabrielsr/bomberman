package br.unb.unbomber.systems;

import static org.junit.Assert.assertEquals;
import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.event.TimeOverEvent;
import br.unb.unbomber.misc.EntityBuilder2;

import com.artemis.World;
import com.artemis.systems.VoidEntitySystem;

/**
 * The Class TimeSystemTestCase.
 */
public class TimeSystemTestCase {
	
	
	World world;
	
	TimeSystem timerSystem;
	
	@Before
	public void setUp() throws Exception {
		timerSystem = new TimeSystem();
		
		world = new World();
		world.setSystem(timerSystem);
		
		world.setManager(new EventManager());

		//world.initialize();
	}

	/**
	 * Count down to three test.
	 */
	@Test
	public void countDownToThreeTest() {

		TimeOverEvent event = new TimeOverEvent();
		
		final CallCounter counter = new CallCounter(); 
		
		world.setSystem(new VoidEntitySystem() {
			
			@Override
			protected void processSystem() {}
			
			//counter the number of events received
			@Subscribe
			void handle(TimeOverEvent e){
				counter.call();
			}
			
		});
		
		world.initialize();
		
		EntityBuilder2.create(world)
			.withTimer(3, event)
			.build();

		world.process();
		world.process();
			
		//assert no TimeOverEvent was created yet
		assertEquals("number of times that the observer was called", 0, counter.getNumberOfCalls());
		// the 3rd and final tick
		world.process();
		
		//assert the Time Over Event was created
		assertEquals("number of times that the observer was called", 1, counter.getNumberOfCalls());
		
		world.process();
		world.process();
		world.process();
		world.process();
		assertEquals("number of times that the observer was called", 1, counter.getNumberOfCalls());
		
		//assertNull("System should remove the component after the timer is triggered",
			//	entityManager.getComponent(Timer.class, entity.getEntityId()));

	}
	
	
	public class CallCounter {
		private int calls = 0;
		
		public void call(){
			calls++;
		}
		
		public int getNumberOfCalls(){
			return calls;
		}
	}
}
