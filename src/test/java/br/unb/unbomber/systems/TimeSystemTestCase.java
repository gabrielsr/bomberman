package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.GameModel;
import br.unb.unbomber.core.GameModelImpl;
import br.unb.unbomber.event.TimeOverEvent;

public class TimeSystemTestCase {
	
	GameModel model;
	TimeSystem system;
	
	@Before
	public void setUp() throws Exception {
		
		model = GameModelImpl.getInstance();
		system = new TimeSystem(model);
	}

	@Test
	public void countDownToThreeTest() {
		
		TimeOverEvent eventToBe = new TimeOverEvent();
		Timer timer = new Timer(3, eventToBe);
		model.addComponent(timer);

		system.update();
		system.update();
		assertTrue(model.getEvents(TimeOverEvent.class) == null);
		
		
		system.update();
		assertFalse(model.getEvents(TimeOverEvent.class).isEmpty());
	}
}
