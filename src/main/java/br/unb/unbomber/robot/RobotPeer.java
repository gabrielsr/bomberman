package br.unb.unbomber.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.event.CommandEvent;
import br.unb.unbomber.event.HitWallEvent;



/**
 * RobotPeer is an object that deals with game mechanics and rules, and makes
 * sure that robots abides the rules.
 */
public class RobotPeer implements IRobotPeer {

	Robot robot;
	
	UUID robotUuid;
	
	List<CommandEvent> commands = new ArrayList<CommandEvent>();

	private List<Event> pendingEvent = new ArrayList<Event>();
	
	public RobotPeer(Robot robot){
		this.robot = robot;
		this.robot.setPeer(this);
	}
	

	@Override
	public void setUuid(UUID uuid) {
		this.robotUuid = uuid;
		
	}
	
    public List<CommandEvent> call() {
    	commands.clear();
    	notifyPendingEvents();
    	robot.run();
		return commands;
    }

	private void notifyPendingEvents() {
		for(Event event:this.pendingEvent){
			notifyEvent(event);
		}
		this.pendingEvent.clear();
	}


	private void notifyEvent(Event event) {
		if(event instanceof HitWallEvent){
			robot.onHitWall((HitWallEvent) event);
		}
	}

	@Override
	public void move(Direction up, float distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doCommand(CommandEvent command) {
		command.setEntityUuid(robotUuid);
		commands.add(command);
	}	

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void clear() {
		commands.clear();
		
	}

	@Override
	public List<CommandEvent> getCommands() {
		return commands;
	}


	@Override
	public void enqueueEvents(List<Event> events) {
		this.pendingEvent.addAll(events);
	}

}
