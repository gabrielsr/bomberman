package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.GameModel;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.TimeOverEvent;

public class BombSystem extends BaseSystem {

	private final String TRIGGERED_BOMB_ACTION = "BOMB_TRIGGERED";
	

	public BombSystem() {
		super();
	}
	
	public BombSystem(GameModel model) {
		super(model);
		
	}
	
	@Override
	public void update() {
		
		//Get ActionCommandEvent events
		List<Event> actionEvents = getModel().getEvents(ActionCommandEvent.class);
		
		for(Event event:actionEvents){
			ActionCommandEvent actionCommand = (ActionCommandEvent) event;
			//verify if is it a DROP_BOMB command
			if(actionCommand.getType()== ActionType.DROP_BOMB){

				BombDropper dropper = (BombDropper) getModel().getComponent(BombDropper.class, 
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
		getModel().addEntity(bomb);
	
		
		//TODO if it is a romete controlled bomb, 
		//make the link so the user can remote explod it
		
	}
	
	/**
	 * Make a Bomb
	 * 
	 * @param dropper
	 */
	private Entity createTimeBomb(BombDropper dropper){

		// find dropper placement
		CellPlacement dropperPlacement = (CellPlacement) getModel().getComponent(CellPlacement.class,
				dropper.getEntityId());
		
		Entity bomb = new Entity();
		//Dropper Owns the Bomb
		bomb.setOnwnerId(dropper.getEntityId());

		 /*  The Bomb Entity is made of this components
		  * 	Explosive 
		  * 	Placement 
		  * 	Timer
		  * components */

		//create placement component
		CellPlacement bombPlacement = new CellPlacement();	
		bombPlacement.setCellX(dropperPlacement.getCellX());
		bombPlacement.setCellY(dropperPlacement.getCellY());
		
		//create explosive component
		Explosive bombExplosive = new Explosive();
		//set the right power
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
