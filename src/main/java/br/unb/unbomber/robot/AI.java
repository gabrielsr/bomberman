package br.unb.unbomber.robot;

import com.artemis.Component;

public class AI extends Component{

	private RobotPeer peer;
	
	
	public AI(RobotPeer peer){
		this.peer = peer;
	}

	public RobotPeer getPeer() {
		return peer;
	}

	public void setPeer(RobotPeer peer) {
		this.peer = peer;
	}
	
}
