package br.unb.unbomber.misc;

import java.util.ArrayList;
import java.util.List;

import net.mostlyoriginal.api.event.common.EventManager;

import com.artemis.EntitySystem;
import com.artemis.World;

public class UpdateRunner {

	private World world;
	
	private List<Updatable> updatables;
		
	private int numberOfTimes;

	private UpdateRunner(){
		updatables = new ArrayList<Updatable>();
	}
	
	public static UpdateRunner update() {
		return new UpdateRunner();
	}

	public UpdateRunner forThis(World world) {
		this.world = world;
		return this;
	}

	public UpdateRunner with(EntitySystem system) {
		world.setSystem(system);
		return this;
	}

	public UpdateRunner with(EventManager eventManager) {
		world.setManager(eventManager);
		return this;
	}
	
	public UpdateRunner with(Updatable updatable) {
		updatables.add(updatable);
		return this;
	}


	public UpdateRunner repeat(int numberOfTimes){
		this.numberOfTimes = numberOfTimes;
		return this;
	}

	public UpdateRunner times() {
		
		for (int i = 0; i < numberOfTimes; i++) {
			run();
		}
		return this;
	}

	public UpdateRunner run() {
		for(Updatable updatable:updatables){
			updatable.update();
		}
		world.process();
		return this;
	}



}
