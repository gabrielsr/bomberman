package br.unb.unbomber.robot;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import net.mostlyoriginal.api.event.common.Event;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.event.CommandEvent;

public interface IRobotPeer extends Callable<List<CommandEvent>> {

	void setUuid(UUID uuid);
	
	/**
	 * Immediately moves your robot in the Direction by distance measured in
	 * cells.
	 * <p/>
	 * This call executes immediately, and does not return until it is complete,
	 * i.e. when the remaining distance to move is 0.
	 * <p/>
	 * If the robot collides with a wall, the move is complete, meaning that the
	 * robot will not move any further.
	 * <p/>
	 * Note that both positive and negative values can be given as input, where
	 * positive values means that the robot is set to move forward, and negative
	 * values means that the robot is set to move backward.
	 * <p/>
	 *
	 * @see robocode.robotinterfaces.IBasicEvents#onHitWall(robocode.HitWallEvent)
	 * @see robocode.robotinterfaces.IBasicEvents#onHitRobot(robocode.HitRobotEvent)
	 */
	void move(Direction direction, float distance);

	/**
	 * Returns the robot's name.
	 *
	 * @return the robot's name.
	 */
	String getName();

	void doCommand(CommandEvent command);

	List<CommandEvent> getCommands();

	void enqueueEvents(List<Event> events);

	void clear();
}
