package br.unb.unbomber.systems;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.entity.Bomb;
import br.unb.unbomber.entity.Character;

public class BombSystemTestCase {

	@Before
	public void setUp() throws Exception {
		BombSystem.createInstance();
	}

	@Test
	public void dropBombTest() {
		//character should drop bomb
		Character c = new Character();
		c.dropBomb();
		
		List<Bomb> bombs = BombSystem.getInstance().getBombs();
		assertFalse(bombs.isEmpty());	
	}
	
	@Test
	public void dropBombAtSamePlaceTest() {
		//character should drop bomb into their own place
		Character c = new Character();
		c.setX(3.0d);
		c.setY(3.0d);
		
		c.dropBomb();
		List<Bomb> bombs = BombSystem.getInstance().getBombs();
		Bomb bomb = bombs.get(0);
		assertTrue(bomb.getX() == 3.0d);
	}
	

}
