package br.unb.unbomber.robot;

import br.unb.unbomber.component.Direction;
import br.unb.unbomber.robot.events.HitWallEvent;

public class SimpleMoverRobot extends Robot {

	Direction selectedDirection = Direction.UP;
	
	@Override
	public void run(){
		go(selectedDirection);
	}
	
	@Override
	public void onHitWall(HitWallEvent event){
		Direction[] directions = Direction.values();
		selectedDirection = directions[ (selectedDirection.ordinal() +1) % directions.length ]; 
	}
	
}
