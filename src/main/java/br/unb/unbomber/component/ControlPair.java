package br.unb.unbomber.component;

import br.unb.unbomber.event.ActionCommandEvent.ActionType;


public class ControlPair {

	public enum CommandType{
		ACTION,
		MOVEMENT
	}
	
	public enum Command{
		COMMAND_UP(CommandType.MOVEMENT, Direction.UP),
		COMMAND_DOWN(CommandType.MOVEMENT, Direction.DOWN),
		COMMAND_LEFT(CommandType.MOVEMENT, Direction.LEFT),
		COMMAND_RIGHT(CommandType.MOVEMENT, Direction.RIGHT),
		COMMAND_DROP(CommandType.ACTION, ActionType.DROP_BOMB),
		COMMAND_THROW(CommandType.ACTION, ActionType.THROW),
		COMMAND_REMOTE(CommandType.ACTION, ActionType.EXPLODE_REMOTE_BOMB),
		COMMAND_REMOTE2(CommandType.ACTION, ActionType.TRIGGERS_REMOTE_BOMB);
		
		
		public CommandType type;
		public Object command;
		
		private Command(CommandType type, Object command){
			this.type = type;
			this.command = command;
		}
		
		CommandType getType(){
			return this.type;
		}
	}
	
	
	
	private int key;
	
	private Command command;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

}
