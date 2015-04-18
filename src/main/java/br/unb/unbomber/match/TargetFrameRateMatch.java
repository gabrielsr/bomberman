package br.unb.unbomber.match;

import java.util.Date;

public class TargetFrameRateMatch extends GameMatch {

	long lastTickTimeInMillis;
	
	long targetEnlapseMillis;
	
	public TargetFrameRateMatch(int rate){
		super();

		targetEnlapseMillis = 1000/rate;
	}
	public TargetFrameRateMatch(){
		super();
		int rate = 60;
		targetEnlapseMillis = 1000/rate;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
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

	
}
