package br.unb.unbomber.entity;

public interface WorldObject {

	
	/**
	 * An normal explosion can pass through only soft blocks
	 * @author grodrigues
	 *
	 */
	public enum PassThrough{
		SOFT,
		HARD
	}
	
	public double getX();
	
	public double getY();
}
