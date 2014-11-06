package br.unb.unbomber.systems;

import java.util.HashSet;
import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.GameOverEvent;
import br.unb.unbomber.event.InvencibleEvent;
import br.unb.unbomber.event.InAnExplosionEvent;

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
				if (!processedEvents.contains(collision)) {
					/**
					 * @if Confere a possibilidade de retirar dano entre as
					 *     entidades do evento de colisão.
					 * 
					 *     Caso seja possível então a vida do alvo da colisão
					 *     será decrementada.
					 * 
					 *     Caso contrário o método seguirá em frente tratando de
					 *     outros eventos.
					 */
					if (isLifeDamage(collision)) {
						Health targetCollisionLife = (Health) getEntityManager()
								.getComponent(Health.class,
										collision.getTargetId());

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
				 * @if Confere se o evento de destruição da entidade já não foi
				 *     tratado.
				 */
				if (!processedEvents.contains(destroyed)) {
					/**
					 * @if Confere a possibilidade de recriar a entidade.
					 * 
					 *     Caso a entidade for do tipo CHAR então será
					 *     averiguada a quantidade de vida da entidade e
					 *     verificada a possibilidade de recriação da entidade.
					 * 
					 *     Caso contrário a entidade será destruída.
					 * 
					 */
					if (permittedRecreate(destroyed)) {
						/**
						 * @if Confere a quantidade de tentativas de vida da
						 *     entidade.
						 * 
						 *     Caso a entidade ainda possuir tentativas de vida
						 *     então ela é recriada no Grid na celula inicial
						 *     (0,0) e com modo Invencible ativo por 270 ticks.
						 * 
						 *     Caso contrário é gerado o evento de Game Over.
						 */
						if (countAvailableTries(destroyed)) {
							recreateEntity(destroyed);
						} else {
							createGameOverEvent(destroyed);
						}
					} else {
						destroyEntity(destroyed);
					}

					/** Processa evento de destruição de entidade. */
					processedEvents.add(destroyed);
				}

			}
		}

		/** Coleta Eventos de explosão. */
		List<Event> explosionEvents = getEntityManager().getEvents(
				InAnExplosionEvent.class);

		/** Verifica se a lista de eventos não está vazia. */
		if (explosionEvents != null) {
			for (Event event : explosionEvents) {
				InAnExplosionEvent explosion = (InAnExplosionEvent) event;

				/**
				 * @if Confere se o evento de explosão já não foi tratado.
				 */
				if (!processedEvents.contains(explosion)) {
					/**
					 * @if Confere a possibilidade de retirar dano da entidade
					 *     do evento de explosão.
					 * 
					 *     Caso seja possível então a vida do alvo da explosão
					 *     será decrementada.
					 * 
					 *     Caso contrário o método seguirá em frente tratando de
					 *     outros eventos.
					 */
					if (isDamageExplosion(explosion)) {
						Health targetCollisionLife = (Health) getEntityManager()
								.getComponent(Health.class,
										explosion.getIdHit());

						takeDamaged(targetCollisionLife);

					}

					/** Processa evento de colisão mesmo se não retirar vida. */
					processedEvents.add(explosion);
				}

			}
		}

	}

	/**
	 * Método que confere se a colisão terá algum efeito à vida de alguma
	 * entidade.
	 * 
	 * @param collision
	 *            Evento que contem a Id da entidade que colidiu e a Id da
	 *            entidade que sofreu a colisão.
	 * @return canTakeDamage
	 */
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
		 * @if Confere se foi atribuído algum tipo de entidade aos componentes
		 *     declarados no escopo deste metodo.
		 * 
		 *     Caso ambos forem definidos então a função auxiliar de validação
		 *     de danos entre colisões será chamada retornando a possibilidade
		 *     de colisão entre os tipos passados.
		 * 
		 *     Caso contrário e retornado false.
		 */
		if (entType1 != null && entType2 != null) {
			canTakeDamage = permittedTypeDamage(entType1, entType2);
		}

		return canTakeDamage;
	}

	/**
	 * Método que retira a vida da entidade que sofreu a colisão.
	 * 
	 * @param targetCollisionLife
	 *            Componente que possui a quantidade de vida de uma entidade e
	 *            sua Id.
	 */
	private void takeDamaged(Health targetCollisionLife) {

		/** Vida da entidade que sofreu a colisão. */
		int lifeEntity;

		/**
		 * @if Confere a possibilidade de retirar vida da entidade.
		 * 
		 *     Caso seja possível então sua vida será decrementada.
		 * 
		 *     Caso contrário o método sai sem realizar nenhuma ação.
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
			 *     Caso nao possua mais vida então é atribuido que não será mais
			 *     permitido retirar vida desta entidade *enquanto este nao for
			 *     recriado* e o componente será passado para um função auxiliar
			 *     de criação de evento de destruição da entidade.
			 * 
			 *     Caso contrário é atribuído que será permitido retirar vida
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
	 * Método que avalia a possibilidade de ser retirado dano de alguma entidade
	 * em um evento de colisão.
	 * 
	 * @param entTypes
	 *            Componente de vida das entidades.
	 * @return canTakeDamage
	 */
	public boolean permittedTypeDamage(LifeType... entTypes) {
		/**
		 * Booleano que irá indicar a possibilidade de retirar dano de uma
		 * entidade que sofreu alguma colisão.
		 */
		boolean canTakeDamage = false;
		/** Lista de entidades que foi passada para o método. */
		List<LifeType> ents;
		ents = null;

		for (LifeType entType : entTypes) {
			ents.add(entType);
		}

		/**
		 * @if Confere se foi passado algum entidade para o método.
		 */
		if (!ents.isEmpty()) {
			/**
			 * @if Confere se foi passado duas entidades ou uma entidade para o
			 *     método.
			 * 
			 *     Caso for passado dois então o método vai ser utilizado para
			 *     comparar a possibilidade de tirar vida em uma colisão de duas
			 *     entidades.
			 * 
			 *     Caso for passado uma entidade então será averiguada a
			 *     possibilidade de retirar vida dela.
			 */
			if (entTypes.length == 2) {
				if (ents.get(0).getType() == Type.MONSTER
						&& ents.get(1).getType() == Type.CHAR) {
					canTakeDamage = true;
				} else if (ents.get(0).getType() == Type.BOMB
						&& ents.get(1).getType() == Type.MONSTER) {
					canTakeDamage = true;
				} else {
					canTakeDamage = false;
				}
			} else if (entTypes.length == 1) {
				if (ents.get(0).getType() == Type.MONSTER
						|| ents.get(0).getType() == Type.CHAR) {
					canTakeDamage = true;
				} else {
					canTakeDamage = false;
				}
			}
		}

		return canTakeDamage;
	}

	/**
	 * Método que cria evento de destruição de uma entidade.
	 * 
	 * @param targetCollisionLife
	 *            Componente que possui a Id que zerou a vida.
	 */
	private void createDestroyEvent(Health targetCollisionLife) {

		/** Cria evento de destruição da entidade. */
		DestroyedEvent destroyEntity = new DestroyedEvent(
				targetCollisionLife.getEntityId());

		/** Adiciona este evento ao gerenciamento de entidades. */
		getEntityManager().addEvent(destroyEntity);

	}

	/**
	 * Método que avalia se é possível retirar uma tentativa de vida da entidade
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
	 * Método que recria a entidade na célula inicial do Grid e atribui o modo
	 * Invencible a entidade por 270 ticks, o qual nao poderá tomar dano.
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

	/**
	 * Método que cria o evento de Game Over.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que foi destruída.
	 */
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

	/**
	 * Método que confere se a colisão terá algum efeito à vida de alguma
	 * entidade.
	 * 
	 * @param collision
	 *            Evento que contem a Id da entidade que colidiu e a Id da
	 *            entidade que sofreu a colisão.
	 * @return canTakeDamage
	 */
	public boolean isDamageExplosion(InAnExplosionEvent explosion) {

		/** Id e tipo da entidade que está na explosão. */
		int sourceId = explosion.getIdHit();
		LifeType entType = null;
		/**
		 * Booleano que irá indicar a possibilidade de retirar dano da entidade
		 * que está na explosão.
		 */
		boolean canTakeDamage = false;

		/** Procura o tipo da entidade pela a Id da mesma. */
		entType = (LifeType) getEntityManager().getComponent(LifeType.class,
				sourceId);

		/**
		 * @if Confere se foi atribuído algum tipo de entidade aos componentes
		 *     declarados no escopo deste metodo.
		 * 
		 *     Caso foi definido o tipo da entidade, então a função auxiliar de
		 *     validação de danos entre colisões será chamada retornando a
		 *     possibilidade de colisão entre os tipos passados.
		 * 
		 *     Caso contrário e retornado false.
		 */
		if (entType != null) {
			canTakeDamage = permittedTypeDamage(entType);
		}

		return canTakeDamage;
	}

	/**
	 * Método que avalia a possibilidade da entidade ser recriada.
	 * 
	 * @param entType
	 *            Componente de vida da entidade.
	 * @return canRecreate
	 */
	public boolean permittedRecreate(DestroyedEvent destroyed) {
		/**
		 * Booleano que irá indicar a possibilidade de entidade ser recriada.
		 */
		boolean canRecreate = false;
		/** Declaração da Id e do tipo da entidade. */
		int sourceId;
		LifeType entType;

		/** Definição da Id e do tipo da entidade. */
		sourceId = destroyed.getSourceId();
		entType = (LifeType) getEntityManager().getComponent(LifeType.class,
				sourceId);

		/**
		 * @if Confere se foi atribuído algum tipo de entidade ao componente
		 *     declarados no escopo deste metodo.
		 */
		if (entType != null) {
			/**
			 * @if Confere o tipo da entidade.
			 * 
			 *     Caso for um char é possível ser recriado.
			 * 
			 *     Caso contrário não será possível, como por exemplo um tipo
			 *     Monster.
			 */
			if (entType.getType() == Type.CHAR) {
				canRecreate = true;
			} else {
				canRecreate = false;
			}
		}

		return canRecreate;
	}

}
