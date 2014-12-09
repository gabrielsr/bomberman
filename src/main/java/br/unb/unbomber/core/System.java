package br.unb.unbomber.core;


/**
 * A System in the Entity Component System (ECS).
 *
 * @author Gabriel Rodrigues <gabrielsr@gmail.com>
 */
public interface System {
	
	/* 
	* System Life Cycle
	*/
	
	/**
	 * Start.
	 */
	public void start();
	
	/**
	 * Stop.
	 */
	public void stop();
	
	/**
	 * Update.
	 */
	public void update();
	
}
