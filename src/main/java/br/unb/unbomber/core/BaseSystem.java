package br.unb.unbomber.core;

/**
 * A Base System for the Entity Component System (ECS) It init the Entity
 * Manager so Systems that extends this base class don't have to.
 * 
 * @author Gabriel Rodrigues <gabrielsr@gmail.com>
 *
 */
public abstract class BaseSystem implements System {

	/** The entity manager. */
	private EntityManager entityManager;

	/*
	 * Init Game System
	 */

	/**
	 * Constructor that gets the default Entity Manager.
	 */
	public BaseSystem() {
		entityManager = EntityManagerImpl.getInstance();
	}

	/**
	 * Constructor that gets a custom Entity Manager.
	 *
	 * @param entityManager
	 *            the entity manager
	 */
	public BaseSystem(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/*
	 * System Life Cycle
	 */

	/**
	 * Called when the system is started. If a system want to have a custom
	 * behavior it needs to override this method.
	 */
	@Override
	public void start() {
	}

	/**
	 * Called when the system is finished. If a system want to have a custom
	 * behavior it needs to override this method.
	 */
	@Override
	public void stop() {
	}

	/**
	 * Called every turn. Every System has to implements this method.
	 */
	@Override
	public abstract void update();

}
