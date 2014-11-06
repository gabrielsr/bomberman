package br.unb.unbomber.systems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.unb.unbomber.Game;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.GameOverEvent;
import br.unb.unbomber.event.InvencibleEvent;
import br.unb.unbomber.event.TimeOverEvent;

/**
 * Classe reponsável pelas regras e lógicas do Módulo Life.
 * 
 * @version 0.3 06 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class LifeSystem extends BaseSystem {

	/** Define um conjunto de eventos processados. */
	HashSet<Event> processedEvents = new HashSet<Event>();

	/** Instancia o EntityManager a partir da classe pai BaseSystem. */
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
		/** Coleta Eventos de Colisão. */
		List<Event> collisionEvents = getEntityManager().getEvents(
				CollisionEvent.class);
		/** Verifica se a lista de eventos não está vazia. */
		if (collisionEvents != null) {
			for (Event event : collisionEvents) {
				CollisionEvent collision = (CollisionEvent) event;
				
				/**
				 * @if Confere se o evento de colisão já não foi tratado.
				 */
				if(!processedEvents.contains(collision)){
					/**
					 * @if Confere a possibilidade de retirar dano entre as
					 *     entidades do evento de colisão.
					 * 
					 *     Caso seja possível então a vida do alvo da colisão será
					 *     decrementada.
					 * 
					 *     Caso contrário o método seguirá em frente tratando de
					 *     outros eventos.
					 */
					if (isLifeDamage(collision)) {
						Health targetCollisionLife = (Health) getEntityManager()
								.getComponent(Health.class, collision.getTargetId());
	
						takeDamaged(targetCollisionLife);
	
					}
					
					/** Processa evento de colisão mesmo se não retirar vida. */
					processedEvents.add(collision);
				}
			}
		}

		/** Coleta Eventos de destruição de entidades. */
		List<Event> destroyedEvents = getEntityManager().getEvents(
				DestroyedEvent.class);

		/** Verifica se a lista de eventos não está vazia. */
		if (destroyedEvents != null) {
			for (Event event : destroyedEvents) {
				DestroyedEvent destroyed = (DestroyedEvent) event;

				
				/**
				 * @if Confere se o evento de destruição da entidade
				 * 	   já não foi tratado.
				 */
				if(!processedEvents.contains(destroyed)){
					/**
					 * @if Confere a possibilidade de recriar a entidade.
					 * 
					 *     Caso a entidade ainda possuir tentativas de vida então
					 *     ela é recriada no Grid na celula inicial (0,0) e com modo
					 *     Invencible ativo por 270 ticks.
					 * 
					 *     Caso contrário é gerado o evento de Game Over.
					 */
					if (countAvailableTries(destroyed) ) {
						recreateEntity(destroyed);
					} else {
						createGameOverEvent(destroyed);
					}
					
					/** Processa evento de destruição de entidade. */
					processedEvents.add(destroyed);
				}
	
			}
		}

	}

	public boolean isLifeDamage(CollisionEvent collision) {

		/** Id e tipo da entidade que realizou a colisão. */
		int sourceId;
		LifeType entType1 = null;
		/** Id e tipo da entidade que sofreu a colisão. */
		int targetId;
		LifeType entType2 = null;
		/**
		 * Booleano que irá indicar a possibilidade de retirar dano de uma
		 * entidade que sofreu alguma colisão.
		 */
		boolean canTakeDamage = false;

		/** Coleta as identidades dos agentes do evento de colisão. */
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
		 *     Caso ambos forem definidos então a funcao auxiliar de validacao
		 *     de danos entre colisoes será chamada retornando a possibilidade
		 *     de colisão entre os tipos passados.
		 * 
		 *     Caso contrario e retornado false.
		 */
		if (entType1 != null && entType2 != null) {
			canTakeDamage = permittedTypeDamage(entType1, entType2);
		}

		return canTakeDamage;
	}

	private void takeDamaged(Health targetCollisionLife) {

		/** Vida da entidade que sofreu a colisão. */
		int lifeEntity;

		/**
		 * @if Confere a possibilidade de retirar vida da entidade.
		 * 
		 *     Caso seja possivel então sua vida será decrementada.
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
			 *     Caso nao possua mais vida então e atribuido que nao será mais
			 *     permitido retirar vida desta entidade *enquanto este nao for
			 *     recriado* e o componente será passado para um funcao auxiliar
			 *     de Criação de evento de destruicao da entidade.
			 * 
			 *     Caso contrario e atribuido que será permitido retirar vida
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
	 * Metodo que avalia a possibilidade de ser retirádo dano de alguma entidade
	 * em um evento de colisão.
	 * 
	 * @param entType1
	 *            Entidade que realizou a colisão.
	 * @param entType2
	 *            Entidade que sofreu a colisão.
	 * @return canTakeDamage
	 */
	public boolean permittedTypeDamage(LifeType entType1, LifeType entType2) {
		/**
		 * Booleano que irá indicar a possibilidade de retirar dano de uma
		 * entidade que sofreu alguma colisão.
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
	private void createDestroyEvent(Health targetCollisionLife) {

		/** Cria evento de destruicao da entidade. */
		DestroyedEvent destroyEntity = new DestroyedEvent(
				targetCollisionLife.getEntityId());

		/** Adiciona este evento ao gerenciamento de entidades. */
		getEntityManager().addEvent(destroyEntity);

	}

	/**
	 * Metodo que avalia se e possivel retirar uma tentativa de vida da entidade
	 * que será destruída.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que será destruída.
	 * @return haveLifeTries
	 */
	public boolean countAvailableTries(DestroyedEvent destroyed) {
		/**
		 * Booleano que irá indicar a possibilidade de retirar uma tentativa de
		 * vida da entidade que foi destruída.
		 */
		boolean haveLifeTries;
		/** Quantidade de tentativas de vida que a entidade possui. */
		int lifeTries;

		/**
		 * Coleta componente de quantidade de tentativas de vidas pelo Id da
		 * entidade que vai ser destruída.
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
	 *            Evento que contem a Id da entidade que será recriada.
	 */
	private void recreateEntity(DestroyedEvent destroyed) {
		/** Quantidade de tentativas de vida que a entidade possui. */
		int avTries;

		/** Atribuição da entidade destruída a celula inicial. */
		CellPlacement placement = (CellPlacement) getEntityManager()
				.getComponent(CellPlacement.class, destroyed.getSourceId());
		placement.setCellX(0);
		placement.setCellY(0);

		/** Atribuição da vida incial da entidade destruída. */
		Health life = (Health) getEntityManager().getComponent(Health.class,
				destroyed.getSourceId());
		life.setLifeEntity(1);
		life.setCanTakeDamaged(true);

		/** Decrementa de uma tentativa de vida da entidade destruída. */
		AvailableTries availableTries = (AvailableTries) getEntityManager()
				.getComponent(AvailableTries.class, destroyed.getSourceId());
		avTries = availableTries.getLifeTries();
		avTries--;
		availableTries.setLifeTries(avTries);

		/** Criação do Evento de Invencible para a entidade recriada */
		InvencibleEvent invencibleEvent = new InvencibleEvent(
				destroyed.getSourceId());

		/** Adicionando evento de invencível para a entidade. */
		getEntityManager().addEvent(invencibleEvent);

	}

	private void createGameOverEvent(DestroyedEvent destroyed) {
		/** Criação do Evento de Game Over para a entidade destruída. */
		GameOverEvent gameOverEvent = new GameOverEvent(destroyed.getSourceId());

		/** Adicionando evento de game over. */
		getEntityManager().addEvent(gameOverEvent);

	}

	/**
	 * Metodo que destrói uma entidade do grid.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que será destruída.
	 */
	private void destroyEntity(DestroyedEvent destroyed) {
		/** Remove a entidade da lista de entidades. */
		getEntityManager().removeEntityById(destroyed.getSourceId());
	}

}
