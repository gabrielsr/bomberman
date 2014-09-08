package br.unb.unbomber.entity;

import br.unb.unbomber.systems.BombSystem;

public class BombDropper implements WorldObject {
	
	private int permittedSimultaneousBombs;
	private boolean firstBombInfinite;
	private boolean firstBombLandMine;
	private boolean canRemoteTrigger;
	private boolean areBombsPassThrough;
	private boolean areBombsHardPassThrough;
	private int bombRange;
	
	private BombSystem sys;
	
	private Entity entity;
	
	public BombDropper(Entity entity){
		this.entity = entity;
		sys = BombSystem.getInstance();
	}
	
	public void drop(){
		sys.dropBomb(this);
	}

	public int getPermittedSimultaneousBombs() {
		return permittedSimultaneousBombs;
	}

	public void setPermittedSimultaneousBombs(int permittedSimultaneousBombs) {
		this.permittedSimultaneousBombs = permittedSimultaneousBombs;
	}
	public boolean isFirstBombInfinite() {
		return firstBombInfinite;
	}

	public void setFirstBombInfinite(boolean firstBombInfinite) {
		this.firstBombInfinite = firstBombInfinite;
	}

	public boolean isFirstBombLandMine() {
		return firstBombLandMine;
	}

	public void setFirstBombLandMine(boolean firstBombLandMine) {
		this.firstBombLandMine = firstBombLandMine;
	}

	public boolean isCanRemoteTrigger() {
		return canRemoteTrigger;
	}

	public void setCanRemoteTrigger(boolean canRemoteTrigger) {
		this.canRemoteTrigger = canRemoteTrigger;
	}

	public boolean isAreBombsPassThrough() {
		return areBombsPassThrough;
	}

	public void setAreBombsPassThrough(boolean areBombsPassThrough) {
		this.areBombsPassThrough = areBombsPassThrough;
	}

	public boolean isAreBombsHardPassThrough() {
		return areBombsHardPassThrough;
	}

	public void setAreBombsHardPassThrough(boolean areBombsHardPassThrough) {
		this.areBombsHardPassThrough = areBombsHardPassThrough;
	}

	public int getBombRange() {
		return bombRange;
	}

	public void setBombRange(int bombRange) {
		this.bombRange = bombRange;
	}

	@Override
	public double getX() {
		return entity.getX();
	}

	@Override
	public double getY() {
		return entity.getX();
	}
}
