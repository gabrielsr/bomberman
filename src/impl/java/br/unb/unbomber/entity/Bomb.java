package br.unb.unbomber.entity;

public class Bomb extends Entity implements Ticks {

	private BombDropper dropper;
	
	public int bombRange;
	
	private long countdownTimer=5;
	
	public enum Type{
		REMOTE,
		TIME
	}
	
	private Type type;
	
	public Bomb(double x, double y, int bombRange, BombDropper dropper){
		super.x = x;
		super.y = y;
		this.bombRange = bombRange;
		this.dropper = dropper;
		
		this.passThrought = PassThrough.SOFT;
	}
	
	public void tick(){
		countdownTimer--;
	}
	
	public boolean shouldExplod(){
		return (countdownTimer<=0);
	}
	
	public BombDropper getDropper(){
		return dropper;
	}
	
	public int getBombRange(){
		return bombRange;
	}
	
	public Type getType(){
		return type;
	}
	
}
