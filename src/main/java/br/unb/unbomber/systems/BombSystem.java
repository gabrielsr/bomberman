package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.TimeOverEvent;

public class BombSystem extends BaseSystem {

	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";
	

	public BombSystem() {
		super();
	}
	
	public BombSystem(EntityManager model) {
		super(model);
	}
	
	@Override
	public void update() {
		
		//Get ActionCommandEvent events
		List<Event> actionEvents = getEntityManager().getEvents(ActionCommandEvent.class);
		
		for(Event event:actionEvents){
			ActionCommandEvent actionCommand = (ActionCommandEvent) event;
			//verify if is it a DROP_BOMB command
			if(actionCommand.getType()== ActionType.DROP_BOMB){

				BombDropper dropper = (BombDropper) getEntityManager().getComponent(BombDropper.class, 
						actionCommand.getEntityId());
				verifyAndDropBomb(dropper);				
			}
			
		}
		
		//TODO verificar TimeOutEvent de bombas que devem ser disparadas neste turno
		
		//TODO verificar InAnExplosionEvent de bombas que extão no range de outras bombas
		// e devem ser disparadas por efeito cascata
		
	}
	
	/**
	 * Drop a bomb
	 */
	public void verifyAndDropBomb(BombDropper dropper){
		
		//TODO verify if the character has not dropped too much bombs
		
		Entity bomb = createTimeBomb(dropper);
		getEntityManager().addEntity(bomb);
	
		
		//TODO if it is a romete controlled bomb, 
		//make the link so the user can remote explod it
		
	}
	
	/**
	 * Make a Bomb
	 * 
	 * @param dropper
	 */
	private Entity createTimeBomb(BombDropper dropper){
		 /*  The Bomb Entity is made of this components
		  * 	Explosive 
		  * 	Placement 
		  * 	Timer
		  * components */
		
		// find dropper placement
		CellPlacement dropperPlacement = (CellPlacement) getEntityManager().getComponent(CellPlacement.class,
				dropper.getEntityId());
		
		Entity bomb = new Entity();
		
		//The bomb is owned by its dropper
		bomb.setOnwnerId(dropper.getEntityId());

		//Create the placement component
		CellPlacement bombPlacement = new CellPlacement();
		
		//the Bomb should have the same placement of its dropper
		bombPlacement.setCellX(dropperPlacement.getCellX());
		bombPlacement.setCellY(dropperPlacement.getCellY());
		
		//create explosive component
		Explosive bombExplosive = new Explosive();
		//the Bomb should have the same power of its dropper
		bombExplosive.setPower(dropper.getBombRange());

		//create Event for time over
		TimeOverEvent triggeredBombEvent = new TimeOverEvent(); 
		triggeredBombEvent.setAction(TRIGGERED_BOMB_ACTION);
		
		//create timer component
		Timer bombTimer = new Timer(90, triggeredBombEvent);			
		
		bomb.addComponent(bombExplosive);
		bomb.addComponent(bombPlacement);
		bomb.addComponent(bombTimer);
		
		return bomb;
		
	}



}
