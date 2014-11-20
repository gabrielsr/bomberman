package br.unb.unbomber.systems;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.AcquiredPowerUpEvent;

/**
 * Classe reponsável pelas regras e lógicas do Módulo Power Up.
 * 
 * @version 0.1 20 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */

public class PowerUpSystem extends BaseSystem {

	/** Define um conjunto de eventos processados. */
	HashSet<Event> processedEvents = new HashSet<Event>();

	/** Instancia o EntityManager a partir da classe pai BaseSystem. */
	public PowerUpSystem() {
		super();
	}

	/**
	 * Construtor da Classe.
	 * 
	 * @param model
	 */
	public PowerUpSystem(EntityManager model) {
		super(model);

	}

	@Override
	public void update() {
		/** Coleta eventos de explosão. */
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
					if (isBlockExplosion(explosion)) {
						createPowerUp(explosion);
					}

					if (isPowerUpExplosion(explosion)) {
						destroyPowerUp(explosion.getIdHit());
					}
				}

				/** Processa evento de explosão. */
				processedEvents.add(explosion);

			}
		}

		/** Coleta eventos de Colisão. */
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
					 * @if Confere a possibilidade de criar evento de power up
					 *     adquirido.
					 */
					if (checkCollision(collision)) {
						createPowerUpEvent(collision);
					}

					/** Processa evento de colisão mesmo se não retirar vida. */
					processedEvents.add(collision);
				}
			}
		}
	}

	/**
	 * Método que confere se quem foi atingido na explosão era um hard bloco.
	 * 
	 * @param explosion
	 *            Evento de explosão.
	 * 
	 * @return boolean Se quem for atingido na explosão for um hard bloco então
	 *         é retornado True.
	 */
	public boolean isBlockExplosion(InAnExplosionEvent explosion) {
		LifeType typeId = (LifeType) getEntityManager().getComponent(
				LifeType.class, explosion.getIdHit());
		return (typeId.getType() == Type.SOFT_BLOCK);
	}

	/**
	 * Método que confere se quem foi atingido na explosão era um power up.
	 * 
	 * @param explosion
	 *            Evento de explosão.
	 * 
	 * @return boolean Se quem for atingido na explosão for um power up então é
	 *         retornado True.
	 */
	public boolean isPowerUpExplosion(InAnExplosionEvent explosion) {
		LifeType typeId = (LifeType) getEntityManager().getComponent(
				LifeType.class, explosion.getIdHit());
		return (typeId.getType() == Type.POWER_UP);
	}

	/**
	 * Método que cria uma entidade power up.
	 * 
	 * @param explosion
	 *            Evento de explosão.
	 */
	private void createPowerUp(InAnExplosionEvent explosion) {
		/** Criação dos componentes da entidade power up. */
		LifeType lifeType = new LifeType(Type.POWER_UP);
		CellPlacement cellPlacement = new CellPlacement();
		/** Coletando o local do bloco que foi destruído. */
		CellPlacement cellBlock = (CellPlacement) getEntityManager()
				.getComponent(CellPlacement.class, explosion.getIdHit());
		/**
		 * Atribuindo local de criação do Power up no mesmo local que o bloco
		 * for destruído.
		 */
		cellPlacement.setCellX(cellBlock.getCellX());
		cellPlacement.setCellY(cellBlock.getCellY());

		/** Gerando um power up de forma aleatória. */
		PowerUp powerRand = new PowerUp(null);

		Random gerador = new Random();

		int num = gerador.nextInt(11);

		switch (num) {
		case 0:
			powerRand = new PowerUp(PowerType.BOMBUP);
			break;
		case 1:
			powerRand = new PowerUp(PowerType.BOXINGGLOVEACQUIRED);
			break;
		case 2:
			powerRand = new PowerUp(PowerType.FIREUP);
			break;
		case 3:
			powerRand = new PowerUp(PowerType.HEALTHUP);
			break;
		case 4:
			powerRand = new PowerUp(PowerType.INVINCIBLE);
			break;
		case 5:
			powerRand = new PowerUp(PowerType.KICKACQUIRED);
			break;
		case 6:
			powerRand = new PowerUp(PowerType.LIFEUP);
			break;
		case 7:
			powerRand = new PowerUp(PowerType.PASSTHROUG);
			break;
		case 8:
			powerRand = new PowerUp(PowerType.PASSTHROUGBOMB);
			break;
		case 9:
			powerRand = new PowerUp(PowerType.REMOTECONTROL);
			break;
		case 10:
			powerRand = new PowerUp(PowerType.SPEEDUP);
			break;
		default:
			// Lançar alguma exceção.
			break;
		}

		/** Criação da entidade de power up. */
		Entity powerUp = getEntityManager().createEntity();

		/** Inicia os componentes entidade power up. */
		powerUp.addComponent(cellPlacement);
		powerUp.addComponent(powerRand);
		powerUp.addComponent(lifeType);

		/** Atualiza a entidade power up com os componentes atribuídos. */
		getEntityManager().update(powerUp);

	}

	/**
	 * Método que destrói a entidade de power up.
	 * 
	 * @param idHit
	 *            Id da entidade POWER_UP que será destruída.
	 */
	private void destroyPowerUp(int idHit) {
		/** Remove a entidade power up. */
		getEntityManager().removeEntityById(idHit);
	}

	/**
	 * Método que confere se a colisão para adquirir um power up é válida.
	 * 
	 * @param collision
	 *            Evento de colisão.
	 * 
	 * @return boolean Se for uma colisão de um CHAR e um POWER_UP então é
	 *         retornado True.
	 */
	public boolean checkCollision(CollisionEvent collision) {
		LifeType typeSourceId = (LifeType) getEntityManager().getComponent(
				LifeType.class, collision.getSourceId());
		LifeType typeTargetId = (LifeType) getEntityManager().getComponent(
				LifeType.class, collision.getTargetId());

		return (typeSourceId.getType() == Type.CHAR && typeTargetId.getType() == Type.POWER_UP);
	}

	/**
	 * Método que cria o evento que indica que uma entidade adquiriu um power up.
	 * 
	 * @param collision
	 *            Evento de colisão.
	 */
	private void createPowerUpEvent(CollisionEvent collision) {
		/** Coleta o componente de power up da entidade CHAR. */
		PowerUp powerUpSource = (PowerUp) getEntityManager().getComponent(
				PowerUp.class, collision.getSourceId());
		/** Coleta o componente de power up da entidade POWER_UP. */
		PowerUp powerUpTarget = (PowerUp) getEntityManager().getComponent(
				PowerUp.class, collision.getTargetId());
		/** Coleta a lista de power ups da entidade POWER_UP. */
		List<PowerType> typeTargetPowerUp = powerUpTarget.getTypes();
		/**
		 * Atribui um item à lista de power ups do componente de power up da
		 * entidade CHAR, sendo que a entidade POWER_UP possui um único tipo de
		 * power up na sua lista de power ups (como se fosse uma característica
		 * da entidade POWER_UP).
		 */
		powerUpSource.setType(typeTargetPowerUp.get(0));

		/** Cria evento de power up adquirido. */
		AcquiredPowerUpEvent acquiredPower = new AcquiredPowerUpEvent(
				typeTargetPowerUp.get(0));
		acquiredPower.setOwnerId(collision.getSourceId());

		/** Adiciona este evento no gerenciador de entidades. */
		getEntityManager().addEvent(acquiredPower);

		/** Após a coleta do POWER_UP então a entidade POWER_UP será destruída. */
		destroyPowerUp(collision.getTargetId());

	}
}
