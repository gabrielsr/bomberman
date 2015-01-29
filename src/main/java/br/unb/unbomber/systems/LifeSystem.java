package br.unb.unbomber.systems;

import java.util.UUID;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.component.AvailableTries;
import br.unb.unbomber.component.Health;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.DestroyedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;

import ecs.common.match.GameOverEvent;

/**
 * Classe reponsável pelas regras e lógicas do Módulo Life.
 * 
 * @version 0.4 19 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

@Wire
public class LifeSystem extends VoidEntitySystem {

	
	ComponentMapper<LifeType> cmLifeType;
	
	ComponentMapper<Health> cmHealth;
	
	ComponentMapper<AvailableTries> cmAvailableTries;
	
	ComponentMapper<Position> cmPosition;
	
	UuidEntityManager uuidManager;
	
	EventManager em;
	
	@Subscribe
	public void handle(CollisionEvent collision){

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
				takeDamaged(collision);
			}
	}

	@Subscribe
	public void handle(DestroyedEvent destroyed) {
		/**
		 * @if Confere a possibilidade de recriar a entidade.
		 * 
		 *     Caso a entidade for do tipo CHAR então será averiguada a
		 *     quantidade de vida da entidade e verificada a possibilidade de
		 *     recriação da entidade.
		 * 
		 *     Caso contrário a entidade será destruída.
		 * 
		 */
		if (permittedRecreate(destroyed)) {
			/**
			 * @if Confere a quantidade de tentativas de vida da entidade.
			 * 
			 *     Caso a entidade ainda possuir tentativas de vida então ela é
			 *     recriada no Grid na celula inicial (0,0) e com modo
			 *     Invencible ativo por 270 ticks.
			 * 
			 *     Caso contrário é gerado o evento de Game Over.
			 */
			if (countAvailableTries(destroyed)) {
				recreateEntity(destroyed);
			} else {
				destroyEntity(destroyed);
				createGameOverEvent(destroyed);
			}
		} else {
			destroyEntity(destroyed);
		}
	}

	@Subscribe
	public void handle(InAnExplosionEvent explosion) {
		/**
		 * @if Confere a possibilidade de retirar dano da entidade do evento de
		 *     explosão.
		 * 
		 *     Caso seja possível então a vida do alvo da explosão será
		 *     decrementada.
		 * 
		 *     Caso contrário o método seguirá em frente tratando de outros
		 *     eventos.
		 */
		if (isDamageExplosion(explosion)) {
			takeDamagedExplosion(explosion);

		}
	}

	/**
	 * Método que confere se a colisão terá algum efeito à vida de alguma
	 * entidade.
	 * 
	 * @param collision
	 *            Evento que contem a Id da entidade que colidiu e a Id da
	 *            entidade que sofreu a colisão.
	 * @return boolean Retorna true se for permitido a possibilidade de retirar
	 *         dano de uma entidade que sofreu alguma colisão, e false caso
	 *         contrário.
	 */
	public boolean isLifeDamage(CollisionEvent collision) {

		/** Id e tipo da entidade que realizou a colisão. */
		UUID sourceId;
		LifeType entType1 = null;
		/** Id e tipo da entidade que sofreu a colisão. */
		UUID targetId;
		LifeType entType2 = null;

		/** Coleta as identidades dos agentes do evento de colisão. */
		sourceId = collision.getSourceUuid();
		targetId = collision.getTargetUuid();

		/** Procura o tipo da entidade pela a Id da mesma. */
		entType1 = cmLifeType.getSafe(uuidManager.getEntity(sourceId));

		entType2 = cmLifeType.getSafe(uuidManager.getEntity(targetId));

		/**
		 * Retorna boolean true se ambas entidades tiverem os tipos definidos e
		 * a função auxiliar de validação de danos entre colisões retornar a
		 * possibilidade como true de colisão entre os tipos passados. Caso
		 * contrário retorna false.
		 */
		return ((entType1 != null && entType2 != null) && permittedTypeDamage(
				entType1, entType2));

	}

	/**
	 * Método que retira a vida da entidade que sofreu a colisão.
	 * 
	 * @param collision
	 *            Evento de colisão das entidades.
	 */
	private void takeDamaged(CollisionEvent collision) {
		/** Vida da entidade que sofreu a colisão. */
		int lifeEntity;
		Entity source = uuidManager.getEntity(collision.getSourceUuid());
		Entity target = uuidManager.getEntity(collision.getTargetUuid());
		Health targetHealth;

		/** Coleta os tipos das entidades da colisão. */
		LifeType targetType = cmLifeType.getSafe(target);

		if (targetType.getType() == Type.MONSTER) {
			Entity temp = source;
			source = target;
			target = temp;
		}
		
		targetHealth = cmHealth.getSafe(source);

		/**
		 * @if Confere a possibilidade de retirar vida da entidade.
		 * 
		 *     Caso seja possível então sua vida será decrementada.
		 * 
		 *     Caso contrário o método sai sem realizar nenhuma ação.
		 */
		if (targetHealth.isCanTakeDamaged()) {
			/** Coleta a vida da entidade. */
			lifeEntity = targetHealth.getLifeEntity();
			/** Decrementa a vida da entidade. */
			lifeEntity--;
			/** Atribui a vida decrementada a entidade de origem. */
			targetHealth.setLifeEntity(lifeEntity);

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
				targetHealth.setCanTakeDamaged(false);

				createDestroyEvent(source.getUuid(), target.getUuid());

			} else {
				targetHealth.setCanTakeDamaged(true);
			}

		}
	}

	/**
	 * Método que avalia a possibilidade de ser retirado dano de alguma entidade
	 * em um evento de colisão.
	 * 
	 * @param entTypes
	 *            Componente de vida das entidades.
	 * @return boolean Retorna true se for possível retirar dano de uma entidade
	 *         que sofreu alguma colisão.
	 */
	public boolean permittedTypeDamage(LifeType... entTypes) {
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
			return ((entTypes[0].getType() == Type.MONSTER && entTypes[1]
					.getType() == Type.CHAR)
					|| (entTypes[0].getType() == Type.CHAR && entTypes[1].getType() == Type.MONSTER)
					);

		} else if (entTypes.length == 1) {
			return (entTypes[0].getType() == Type.MONSTER || entTypes[0]
					.getType() == Type.CHAR);

		}

		return false;
	}

	/**
	 * Método que cria evento de destruição de uma entidade.
	 * 
	 * @param sourceId
	 *            Identidade que gerou a destruição.
	 * @param targetId
	 *            Identidade que foi destruída.
	 */
	private void createDestroyEvent(UUID sourceId, UUID targetId) {

		/** Cria evento de destruição da entidade. */
		DestroyedEvent destroyEntity = new DestroyedEvent(sourceId, targetId);

		/** Adiciona este evento ao gerenciamento de eventos. */
		em.dispatch(destroyEntity);

	}

	/**
	 * Método que avalia se é possível retirar uma tentativa de vida da entidade
	 * que será destruída.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que será destruída.
	 * @return boolean Retorna true se for possível retirar uma tentativa de
	 *         vida da entidade que foi destruída. Caso contrário é retornado
	 *         falso.
	 */
	public boolean countAvailableTries(DestroyedEvent destroyed) {
		/** Quantidade de tentativas de vida que a entidade possui. */
		int lifeTries;

		/**
		 * Coleta componente de quantidade de tentativas de vidas pelo Id da
		 * entidade que vai ser destruída.
		 */
		AvailableTries availableTries = cmAvailableTries.get(
				uuidManager.getEntity(destroyed.getTargetId()));

		lifeTries = availableTries.getLifeTries();

		return (lifeTries > 0);

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
		Entity target = uuidManager.getEntity(destroyed.getTargetId());

		/** Atribuição da entidade destruída a celula inicial. */
		Position placement = cmPosition.get(target);
		placement.setCellX(0);
		placement.setCellY(0);

		/** Atribuição da vida incial da entidade destruída. */
		Health life = cmHealth.get(target);;
		life.setLifeEntity(1);
		life.setCanTakeDamaged(true);

		/** Decrementa de uma tentativa de vida da entidade destruída. */
		AvailableTries availableTries = cmAvailableTries.get(target);
		avTries = availableTries.getLifeTries();
		avTries--;
		availableTries.setLifeTries(avTries);

		/** Criação do Evento de Invencible para a entidade recriada */
		//InvencibleEvent invencibleEvent = new InvencibleEvent(
			//	destroyed.getTargetId());

		/** Adicionando evento de invencível para a entidade. */
		//getEntityManager().addEvent(invencibleEvent);

	}

	/**
	 * Método que cria o evento de Game Over.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que foi destruída.
	 */
	private void createGameOverEvent(DestroyedEvent destroyed) {
		/** Criação do Evento de Game Over para a entidade destruída. */
		GameOverEvent gameOverEvent = new GameOverEvent(destroyed.getTargetId());

		/** Adicionando evento de game over. */
		em.dispatch(gameOverEvent);
	}

	/**
	 * Metodo que destrói uma entidade do grid.
	 * 
	 * @param destroyed
	 *            Evento que contem a Id da entidade que será destruída.
	 */
	private void destroyEntity(DestroyedEvent destroyed) {
		/** Remove a entidade da lista de entidades. */
		Entity target = uuidManager.getEntity(destroyed.getTargetId());
		target.deleteFromWorld();
	}

	/**
	 * Método que confere se a colisão terá algum efeito à vida de alguma
	 * entidade.
	 * 
	 * @param explosion
	 *            Evento que contem a Id da entidade que sofrerá dano da
	 *            explosão.
	 * @return boolean Retorna true se for possível retirar dano de uma entidade
	 *         que está na explosão.
	 */
	public boolean isDamageExplosion(InAnExplosionEvent explosion) {

		/** Id e tipo da entidade que está na explosão. */
		Entity hitEntity = uuidManager.getEntity(explosion.getHitUuid());

		LifeType entType = null;

		/** Procura o tipo da entidade pela a Id da mesma. */
		entType = cmLifeType.getSafe(hitEntity);
		/**
		 * Retorna boolean true se caso for definido o tipo da entidade, e a
		 * função auxiliar de validação de danos entre colisões retornar a
		 * possibilidade como true. Caso contrário e retornado false.
		 */
		return (entType != null && permittedTypeDamage(entType));

	}

	/**
	 * Método que avalia a possibilidade da entidade ser recriada.
	 * 
	 * @param destroyed
	 *            Id da entidade que foi destruída.
	 * @return boolean Retorna true se for possível a entidade passada ser
	 *         recriada.
	 */
	public boolean permittedRecreate(DestroyedEvent destroyed) {
		/** Declaração da Id e do tipo da entidade. */
		Entity target = uuidManager.getEntity(destroyed.getTargetId());
		LifeType entType;

		/** Definição da Id e do tipo da entidade. */
		entType = cmLifeType.getSafe(target);

		/**
		 * Retorna true se foi atribuído algum tipo de entidade ao componente
		 * declarado no escopo deste método e caso a entidade for do tipo Char.
		 * Caso contrário não será possível, como por exemplo um tipo Monster.
		 */
		return (entType != null && entType.getType() == Type.CHAR);

	}

	/**
	 * Método que retira a vida da entidade em uma explosão.
	 * 
	 * @param explosion
	 *            Evento de explosão que possui a Id que sofrerá dano.
	 */
	private void takeDamagedExplosion(InAnExplosionEvent explosion) {
		int lifeEntity;
		Entity hit = uuidManager.getEntity(explosion.getHitUuid());
		
		Health targetCollisionLife = cmHealth.get(hit);

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

				createDestroyEvent(explosion.getExplosionCause(),
						hit.getUuid());

			} else {
				targetCollisionLife.setCanTakeDamaged(true);
			}

		}
	}

	@Override
	protected void processSystem() {
		// TODO Auto-generated method stub
		
	}

}
