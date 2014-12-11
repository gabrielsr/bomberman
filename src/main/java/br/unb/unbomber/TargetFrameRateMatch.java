package br.unb.unbomber;

import java.util.Date;

import br.unb.unbomber.core.BaseSystem;

public class TargetFrameRateMatch extends GameMatch {

	long lastTickTimeInMillis;
	
	long targetEnlapseMillis;
	
	public TargetFrameRateMatch(int rate){
		super();

		targetEnlapseMillis = 1000/rate;
	}
	public TargetFrameRateMatch(){
		super();
		int rate = 30;
		targetEnlapseMillis = 1000/rate;
	}
	
	@Override
	public void update() {
		super.update();
		waitTime();
	}
	
	public void waitTime(){
		long passed = now() - lastTickTimeInMillis;
		long toBeWaited = targetEnlapseMillis - passed;
		if(toBeWaited>0){
			try {
				Thread.sleep(toBeWaited);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lastTickTimeInMillis = (new Date()).getTime();
	}
	public static long now(){
		return ((new Date()).getTime());
	}
	
	/**
	 * 1 sec turn, just for test
	 * @param args
	 */
	public static void main(String[] args){
		
		GameMatch testMatch = new TargetFrameRateMatch(1);
		
		/** A Sysout System */
		BaseSystem printSystem = new BaseSystem(){
			public void update(){
				System.out.println(now());
			}
		};
		
		testMatch.addSystem(printSystem);
		
		for(int i = 0; i<1000; i++){
			testMatch.update();
		}
	}

	
}
