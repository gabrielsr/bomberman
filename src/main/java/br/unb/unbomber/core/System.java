package br.unb.unbomber.core;


/**
 * A System in the Entity Component System (ECS)
 * 
 * @author grodrigues
 *
 */
public interface System {
	
	/* 
	* System Life Cycle
	*/
	public void start();
	
	public void stop();
	
	public void update();
	
}
