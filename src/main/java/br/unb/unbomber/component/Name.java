/**
* @file Name.java
* @brief Este é um componente que representa o nome de um jogador.
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 12/11/2014
* @version 2.0
*/
package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;

public class Name extends Component{

	private String name;
	
	/**
	 * @brief Método responsável pelo retorno do nome de um jogador.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @brief Método responsável por estabelecer o nome de um jogador(entidade).
	 * @param nome Nome do jogador 'x'.
	 * @return void
	 */
	public void setName(String nome){
		this.name = nome;
	}
}
