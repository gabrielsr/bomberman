package br.unb.unbomber.core;


/**
 * A Base System for the Entity Component System (ECS)
 * It init the Entity Manager so Systems that extends this
 * base class don't have to.
 * 
 * @author grodrigues
 *
 */
abstract public class BaseSystem implements System{

	private EntityManager entityManager;
	
	
	/*
	 * Init Game System
	 */
	
	/**
	 * Constructor that gets the default Entity Manager
	 */
	public BaseSystem(){
		entityManager = EntitySystemImpl.getInstance();
	}

	/**
	* Constructor that gets a custom Entity Manager
	*/
	public BaseSystem(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	protected EntityManager getEntityManager(){
		return entityManager;
	}

	/*
	* System Life Cycle
	*/
	
	/**
	* Called when the system is started. If a system want to 
	* have a custom behavior it needs to override this method.
	*/
	public void start(){
	}

	/**
	* Called when the system is finished. If a system want to 
	* have a custom behavior it needs to override this method.
	*/	
	public void stop(){
	}
	
	/**
	* Called every turn. Every System has to implements this method.
	*/
	abstract public void update();


	
}
