package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.entity.Bomb;
import br.unb.unbomber.entity.BombDropper;

public class BombSystem implements System {

	static BombSystem instance;
	
	private List<BombDropper> bombDroppers = new ArrayList<BombDropper>();
	
	private List<Bomb> bombs = new ArrayList<Bomb>();
	
	public static BombSystem getInstance() {
		return instance;
	}
	
	public static void createInstance(){
		instance = new BombSystem();
		instance.bombDroppers = new ArrayList<BombDropper>();
		instance.bombs = new ArrayList<Bomb>();
	}
	

	public void dummy(){
		
	}
	
	
	/**
	 * Explode bombs after their wait time
	 */
	public void explodeTimeBombs(){
		//TODO not implemented
		
		Bomb bomb = new Bomb(0, 0, 0, null);
		
		if(bomb.getType()==Bomb.Type.TIME){
			
		}
		
	}
	
	/**
	 * Explode remote controlled bombs
	 */
	public void explodeRemoteBombs(){
		//TODO not implemented
		
	}
	
	/**
	 * Drop a bomb. Called by a character
	 */
	public void dropBomb(BombDropper dropper){
		//TODO verify if the character has not dropped too much bombs
		
		//TODO create a bomb
		
		Bomb bomb = new Bomb(dropper.getX(), dropper.getY(), dropper.getBombRange(), dropper);
		bombs.add(bomb);
		
		//TODO posit it
		
		//TODO set the right power and time
		
		//TODO if it is a romete controlled bomb, 
		//make the link so the user can remote explod it
		
	}
	
	/**
	 * Get the list of bombs
	 */
	public List<Bomb> getBombs(){
		return bombs;
	}


}
