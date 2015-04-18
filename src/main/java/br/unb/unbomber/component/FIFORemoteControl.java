package br.unb.unbomber.component;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import com.artemis.Component;

public class FIFORemoteControl extends Component{

	private Queue<UUID> remoteBombs;

	public FIFORemoteControl(){
		remoteBombs = new ArrayBlockingQueue<>(16);
	}
	
	public UUID next() {
		return remoteBombs.poll();
	}

	public void include(UUID entityUUID){
		remoteBombs.add(entityUUID);
	}
}
