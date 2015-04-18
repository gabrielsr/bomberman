package br.unb.unbomber.robot.tasks;

interface Strategy<T> {

	boolean isDone();
	
	public void execute();
	
}
