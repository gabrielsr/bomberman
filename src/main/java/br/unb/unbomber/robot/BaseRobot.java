package br.unb.unbomber.robot;

abstract class BaseRobot implements Runnable {

	IRobotPeer peer;

	/**
	 * {@inheritDoc}
	 */
	public final void setPeer(IRobotPeer peer) {
		this.peer = peer;
	}
	
	public abstract void run();

}
