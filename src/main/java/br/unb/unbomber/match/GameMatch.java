package br.unb.unbomber.match;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.mostlyoriginal.api.event.common.EventManager;
import br.unb.unbomber.systems.BombSystem;
import br.unb.unbomber.systems.ExplosionSystem;
import br.unb.unbomber.systems.MovementSystem;
import br.unb.unbomber.systems.TimeSystem;

import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.UuidEntityManager;

public class GameMatch {
	
	World world;
	
	private final static Logger LOGGER = Logger.getLogger(GameMatch.class.getName()); 
	
	
	/**
	 * Create the Base Systes for the match.
	 * 
	 */
	public GameMatch() {
		
		world = new World();
		world.setSystem(new TimeSystem());
		world.setSystem(new MovementSystem());
		world.setSystem(new BombSystem());
		world.setSystem(new ExplosionSystem());
		
	}
	
	/**
	 * Include a new System.
	 * 
	 * Used for UI projects to include Render, Sound and Control
	 * Systems.
	 * 
	 * @param system
	 */
	public void addSystem(EntitySystem system){
		world.setSystem(system);
	}

	public void start() {
		
		world.setManager(new EventManager());
		world.setManager(new UuidEntityManager());
		world.setManager(new GroupManager());
		world.initialize();
	}
		
	public void update(float delta) {
		try{
			world.setDelta(delta);
			world.process();
		/** Log the system errors and continue*/
		}catch(Exception e){
			log("Not expected error processing game systems", e);
		}
	}
	
	public void log(String msg,Throwable thrown){
		LOGGER.log(Level.SEVERE, msg, thrown);
	}

}
