package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;
import br.unb.unbomber.event.MovementMadeEvent;

public class MovimentSystem extends BaseSystem {

	public void update(){

		/*
		 * List<Event> colision = getEntityManager().getEvents(ColosionEvent.class);


  for(Event bla:colision){
   ColosionEvent var = (ColisionEvent)bla;

   int ids=var.getSourceId();
   int idt=var.getTargetId();

  }

  tivemos um problema nessa parte a ser comentado na documentacao
		 *
		 */

		List<Event> actionEvents = getEntityManager().getEvents(MovementCommandEvent.class);

		CellPlacement Coord;  

		for(Event event:actionEvents){
			MovementCommandEvent actionCommand = (MovementCommandEvent) event;

			int id=actionCommand.getEntityId();


			Movable speedable = (Movable)getEntityManager().getComponent(Movable.class,id);

			int speed = speedable.getSpeed();

			Coord =(CellPlacement)getEntityManager().getComponent(CellPlacement.class,id);
			int x=Coord.getCellX();
			int y=Coord.getCellY();



			if(actionCommand.getType() == MovementType.MOVE_UP){
				y=(Coord.getCellY()+speed); 
			}

			if(actionCommand.getType()==MovementType.MOVE_DOWN){
				y=(Coord.getCellY()-speed);

			}

			if(actionCommand.getType()==MovementType.MOVE_RIGHT){
				x=(Coord.getCellX()+speed);
			}

			if(actionCommand.getType()==MovementType.MOVE_LEFT){
				x=(Coord.getCellX()-speed);
			}

			MovementMadeEvent coisa=new MovementMadeEvent();
			coisa.setNewCellY(y);
			coisa.setNewCellX(x);
			Coord.setCellX(x);
			Coord.setCellY(y);
		}



	}

}