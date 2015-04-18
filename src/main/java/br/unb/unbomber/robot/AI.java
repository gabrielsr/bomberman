package br.unb.unbomber.robot;

import com.artemis.Component;

public class AI extends Component{

	private Robot robot;
	
	private IRobotPeer robotPeer;
	
	public AI(){
	}
	
	public AI(Robot robot){
		this.setRobot(robot);
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public IRobotPeer getRobotPeer() {
		if(robotPeer == null && this.robot !=null){
			robotPeer = new RobotPeer(this.robot);
		}else if(robotPeer == null){
			throw new IllegalStateException("AI not initialized with a robot");	
		}
		return robotPeer;
	}

	public void setRobotPeer(IRobotPeer robotPeer) {
		this.robotPeer = robotPeer;
	}

}
