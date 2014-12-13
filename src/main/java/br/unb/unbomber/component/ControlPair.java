package br.unb.unbomber.component;

import br.unb.unbomber.event.MovementCommandEvent.MovementType;

public class ControlPair {

	private int key;
	
	private MovementType command;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public MovementType getCommand() {
		return command;
	}

	public void setCommand(MovementType command) {
		this.command = command;
	}

}
