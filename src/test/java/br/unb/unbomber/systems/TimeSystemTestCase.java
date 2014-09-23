package br.unb.unbomber.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.core.GameModel;
import br.unb.unbomber.core.GameModelImpl;
import br.unb.unbomber.core.TimeEffect;
import br.unb.unbomber.core.TimeOverEvent;
import br.unb.unbomber.core.TimeSystem;

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
		TimeEffect timer = new TimeEffect(3, eventToBe);
		model.addComponents(timer);

		system.update();
		system.update();
		assertTrue(model.getEvents(TimeOverEvent.class) == null);
		
		
		system.update();
		assertFalse(model.getEvents(TimeOverEvent.class).isEmpty());
	}
}
