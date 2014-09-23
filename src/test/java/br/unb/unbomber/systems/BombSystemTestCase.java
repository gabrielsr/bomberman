package br.unb.unbomber.systems;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.GameModel;
import br.unb.unbomber.core.GameModelImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;

public class BombSystemTestCase {
	
	GameModel model;
	BombSystem system;
	
	@Before
	public void setUp() throws Exception {
		
		model = GameModelImpl.getInstance();
		system = new BombSystem(model);
	}
	

	@Test
	public void dropBombTest() {
		//Create a Dropper
		BombDropper dropper = new BombDropper();
		model.addComponent(dropper);
		
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, dropper.getEntityId());
		model.addEvent(event);
		
		List<Component> explosives = (List<Component>) model.getComponents(Explosive.class);
		assertNotNull(explosives);
	}
	
	@Test
	public void dropBombAtSamePlaceTest() {
		//character should drop bomb into their own place
//		Character c = new Character();
//		c.setX(3.0d);
//		c.setY(3.0d);
//		
//		c.dropBomb();
//		List<Explosive> bombs = BombSystem.getInstance().getBombs();
//		Explosive bomb = bombs.get(0);
//		assertTrue(bomb.getX() == 3.0d);
	}
	

}
