package br.unb.unbomber.entity;

/**
 * Every element in the stage that interface with the game, 
 * has a position and need to be part of the simulation is an entity
 * 
 * @author gabrielsr@gmail.com
 * 
 */
public class Entity implements WorldObject{

	public double x;
	public double y;

	//if soft fire can pass through it
	public PassThrough passThrought;
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public PassThrough getPassThrought() {
		return passThrought;
	}
	public void setPassThrought(PassThrough passThrought) {
		this.passThrought = passThrought;
	}
	
}
