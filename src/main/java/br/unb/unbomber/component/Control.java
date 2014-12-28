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

import java.util.ArrayList;
import java.util.List;

import com.artemis.Component;

public class Control extends Component{
	
	private List<ControlPair> actions;

	public List<ControlPair> getActions() {
		if(actions == null){
			actions = new ArrayList<>();
		}
		return actions;
	}

	public void setActions(List<ControlPair> actions) {
		this.actions = actions;
	}
	
}
