package br.unb.unbomber;

import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.System;
import br.unb.unbomber.systems.BombSystem;
import br.unb.unbomber.systems.LifeSystem;
import br.unb.unbomber.systems.MovimentSystem;
import br.unb.unbomber.systems.TimeSystem;

public class GameMatch {

	
	/** The system. */
	List<System> systems;

	/** The entity manager. */
	EntityManager entityManager;
	
	
	/**
	 * Create the Base Systes for the match.
	 * 
	 */
	public GameMatch() {
		
		//init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		
		systems = new ArrayList<System>();

		systems.add(new TimeSystem(entityManager));

		systems.add(new BombSystem(entityManager));
		//systems.add(new CollisionSystem(entityManager));
		//systems.add(new ExplosionSystem(entityManager));
		systems.add(new LifeSystem(entityManager));
		systems.add(new MovimentSystem(entityManager));
		//systems.add(new PlayerControlSystem(entityManager));

	}
	
	
	
	/**
	 * Include a new System.
	 * 
	 * Used for UI projects to include Render, Sound and Control
	 * Systems.
	 * 
	 * @param system
	 */
	public void addSystem(System system){
		this.systems.add(system);
	}

	public EntityManager getEntityManager(){
		return this.entityManager;
	}
	
	public void update() {

		for(System system:this.systems){
			system.update();
		}
		
	}

}
