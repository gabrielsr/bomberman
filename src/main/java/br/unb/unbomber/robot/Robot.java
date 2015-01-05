package br.unb.unbomber.robot;

import java.util.UUID;

import br.unb.unbomber.component.Direction;
import br.unb.unbomber.event.CommandEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.robot.events.HitWallEvent;


public class Robot extends BaseRobot {


	
	private UUID entityUuid;

	/**
	 * Constructs a new robot.
	 */
	public Robot() {}
	
	
	
	/**
	 * The main method in every robot. You must override this to set up your
	 * robot's basic behavior.
	 * <p/>
	 * Example:
	 * <pre>
	 *   // A basic robot that moves around in a square
	 *   public void run() {
	 *       while (true) {
	 *           goUp(2);
	 *           goRight(2);
	 *       }
	 *   }
	 * </pre>
	 */
	public void run() {}
	
	
	/**
	 * Immediately moves your robot up by distance measured cells.
	 * <p/>
	 * This call executes immediately, and does not return until it is complete,
	 * i.e. when the remaining distance to move is 0.
	 * <p/>
	 * If the robot collides with a wall, the move is complete, meaning that the
	 * robot will not move any further.
	 * <p/>
	 * Note that both positive and negative values can be given as input,
	 * where negative values means that the robot is set to move backward
	 * instead of forward.
	 * <p/>
	 * Example:
	 * <pre>
	 *   // Move the robot 2 cells up
	 *   goUp(2);
	 *
	 *   // Afterwards, move the robot 50 pixels backward
	 *   goUp(-2);
	 * </pre>
	 *
	 * @param distance the distance to move ahead measured in pixels.
	 *                 If this value is negative, the robot will move back instead of ahead.
	 * @see #back(double)
	 * @see #onHitWall(HitWallEvent)
	 * @see #onHitRobot(HitRobotEvent)
	 */
	public void goUp() {
		go(Direction.UP);
	}
	
	
	public void go(Direction direction) {
		final MovementCommandEvent command = new MovementCommandEvent(
				direction, getUUID());
			
		doIt(command);
	}
	
	Object goinOn;


	public void doIt(CommandEvent command){
		if (peer != null) {			
			peer.doCommand(command);
		} else {
			uninitializedException();
		}
	}
	
	
	private UUID getUUID() {
		return this.entityUuid;
	}
	

	/**
	 * Throws a RobotException. This method should be called when the robot's peer
	 * is uninitialized.
	 */
	static void uninitializedException() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		String methodName = trace[2].getMethodName();

		throw new RobotException(
				"You cannot call the " + methodName
				+ "() method before your run() method is called, or you are using a Robot object that the game doesn't know about.");
	}



	public void onHitWall(HitWallEvent event) { }
}
