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

import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.component.Bounty;
import br.unb.unbomber.component.Score;
import br.unb.unbomber.event.DestroyedEvent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;

@Wire
public class ScoreSystem extends VoidEntitySystem {

	ComponentMapper<Bounty> cmBounty;
	
	ComponentMapper<Score> cmScore;
	
	UuidEntityManager uuidManager;
	
	/**
	 * @brief Método que realiza a função principal do módulo, atualizando a
	 *        pontuação dos jogadores de acordo com os eventos de um turno.
	 */
	@Subscribe
	public void handle(DestroyedEvent destroyed) {

		Entity target = uuidManager.getEntity(destroyed.getTargetId());
		Entity source = uuidManager.getEntity(destroyed.getSourceId());
		/*
		 * Ao encontrar o evento correto, vemos qual é a pontuação que a
		 * entidade destruída dá para quem a destruiu (bounty)
		 */
		Bounty defuntopoints = cmBounty.get(target);
		Score entScore = cmScore.get(source);
		entScore.addScore(defuntopoints.getBounty());
	}


	@Override
	protected void processSystem() {
		// TODO Auto-generated method stub
		
	}
}




