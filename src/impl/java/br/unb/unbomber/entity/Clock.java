package br.unb.unbomber.entity;

public class Clock implements Ticks {

	private long time=0;
	
	public void tick(){
		time++;
	}
	
	public long getTime(){
		return time;
	}
}
