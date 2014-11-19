/**
* @file ScoreSystem.java
* @brief Este é um sistema do jogo responsável pela atualização da pontuação de jogadores após a destruição de entidades.
* @author Camila Imbuzeiro Camargo
* @author Lucas Araújo Pena
* @author Miguel Angelo Montagner Filho
* @author Nicolas Machado Schumacher
* @since 12/11/2014
* @version 2.0
*/

package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.Bounty;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.DestroyedEvent;

public class ScoreSystem extends BaseSystem {

	/**
	 * @brief Construtor score
	 */
	public ScoreSystem() {
		super();
	}
	
	/**
	 * @brief Construtor score
	 * @param model uma instância de EntityManager
	 */
	public ScoreSystem(EntityManager model) {
		super(model);
	}
	
	/**
	 * @brief Método que realiza a função principal do módulo, atualizando a pontuação dos jogadores de acordo com os eventos de um turno.
	 */
	@Override
	public void update() {
		
		EntityManager entityManager = getEntityManager();
		/*Percorrendo a lista de eventos para encontrar um evento de destruição*/
		List<Event> destroyedEvent = entityManager.getEvents(DestroyedEvent.class);
		if (destroyedEvent != null) {
			for (Event event : destroyedEvent) {
				DestroyedEvent destroyed = (DestroyedEvent) event;	
				/*Ao encontrar o evento correto, vemos qual é a pontuação que a entidade destruída dá para quem a destruiu (bounty)*/
				Bounty defuntopoints = (Bounty) getEntityManager().getComponent(Bounty.class, destroyed.getSourceId());
				//TODO ACESSAR ENTIDADE DESTRUIDORA PARA PODER INCREMENTAR O SCORE. Aqui seria necessário que houvesse uma função para
				//que fosse possível acessar o componente Score da entidade destruidora a partir do seu ID. Assim, seria possível usar 
				//o  método addScore do componente dessa entidade, e consequentemente, atribuir à ela a sua pontuação de direito.
			}
		}
	}
}


