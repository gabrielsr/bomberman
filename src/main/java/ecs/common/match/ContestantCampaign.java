package ecs.common.match;

public class ContestantCampaign {
	
	private Contestant contestant;
	
	private int score;
	
	private int victories;
	
	public ContestantCampaign() {
	}

	public ContestantCampaign(Contestant contestant, int score) {
		this.setContestant(contestant);
		this.score = score;
	}

	public Contestant getContestant() {
		return contestant;
	}

	public void setContestant(Contestant contestant) {
		this.contestant = contestant;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getVictories() {
		return victories;
	}

	public void setVictories(int victories) {
		this.victories = victories;
	}

}
