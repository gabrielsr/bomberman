/**
* @file Score.java
* @brief Este é um componente que representa a pontuação de uma entidade no jogo.
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 12/11/2014
* @version 2.0
*/

package br.unb.unbomber.component;

import com.artemis.Component;

public class Score extends Component{
	
	private int score = 0; 
	
	/**
	 * @brief Este método tem como função retornar a pontuação de uma entidade do jogo.
	 * @return score
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * @brief Este método tem como função incrementar a pontuação de uma entidade que destruiu outra entidade.
	 * @param pontuacao A pontuação que será incrementada será igual a recompensa oferecida pela entidade destruída.
	 * @return void
	 */
	public void addScore(int pontuacao) {
		this.score += pontuacao;
	}

}
