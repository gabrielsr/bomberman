package br.unb.unbomber.match;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.mostlyoriginal.api.event.common.EventManager;
import br.unb.unbomber.robot.RobotSystem;
import br.unb.unbomber.systems.BlockSystem;
import br.unb.unbomber.systems.BombSystem;
import br.unb.unbomber.systems.CollisionSystem;
import br.unb.unbomber.systems.ExplosionSystem;
import br.unb.unbomber.systems.KickSystem;
import br.unb.unbomber.systems.LifeSystem;
import br.unb.unbomber.systems.MovementSystem;
import br.unb.unbomber.systems.PowerUpSystem;
import br.unb.unbomber.systems.ThrowSystem;
import br.unb.unbomber.systems.TimeSystem;

import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.UuidEntityManager;

import ecs.common.match.Match;

public class GameMatch implements Match {
	
	protected World world;
	
	private final static Logger LOGGER = Logger.getLogger(GameMatch.class.getName()); 
	
	public enum State {
		RUNNING,
		FINISHING,
		FINISHED
	}
	
	public State state = State.RUNNING;
	
	
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
		world.setSystem(new CollisionSystem());
		world.setSystem(new RobotSystem());
		world.setSystem(new LifeSystem());
		world.setSystem(new PowerUpSystem());		
		world.setSystem(new BlockSystem());
		world.setSystem(new KickSystem());
		world.setSystem(new ThrowSystem());
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
