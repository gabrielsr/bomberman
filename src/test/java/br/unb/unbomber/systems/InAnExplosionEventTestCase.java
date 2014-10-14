package br.unb.unbomber.systems;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;



public class InAnExplosionEventTestCase {
	
	EntityManager entityManager;
	BombSystem bombSystem;
	InAnExplosionEvent inAnExplosionEvent;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntityManagerImpl.init();
		
		entityManager = EntityManagerImpl.getInstance();
		
		this.inAnExplosionEvent = new InAnExplosionEvent();
		this.bombSystem = new BombSystem(entityManager);

	}
	
	@Test
	public void testIfBombExplode(){
		//Create bombDropper
		Entity anEntity = createDropperEntity();
		
		BombDropper bombDropper = (BombDropper) entityManager.getComponent(BombDropper.class, anEntity.getEntityId());
		//put one bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		entityManager.update(anEntity);
		
		List<Component> explosives = entityManager.getComponents(Explosive.class);
		
		//send a event telling that this bomb have to explode 
		this.inAnExplosionEvent.setIdHit(explosives.get(0).getEntityId());
		entityManager.addEvent(inAnExplosionEvent);
		
		//update bombSystem to check the events. After that, the BombSystem have  
		//to create an event of the explosion of the early created bomb
		this.bombSystem.update();
		
		assertEquals( entityManager.getEvents(ExplosionStartedEvent.class).size() , 1);
	}
	
	private void pubBombOnGrid(int x, int y, BombDropper bombDropper){
		
		CellPlacement placement = (CellPlacement) entityManager.getComponent(CellPlacement.class, bombDropper.getEntityId());
		
		placement.setCellX(x);
		placement.setCellY(y);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		
		//run the system
		bombSystem.update();
	}
	
	private Entity createDropperEntity(){
		
		Entity anEntity = entityManager.createEntity();
		
		//Create Dropper
		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(5);
		
		//Create Placement
		CellPlacement placement = new CellPlacement();
		placement.setCellX(0);
		placement.setCellY(0);
		
		//Add components
		anEntity.addComponent(bombDropper);
		anEntity.addComponent(placement);
		
		entityManager.update(anEntity);
		
		return anEntity;
	}
	
	
}
