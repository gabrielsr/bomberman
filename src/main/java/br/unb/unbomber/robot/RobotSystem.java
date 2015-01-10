package br.unb.unbomber.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.mostlyoriginal.api.event.common.Event;
import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.event.CommandEvent;
import br.unb.unbomber.event.HitWallEvent;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;

@Wire
public class RobotSystem extends EntitySystem {

	ExecutorService executor;

	ComponentMapper<AI> cmRobot;
	
	EventManager em;
	
	Map<UUID, List<Event>> pendingEvents = new HashMap<>();
	
	//private int currentTime;
	private int millisWait;
	//private int nanoWait;
	
	private static final int NTHREDS = 10;
	
	@SuppressWarnings("unchecked")
	public RobotSystem(){
		super(Aspect.getAspectForAll(AI.class));
	    executor = Executors.newFixedThreadPool(NTHREDS);
	}
	
	
	@Override
	protected void processEntities(ImmutableBag<Entity> robots) {
		List<IRobotPeer> peers = new ArrayList<IRobotPeer>();
		
		//get robots to execute
		for(Entity robotEntity: robots){
			IRobotPeer peer = cmRobot.get(robotEntity).getRobotPeer();
			peer.setUuid(robotEntity.getUuid());
			
			if(pendingEvents.get(robotEntity.getUuid())!=null){
				peer.enqueueEvents(pendingEvents.get(robotEntity.getUuid()));		
				pendingEvents.get(robotEntity.getUuid()).clear();
			}
			peers.add(peer);
		}
		
		// execute robots code and get commands
		List<Future<List<CommandEvent>>> commands = null;
		try {
			commands = executor.invokeAll(peers);
			executor.awaitTermination(millisWait, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			throw new IllegalStateException(e1);
		}
		
		// process robots commands
		for(Future<List<CommandEvent>> commandsByRobot: commands){
			if(commandsByRobot.isDone()){
				try {
					for(CommandEvent command:commandsByRobot.get()){
						em.dispatch(command);
					}
				} catch (InterruptedException | ExecutionException e) {
					throw new IllegalStateException(e);
				}
			}else{
				System.out.println("Computation not concluded!");
			}
		}

	}

	@Subscribe
	public void handle(HitWallEvent event){
		List<Event> eventsByUuid = pendingEvents.get(event.getEntityUuid());
		if(eventsByUuid == null){
			eventsByUuid = new ArrayList<Event>();
			pendingEvents.put(event.getEntityUuid(), eventsByUuid);
		}
		
		eventsByUuid.add(event);
	}

}
	