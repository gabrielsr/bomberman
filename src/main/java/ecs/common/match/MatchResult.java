package ecs.common.match;

import java.util.List;

public class MatchResult {

	public enum MatchStatus {
		WIN,
		DRAW
	}
	
	private MatchStatus status;

	private List<ContestantCampaign> scores;

	private Contestant winner;
	
	public MatchStatus getStatus() {
		return status;
	}

	public void setStatus(MatchStatus status) {
		this.status = status;
	}
	
	public List<ContestantCampaign> getScores() {
		return scores;
	}

	public void setScores(List<ContestantCampaign> scores) {
		this.scores = scores;
	}

	public Contestant getWinner() {
		return winner;
	}

	public void setWinner(Contestant winner) {
		this.winner = winner;
	}
	
}
