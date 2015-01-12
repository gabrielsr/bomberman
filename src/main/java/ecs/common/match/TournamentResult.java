package ecs.common.match;

import java.util.ArrayList;
import java.util.List;

public class TournamentResult {

	private Contestant champion;

	private List<ContestantCampaign> campaigns;
	
	public Contestant getChampion() {
		return champion;
	}

	public void setChampion(Contestant champion) {
		this.champion = champion;
	}

	public List<ContestantCampaign> getCampaigns() {
		if(campaigns == null){
			campaigns = new ArrayList<>();
		}
		return campaigns;
	}

	public void setCampaigns(List<ContestantCampaign> campaigns) {
		this.campaigns = campaigns;
	}
	
	public int increaseVictories(Contestant contestant){
		for(ContestantCampaign contestantCamp : getCampaigns()){
			if(contestantCamp.getContestant().equals(contestant)){
				int victoriesCount = contestantCamp.getVictories() + 1;
				contestantCamp.setVictories(victoriesCount);
				return victoriesCount;
			}
		}
		return 0;
	}
	
}
