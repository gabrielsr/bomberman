package br.unb.unbomber.component;

import br.unb.gridphysics.Vector2D;

import com.artemis.Component;

public class Ballistic extends Component{

	private Vector2D<Integer> orig;
	
	private Vector2D<Integer> displ;
	
	public Ballistic(Vector2D<Integer> orig, 
			Vector2D<Integer> displ) {
		this.orig = orig;
		this.displ = displ;
	}

	public Vector2D<Integer> getOrig() {
		return orig;
	}

	public void setOrig(Vector2D<Integer> orig) {
		this.orig = orig;
	}

	public Vector2D<Integer> getDispl() {
		return displ;
	}

	public void setDispl(Vector2D<Integer> displ) {
		this.displ = displ;
	}
}
