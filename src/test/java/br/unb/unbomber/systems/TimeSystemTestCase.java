package br.unb.unbomber.systems;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.unb.entitysystem.Entity;
import br.unb.entitysystem.EntityManager;
import br.unb.entitysystem.EntityManagerImpl;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.event.TimeOverEvent;

/**
 * The Class TimeSystemTestCase.
 */
public class TimeSystemTestCase {
	
	/** The entity manager. */
	EntityManager entityManager;
	
	/** The system. */
	TimeSystem system;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntityManagerImpl.init();
		
		entityManager = EntityManagerImpl.getInstance();
		system = new TimeSystem(entityManager);
	}

	/**
	 * Count down to three test.
	 */
	@Test
	public void countDownToThreeTest() {

		TimeOverEvent event = new TimeOverEvent();
		
		//Timer configured for 3 ticks
		Entity entity = entityManager.createEntity();
		Timer timer = new Timer(3, event);
		entity.addComponent(timer);
		entityManager.update(entity);

		// 2 ticks
		system.update();
		system.update();
		
		//assert no TimeOverEvent was created yet
		assertTrue(entityManager.getEvents(TimeOverEvent.class).isEmpty());
		
		// the 3rd and final tick
		system.update();
		
		//assert the Time Over Event was created
		assertFalse("System should create an Event", entityManager.getEvents(TimeOverEvent.class).isEmpty());
		
		assertNull("System should remove the component after the timer is triggered",
				entityManager.getComponent(Timer.class, entity.getEntityId()));

	}
}
