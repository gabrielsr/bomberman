package ecs.common.match;

public interface MatchFactory {
	
	public Match create(TournamentRules rules, MatchResultListener matchResultListner);
	
}
