package br.unb.unbomber.systems;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntitySystemImpl;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.TimeOverEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.InAnExplosionEvent;



public class InAnExplosionEventTestCase {
	
	EntityManager entityManager;
	BombSystem bombSystem;
	BombDropper bombDropper;
	InAnExplosionEvent inAnExplosionEvent;
	
	@Before
	public void setUp() throws Exception {
		
		//init a new system for each test case
		EntitySystemImpl.init();
		
		entityManager = EntitySystemImpl.getInstance();
		
		this.inAnExplosionEvent = new InAnExplosionEvent();
		this.bombSystem = new BombSystem(entityManager);

	}
	
	@Test
	public void testIfBombExplode(){
		//Create bombDropper
		BombDropper bombDropper = new BombDropper();
		entityManager.addComponent(bombDropper);
		
		//put this bomb on grid
		pubBombOnGrid(0,0, bombDropper);
		
		//send a event telling that this bomb have to explode 
		this.inAnExplosionEvent.setIdHit(bombDropper.getEntityId());
		
		//update bombSystem to check the events. After that, the BombSystem have  
		//to create an event of the explosion of the early created bomb
		this.bombSystem.update();
		
		//TODO assertEquals(this.bombSystem.sendedExplosionEvent, true);
	}
	
	private void pubBombOnGrid(int x, int y, BombDropper bombDropper){
		// create a entity
		// SO it get an entityId (needed as the new bomb dropped will need it as its ownerId)
		Entity anEntity = entityManager.createEntity();
		
		//Create the placement component
		CellPlacement dropperPlacement = new CellPlacement();
		
		//set the dropper position
		dropperPlacement.setCellX(x);
		dropperPlacement.setCellY(y);
		
		//Create the Timer Component
		TimeOverEvent triggeredBombEvent = new TimeOverEvent(); 
		triggeredBombEvent.setAction("BOMB_TRIGGERED");
		Timer timer = new Timer(90, triggeredBombEvent );
		
		//Create the Explosive Component
		Explosive explosive = new Explosive();
		explosive.setExplosionRange(bombDropper.getExplosionRange());
		
		// add the components
		anEntity.addComponent(dropperPlacement);
		anEntity.addComponent(timer);
		anEntity.addComponent(explosive);
		
		// add the dropper to the model
		entityManager.update(anEntity);
		
		//create an DROP_BOMB Command Event
		ActionCommandEvent event = new ActionCommandEvent(ActionType.DROP_BOMB, bombDropper.getEntityId());
		entityManager.addEvent(event);
		

		entityManager.update(anEntity);
		//run the system
		bombSystem.update();
	}
	
	
}
