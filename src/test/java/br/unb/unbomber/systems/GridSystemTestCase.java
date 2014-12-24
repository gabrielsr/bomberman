package br.unb.unbomber.systems;

import junit.framework.Assert;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import com.artemis.World;

public class GridSystemTestCase {
	
	GridSystem gridSystem;
	
	World world;
	
	@Before
	public void setUp() throws Exception {
		gridSystem = new GridSystem();
		
		world = new World();
		world.setSystem(gridSystem);
		
		world.setManager(new EventManager());

		world.process();
	}
	
	
	@Test
	public void getAEntityAtAGridPostionTest() {		
		/** get it in correct position */
		Assert.assertNotNull(gridSystem.getInPosition(3,4));
	}

	@Test
	public void notEntityAtAGridPostionTest() {		
		/** get it in correct position */
		Assert.assertNull(gridSystem.getInPosition(4,3));
	}
}
