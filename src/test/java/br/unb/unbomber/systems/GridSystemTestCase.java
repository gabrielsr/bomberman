package br.unb.unbomber.systems;

import junit.framework.Assert;
import net.mostlyoriginal.api.event.common.EventManager;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;

import com.artemis.World;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.EntityBuilder;

public class GridSystemTestCase {
	
	GridSystem gridSystem;
	
	World world;
	
	@Before
	public void setUp() throws Exception {
		gridSystem = new GridSystem();
		
		world = new World();
		world.setSystem(gridSystem);
		
		world.setManager(new EventManager());
		world.setManager(new UuidEntityManager());
		
		world.initialize();
		
		/** create block in 3,4  */
		new EntityBuilder(world)
		 .with(new Position(3,4), 
				 new MovementBarrier(MovementBarrierType.BLOCKER))
		 .build();
		
		world.process();
	}
	
	
	@Test
	public void getAEntityAtAGridPostionTest() {		
		/** the list of entities in 3,4 ins´t empty */
		Assert.assertFalse(gridSystem.getInPosition(3,4).isEmpty());
	}

	@Test
	public void notEntityAtAGridPostionTest() {		
		
		/** the list of entities in 5,5 is empty */
		Assert.assertTrue(gridSystem.getInPosition(5,5).isEmpty());
	}
}
