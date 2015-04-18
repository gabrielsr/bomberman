package ecs.common.match;

import java.util.ArrayList;
import java.util.List;

public class TournamentRules {

	/** matches wins need to be a champion */
	private int winsToChap;
	
	private int matchTime;

	private List<Contestant> contestants;
	

	public int getWinsToChap() {
		return winsToChap;
	}

	public void setWinsToChap(int winsToChap) {
		this.winsToChap = winsToChap;
	}

	public int getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(int matchTime) {
		this.matchTime = matchTime;
	}

	public List<Contestant> getContestants() {
		if(contestants == null){
			contestants = new ArrayList<>();
		}
		return contestants;
	}

	public void setContestants(List<Contestant> contestants) {
		this.contestants = contestants;
	}

	
	
}
