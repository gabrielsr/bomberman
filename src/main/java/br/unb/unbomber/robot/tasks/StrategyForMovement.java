package br.unb.unbomber.robot.tasks;

import java.util.Queue;

import br.unb.unbomber.component.Direction;
import br.unb.unbomber.event.CommandEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.robot.IRobotPeer;

public class StrategyForMovement implements Strategy<ManyTurnMovement> {

	private Direction direction;
	
	private float distance;

	private ManyTurnMovement task;

	private Queue<CommandEvent> commands;
	
	private IRobotPeer peer;

	
	private StrategyForMovement(ManyTurnMovement task){
		this.task = task;
	}
	
	private StrategyForMovement(){

	}
	
	public static StrategyForMovement create(ManyTurnMovement task) {
		return new StrategyForMovement(task);
	}

	//// Builder version
	public StrategyForMovement create(IRobotPeer peer) {
		this.peer = peer;
		return new StrategyForMovement();
		
	}
	
	public StrategyForMovement with(Direction direction){
		this.direction = direction;
		return this;
	}
	
	public StrategyForMovement with(float distance){
		this.distance = distance;
		return this;
	}
	
	public void start(){
		task = new ManyTurnMovement(direction,distance);		
	}
	
	//// Strategy
	
	@Override
	public void execute() {
	
		commands.add(new MovementCommandEvent(direction, null));
	
	}


	@Override
	public boolean isDone() {
		
		//make some checks
		
		return true;
	}

	public Queue<CommandEvent> generatedCommands(){
		return this.commands;
	}
}
