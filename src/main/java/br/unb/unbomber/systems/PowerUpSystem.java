package br.unb.unbomber.systems;

import java.util.Random;
import java.util.UUID;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.component.PowerUpFont;
import br.unb.unbomber.component.PowerUpInventory;
import br.unb.unbomber.event.CollectedPowerUpEvent;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.misc.EntityBuilder2;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;

/**
 * Classe reponsável pelas regras e lógicas do Módulo Power Up.
 * 
 * @version 0.1 20 Nov 2014
 * @author Grupo 5 - Dayanne <dayannefernandesc@gmail.com>
 */
@Wire
public class PowerUpSystem extends VoidEntitySystem {

	private ComponentMapper<PowerUp> cmPowerUp;

	private ComponentMapper<PowerUpInventory> cmPowerUpInventory;

	private ComponentMapper<PowerUpFont> cmPowerUpFont;

	private ComponentMapper<LifeType> cmLifeType;

	private ComponentMapper<Position> cmPosition;

	private UuidEntityManager uuidEm;

	private EventManager em;

	private enum CollisionResult {
		IGNORE, COLLECT, DESTROY
	}

	/** Instancia o EntityManager a partir da classe pai BaseSystem. */
	public PowerUpSystem() {
		super();
	}

	@Subscribe
	public void handle(InAnExplosionEvent explosion) {
		Entity hit = uuidEm.getEntity(explosion.getHitUuid());

		if (isPowerUpFontExplosion(hit)) {
			createPowerUp(hit);
		}

		if (isPowerUpExplosion(explosion)) {
			destroyPowerUp(explosion.getHitUuid());
		}
	}

	@Subscribe
	public void handle(CollisionEvent collision) {
		/**
		 * @if Confere a possibilidade de criar evento de power up adquirido.
		 */
		switch (checkCollision(collision)) {
		case COLLECT:
			collectPowerUp(collision);
			break;
		case DESTROY:
			destroyPowerUp(collision.getTargetUuid());
			break;
		default:
			break;
		}
	}

	/**
	 * Método que confere se quem foi atingido na explosão era um bloco.
	 * 
	 * @param explosion
	 *            Evento de explosão.
	 * 
	 * @return boolean Se quem for atingido na explosão for um hard bloco então
	 *         é retornado True.
	 */
	public boolean isPowerUpFontExplosion(Entity hit) {
		PowerUpFont powerUpFont = cmPowerUpFont.getSafe(hit);
		return (powerUpFont != null);
	}

	/**
	 * Método que confere se quem foi atingido na explosão era um power up.
	 * 
	 * @param inAnExplosinEvent
	 *            Evento de explosão.
	 * 
	 * @return boolean Se quem for atingido na explosão for um power up então é
	 *         retornado True.
	 */
	public boolean isPowerUpExplosion(InAnExplosionEvent inAnExplosinEvent) {
		/** check if target is a powerup */
		Entity target = uuidEm.getEntity(inAnExplosinEvent.getHitUuid());
		PowerUp powerUpComponent = cmPowerUp.getSafe(target);

		return (powerUpComponent != null);
	}

	/**
	 * Método que cria uma entidade power up.
	 * 
	 * @param explosion
	 *            Evento de explosão.
	 */
	private void createPowerUp(Entity origin) {
		/** Gerando um power up de forma aleatória. */
		PowerUp powerRand = getRandonPowerUp();

		if (powerRand == null) {
			// not this time
			return;
		}

		/** Criação dos componentes da entidade power up. */

		/** Coletando o local do bloco que foi destruído. */
		Position originPosition = cmPosition.get(origin);

		/** Criação da entidade de power up. */
		EntityBuilder2.create(world).withPosition(originPosition.getIndex())
				.withDraw("powerup").with(powerRand).build();
	}

	private PowerUp getRandonPowerUp() {
		PowerUp powerRand = null;

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
			return null;
		}
		return powerRand;
	}

	/**
	 * Método que destrói a entidade de power up.
	 * 
	 * @param uuid
	 *            Id da entidade POWER_UP que será destruída.
	 */
	private void destroyPowerUp(UUID uuid) {
		/** Remove a entidade power up. */
		Entity entity = uuidEm.getEntity(uuid);
		entity.edit().deleteEntity();
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
	public CollisionResult checkCollision(CollisionEvent collision) {
		/** check if target is a powerup */
		Entity target = uuidEm.getEntity(collision.getTargetUuid());
		PowerUp targetPowerUp = cmPowerUp.getSafe(target);
		if (targetPowerUp == null) {
			return CollisionResult.IGNORE;
		}

		/** check if source is a char */
		Entity source = uuidEm.getEntity(collision.getSourceUuid());
		LifeType typeSource = cmLifeType.getSafe(source);
		if (typeSource == null || typeSource.getType() != Type.CHAR) {
			return CollisionResult.DESTROY;
		}
		return CollisionResult.COLLECT;
	}

	/**
	 * @param collision
	 *            Evento de colisão.
	 */
	private void collectPowerUp(CollisionEvent collision) {
		/** Coleta o componente de power up da entidade CHAR. */
		Entity collector = uuidEm.getEntity(collision.getSourceUuid());
		PowerUpInventory collectorInventory = cmPowerUpInventory.get(collector);
		/** Coleta o componente de power up da entidade POWER_UP. */
		Entity target = uuidEm.getEntity(collision.getTargetUuid());
		PowerUp powerUpTarget = cmPowerUp.getSafe(target);

		/** Coleta a lista de power ups da entidade POWER_UP. */
		PowerType typeTargetPowerUp = powerUpTarget.getType();
		/**
		 * Atribui um item à lista de power ups do componente de power up da
		 * entidade CHAR, sendo que a entidade POWER_UP possui um único tipo de
		 * power up na sua lista de power ups (como se fosse uma característica
		 * da entidade POWER_UP).
		 */
		collectorInventory.addType(typeTargetPowerUp);

		/** Cria evento de power up adquirido. */
		CollectedPowerUpEvent acquiredPower = new CollectedPowerUpEvent(
				typeTargetPowerUp);

		// acquiredPower.setOwnerUuid(collision.getSourceUuid());

		/** Adiciona este evento no gerenciador de entidades. */
		em.dispatch(acquiredPower);

		/** Após a coleta do POWER_UP então a entidade POWER_UP será destruída. */
		destroyPowerUp(collision.getTargetUuid());

	}

	@Override
	protected void processSystem() {
		// TODO Auto-generated method stub

	}

}
