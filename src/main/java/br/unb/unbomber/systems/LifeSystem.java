package br.unb.unbomber.systems;

import java.util.List;

import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.InvencibleEvent;

/**
 * Classe reponsavel pelas regras e logicas do Modulo Life.
 * 
 * @version 0.2 21 Out 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
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

		if (collisionEvents != null) {
			for (Event event : collisionEvents) {
				CollisionEvent collision = (CollisionEvent) event;

				/**
				 * @if Confere a possibilidade de retirar dano entre as
				 *     entidades do evento de colisao.
				 * 
				 *     Caso seja possivel entao a vida do alvo da colisao sera
				 *     decrementada.
				 * 
				 *     Caso contrario o metodo seguira em frente tratando de
				 *     outros eventos.
				 */
				if (isLifeDamage(collision)) {
					Health targetCollisionLife = (Health) getEntityManager()
							.getComponent(Health.class, collision.getTargetId());

					takeDamaged(targetCollisionLife);

				}
			}
		}

		/** Coleta Eventos de destruicao de entidades. */
		List<Event> destroyedEvents = getEntityManager().getEvents(
				DestroyedEvent.class);

		if (destroyedEvents != null) {
			for (Event event : destroyedEvents) {
				DestroyedEvent destroyed = (DestroyedEvent) event;

				/**
				 * @if Confere a possibilidade de recriar a entidade.
				 * 
				 *     Caso a entidade ainda possuir tentativas de vida entao
				 *     ela e recriada no Grid na celula inicial (0,0) e com modo
				 *     Invencible ativo por 270 ticks.
				 * 
				 *     Caso contrario e gerado o evento de Game Over.
				 */
				if (countAvailableTries(destroyed)) {
					recreateEntity(destroyed);
				} else {
					createGameOverEvent(destroyed);
				}
			}
		}

	}

	public boolean isLifeDamage(CollisionEvent collision) {

		/** Id e tipo da entidade que realizou a colisao. */
		int sourceId;
		LifeType entType1 = null;
		/** Id e tipo da entidade que sofreu a colisao. */
		int targetId;
		LifeType entType2 = null;
		/**
		 * Booleano que ira indicar a possibilidade de retirar dano de uma
		 * entidade que sofreu alguma colisao.
		 */
		boolean canTakeDamage = false;

		/** Coleta as identidades dos agentes do evento de colisao. */
		sourceId = collision.getSourceId();
		targetId = collision.getTargetId();

		/** Procura o tipo da entidade pela a Id da mesma. */
		entType1 = (LifeType) getEntityManager().getComponent(LifeType.class,
				sourceId);

		entType2 = (LifeType) getEntityManager().getComponent(LifeType.class,
				targetId);

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
		 *     Caso contrario o metodo sai sem realizar nenhuma acao.
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
			 *     recriado* e o componente sera passado para um funcao auxiliar
			 *     de criacao de evento de destruicao da entidade.
			 * 
			 *     Caso contrario e atribuido que sera permitido retirar vida
			 *     daquela entidade.
			 * 
			 */
			if (lifeEntity <= 0) {
				targetCollisionLife.setCanTakeDamaged(false);

				createDestroyEvent(targetCollisionLife);

			} else {
				targetCollisionLife.setCanTakeDamaged(true);
			}

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
	public boolean permittedTypeDamage(LifeType entType1, LifeType entType2) {
		/**
		 * Booleano que ira indicar a possibilidade de retirar dano de uma
		 * entidade que sofreu alguma colisao.
		 */
		boolean canTakeDamage;

		if (entType1.getType() == Type.MONSTER
				&& entType2.getType() == Type.CHAR) {
			canTakeDamage = true;
		} else if (entType1.getType() == Type.BOMB
				&& entType2.getType() == Type.MONSTER) {
			canTakeDamage = true;
		} else {
			canTakeDamage = false;
		}

		return canTakeDamage;
	}

	/**
	 * Metodo que cria evento de destruicao de uma entidade.
	 * 
	 * @param targetCollisionLife
	 *            Componente que possui a Id que zerou a vida.
	 */
	public void createDestroyEvent(Health targetCollisionLife) {

		/** Cria evento de destruicao da entidade. */
		DestroyedEvent destroyEntity = new DestroyedEvent(
				targetCollisionLife.getEntityId());

		/** Adiciona este evento ao gerenciamento de entidades. */
		getEntityManager().addEvent(destroyEntity);

	}

	/**
	 * Metodo que avalia se e possivel retirar uma tentativa de vida da entidade
	 * que sera destruida.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que sera destruida.
	 * @return haveLifeTries
	 */
	public boolean countAvailableTries(DestroyedEvent destroyed) {
		/**
		 * Booleano que ira indicar a possibilidade de retirar uma tentativa de
		 * vida da entidade que foi destruida.
		 */
		boolean haveLifeTries;
		/** Quantidade de tentativas de vida que a entidade possui. */
		int lifeTries;

		/**
		 * Coleta componente de quantidade de tentativas de vidas pelo Id da
		 * entidade que vai ser destruida.
		 */
		AvailableTries availableTries = (AvailableTries) getEntityManager()
				.getComponent(AvailableTries.class, destroyed.getSourceId());

		lifeTries = availableTries.getLifeTries();

		if (lifeTries > 0) {
			haveLifeTries = true;
		} else {
			haveLifeTries = false;
		}

		return haveLifeTries;
	}

	/**
	 * Metodo que recria a entidade na celula inicial do Grid e atribui o modo
	 * Invencible a entidade por 270 ticks, o qual nao podera tomar dano.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que sera recriada.
	 */
	public void recreateEntity(DestroyedEvent destroyed) {
		/** Quantidade de tentativas de vida que a entidade possui. */
		int avTries;

		/** Atribuicao da entidade destruida a celula inicial. */
		CellPlacement placement = (CellPlacement) getEntityManager()
				.getComponent(CellPlacement.class, destroyed.getSourceId());
		placement.setCellX(0);
		placement.setCellY(0);

		/** Atribuicao da vida incial da entidade destruida. */
		Health life = (Health) getEntityManager().getComponent(Health.class,
				destroyed.getSourceId());
		life.setLifeEntity(1);
		life.setCanTakeDamaged(true);

		/** Decrementa de uma tentativa de vida da entidade destruida. */
		AvailableTries availableTries = (AvailableTries) getEntityManager()
				.getComponent(AvailableTries.class, destroyed.getSourceId());
		avTries = availableTries.getLifeTries();
		avTries--;
		availableTries.setLifeTries(avTries);

		/** Criacao do Evento de Invencible para a entidade recriada */
		InvencibleEvent invencibleEvent = new InvencibleEvent(
				destroyed.getSourceId());

		/** Adicionando evento de invencivel para a entidade. */
		getEntityManager().addEvent(invencibleEvent);

	}

	public void createGameOverEvent(DestroyedEvent destroyed) {
		/** Retira a entidade do Grid */
		destroyEntity(destroyed);
	}

	public void destroyEntity(DestroyedEvent destroyed) {

		/** Remove a entidade da lista de entidades. */
		getEntityManager().removeEntityById(destroyed.getSourceId());
	}

}
