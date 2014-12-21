/**
* @file Bounty.java
* @brief Este é um componente que representa a recompensa que uma entidade destruída no jogo dá para quem a destruiu.
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 12/11/2014
* @version 2.0
*/

package br.unb.unbomber.component;

import java.util.List;

import br.unb.entitysystem.Component;

public class Control extends Component{

	private List<ControlPair> movement;
	
	private List<ControlPair> action;

	public List<ControlPair> getMovement() {
		return movement;
	}

	public void setMovement(List<ControlPair> movement) {
		this.movement = movement;
	}

	public List<ControlPair> getAction() {
		return action;
	}

	public void setAction(List<ControlPair> action) {
		this.action = action;
	}
	
	
}
