package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.EntityType.EntType;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.EntityType;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.DamageEntityEvent;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.GameOverEvent;
import br.unb.unbomber.event.InvencibleEvent;

/**
 * Classe reponsavel pelas regras e logicas do Modulo Life.
 * 
 * @version 0.1 14 Out 2014
 * @author Grupo 5 - Dayanne
 */

public class LifeSystem extends BaseSystem {

	/** Instancia o EntityManager a partir da classe pai BaseSystem */
	public LifeSystem() {
		super();
	}

	/**
	 * Construtor da Classe.
	 * 
	 * @param model
	 */
	public LifeSystem(EntityManager model) {
		super(model);

	}

	@Override
	public void update() {

		/** Coleta Eventos de Colisao */
		List<Event> collisionEvents = getEntityManager().getEvents(
				CollisionEvent.class);

		for (Event event : collisionEvents) {
			CollisionEvent collision = (CollisionEvent) event;

			/**
			 * @if Confere se ha colisao.
			 * 
			 *     Monster/Character - Gera dano no Character caso colidam.
			 * 
			 *     Bomb/Monster - Gera dano no Monster caso colidam.
			 * 
			 *     Bomb/Character - Gera dano no Character caso colidam.
			 * 
			 *     Monster/Explosion - Gera dano no Monster.
			 * 
			 *     Character/Explosion - Gera dano no Character.
			 * 
			 *     Outros casos nao sao gerado danos.
			 */
			if (isLifeDamage(collision)) {
				Health targetCollisionLife = (Health) getEntityManager()
						.getComponent(Health.class, collision.getTargetId());

				takeDamaged(targetCollisionLife);

			}
		}

	}

	public boolean isLifeDamage(CollisionEvent collision) {

		/** Id e tipo da entidade que  realizou a colisao. */
		int sourceId;
		EntityType entType1 = null;
		/** Id e tipo da entidade que sofreu a colisao. */
		int targetId;
		EntityType entType2 = null;
		/**
		 * Booleano que ira indicar a possibilidade de retirar dano de uma
		 * entidade que sofreu alguma colisao.
		 */
		boolean canTakeDamage = false;

		/** Coleta as identidades dos agentes do evento de colisao. */
		sourceId = collision.getSourceId();
		targetId = collision.getTargetId();;

		/** Procura o tipo da entidade pela a Id da mesma. */
		entType1 = (EntityType) getEntityManager().getComponent(
				EntityType.class, sourceId);

		entType2 = (EntityType) getEntityManager().getComponent(
				EntityType.class, targetId);

		/**
		 * @if Confere se foi atribuido algum tipo de entidade aos componentes
		 *     declarados no escopo deste metodo.
		 * 
		 *     Caso ambos forem definidos entao a funcao auxiliar de validacao
		 *     de danos entre colisoes sera chamada retornando a possibilidade
		 *     de colisao entre os tipos passados.
		 * 
		 *     Caso contrario e retornado false.
		 */
		if (entType1 != null && entType2 != null) {
			canTakeDamage = permittedTypeDamage(entType1, entType2);
		}

		return canTakeDamage;
	}

	public void takeDamaged(Health targetCollisionLife) {

		/** Vida da entidade que sofreu a colisao. */
		int lifeEntity;

		/**
		 * @if Confere a possibilidade de retirar vida da entidade.
		 * 
		 *     Caso seja possivel entao sua vida sera decrementada.
		 * 
		 *     Caso contrario, o componente sera passado para uma funcao
		 *     auxiliar para contabilizar as tentativas de vidas que a entidade
		 *     possui para que possa ser decidido se o jogo se encerrara ou se o
		 *     personagem sera recriado no Grid.
		 */
		if (targetCollisionLife.isCanTakeDamaged()) {
			/** Coleta a vida da entidade. */
			lifeEntity = targetCollisionLife.getLifeEntity();
			/** Decrementa a vida da entidade. */
			lifeEntity--;
			/** Atribui a vida decrementada a entidade de origem. */
			targetCollisionLife.setLifeEntity(lifeEntity);

			/**
			 * @if Confere se entidade nao possui mais vida.
			 * 
			 *     Caso nao possua mais vida entao e atribuido que nao sera mais
			 *     permitido retirar vida desta entidade *enquanto este nao for
			 *     recriado*, logo entao e chamado outra funcao auxiliar para
			 *     que seja recriado o personagem caso ele contenha mais
			 *     tentativas de vidas.
			 * 
			 *     Caso contrario.
			 * 
			 */
			if (lifeEntity == 0) {
				targetCollisionLife.setCanTakeDamaged(false);

				// TODO recreateEntity(targetCollisionLife)
			}

		} else {
			// TODO countAvailableTries(targetCollisionLife)
		}

	}

	/**
	 * Metodo que avalia a possibilidade de ser retirado dano de alguma entidade
	 * em um evento de colisao.
	 * 
	 * @param entType1
	 *            Entidade que realizou a colisao.
	 * @param entType2
	 *            Entidade que sofreu a colisao.
	 * @return canTakeDamage
	 */
	public boolean permittedTypeDamage(EntityType entType1, EntityType entType2) {
		/**
		 * Booleano que ira indicar a possibilidade de retirar dano de uma
		 * entidade que sofreu alguma colisao.
		 */
		boolean canTakeDamage;

		if (entType1.getEntType() == EntType.MONSTER
				&& entType2.getEntType() == EntType.CHAR) {
			canTakeDamage = true;
		} else if (entType1.getEntType() == EntType.BOMB
				&& entType2.getEntType() == EntType.MONSTER) {
			canTakeDamage = true;
		} else {
			canTakeDamage = false;
		}

		return canTakeDamage;
	}

}
