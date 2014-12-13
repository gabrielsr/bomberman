package br.unb.unbomber.core;

import java.util.ArrayList;
import java.util.List;

public class UpdateRunner {

	private List<Updatable> systems;
	
	private int numberOfTimes;

	private UpdateRunner(){
		systems = new ArrayList<Updatable>();
	}
	
	public static UpdateRunner update() {
		return new UpdateRunner();
	}

	public UpdateRunner forThis(Updatable system) {
		systems.add(system);
		return this;
	}

	public UpdateRunner forThis(System system) {
		system.start();
		return forThis((Updatable) system);
	}
	
	public UpdateRunner repeat(int numberOfTimes){
		this.numberOfTimes = numberOfTimes;
		return this;
	}

	public UpdateRunner times() {
		for (; numberOfTimes >= 0; numberOfTimes--) {
			run();
		}
		return this;
	}

	public UpdateRunner run() {
		for (Updatable system : this.systems) {
			system.update();
		}
		return this;
	}

}
