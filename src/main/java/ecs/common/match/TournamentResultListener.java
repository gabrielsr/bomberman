package ecs.common.match;

public interface TournamentResultListener {

	public void end(TournamentResult result);

	public void matchEnd(MatchResult lastMatchResult);
	
}
