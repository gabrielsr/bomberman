package br.unb.unbomber.misc;

import java.util.logging.Level;

public interface Logger {
	
	
	public void log(Level level, String message);
	
	public void log(Level level, String message, Throwable e);
	
	
}
