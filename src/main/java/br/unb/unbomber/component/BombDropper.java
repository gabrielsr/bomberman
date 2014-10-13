package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

public class BombDropper extends Component {
	

	private int ownerId;
	
	private int permittedSimultaneousBombs;
	private boolean firstBombInfinite;
	private boolean firstBombLandMine;
	private boolean canRemoteTrigger;
	private boolean areBombsPassThrough;
	private boolean areBombsHardPassThrough;
	private int explosionRange;
	
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
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

	public int getExplosionRange() {
		return explosionRange;
	}

	public void setExplosionRange(int explosionRange) {
		this.explosionRange = explosionRange;
	}

}
