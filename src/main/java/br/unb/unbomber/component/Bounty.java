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

import br.unb.entitysystem.Component;

public class Bounty extends Component{

	private int bounty;
	
	/**
	 * @brief Este método tem como função retornar a recompensa que um monstro/personagem dá.
	 * @return bounty 
	 */
	public int getBounty() {
		return bounty; 
	}
	
	/**
	 * @brief Este método tem como função estabelecer qual vai ser a recompensa que cada entidade irá "oferecer".
	 * @param points O número de pontos que será estabelecido como recompensa.
	 * @return void
	 */
	public void setBounty(int points) {
		this.bounty = points; 
	}
}
