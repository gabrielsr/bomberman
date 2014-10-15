package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntitySystemImpl;
import br.unb.unbomber.event.TimeOverEvent;

public class TimeSystemTestCase {
	
	EntityManager entityManager;
	TimeSystem system;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntitySystemImpl.init();
		
		entityManager = EntitySystemImpl.getInstance();
		system = new TimeSystem(entityManager);
	}

	@Test
	public void countDownToThreeTest() {

		TimeOverEvent event = new TimeOverEvent();
		
		//Timer configured for 3 ticks
		Timer timer = new Timer(3, event);
		entityManager.addComponent(timer);

		// 2 ticks
		system.update();
		system.update();
		
		//assert no TimeOverEvent was created yet
		assertNull(entityManager.getEvents(TimeOverEvent.class));
		
		// the 3rd and final tick
		system.update();
		
		//assert the Time Over Event was created
		assertFalse(entityManager.getEvents(TimeOverEvent.class).isEmpty());
	}
}
