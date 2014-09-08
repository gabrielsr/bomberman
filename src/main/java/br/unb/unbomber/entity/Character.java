package br.unb.unbomber.entity;


public class Character extends Entity {

	int playerNumber;	// Some way to identify the player - this could also be a player name string
	float maxSpeed;
	Direction facingDirection = Direction.DOWN;
	BombDropper bombDropper;
	
	public Character(){
		 bombDropper = new BombDropper(this);
	}
	
	public final void dropBomb(){
		bombDropper.drop();
	}
	
}
