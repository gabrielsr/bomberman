package ecs.common.match;

import ecs.common.match.MatchResult.MatchStatus;


/**
 * A Simple tournament where everyone plays 
 * again each other every match.
 * 
 * The champion is the one that wins more matches.
 * 
 * @author grodrigues
 *
 */
public abstract class TournamentController implements MatchResultListener, ScreenFlowController{

	private TournamentRules rules;
	
	private TournamentResult result;

	private TournamentResultListener tournamentListner;
	
	private MatchResult lastMatchResult;
	
	private boolean end = false;
	
	
	public enum BattleFlow {
		INITIALIZE,
		SET_CONTESTANTS,
		PLAY_THE_MATCH,
		MATCH_RESULT,
		TOURNAMENT_RESULT,
		MAIN_MENU
	}
	
	BattleFlow actual = BattleFlow.INITIALIZE;

	
	public TournamentController(TournamentRules rules, TournamentResultListener listener) {
		super();
		this.rules = rules;
		this.tournamentListner = listener;
	}
	
	public void init(){
		result = new TournamentResult();
	}

	/**
	 * Run the Tournament.
	 */
	public void runAnotherMatch(){
		if(!shouldEnd()){
			actual = BattleFlow.PLAY_THE_MATCH;
			runAMatch();
		}else{
			actual = BattleFlow.TOURNAMENT_RESULT;
			if(tournamentListner!=null){
				tournamentListner.end(result);
			}
			getScreen(actual);
		}
	}

	private boolean shouldEnd() {
		return end;
	}

	private void runAMatch() {
		actual = BattleFlow.PLAY_THE_MATCH;
		getScreen(actual);
	}
	
	private void matchEnd(MatchResult lastMatchResult2) {
		nextScreen();
	}

	
	public void consolidateMatchResult(MatchResult matchResult){
		if(matchResult.getStatus() == MatchStatus.WIN){
			Contestant winner = matchResult.getWinner();
			int victoriesCount = result.increaseVictories(winner);
			
			if(victoriesCount >= rules.getWinsToChap()){
				result.setChampion(winner);
				end = true;
			}
		}
		
	}
	
	public TournamentRules getRules() {
		return rules;
	}

	public void setRules(TournamentRules rules) {
		this.rules = rules;
	}
	
	public void endMatchHandle(MatchResult result) {
		lastMatchResult = result;
		consolidateMatchResult(result);
	}
	
	
	@Override
	public void nextScreen() {
		
		switch (actual) {
		case INITIALIZE:
			init();
			actual = BattleFlow.SET_CONTESTANTS;
			break;
		case SET_CONTESTANTS:
			actual = BattleFlow.PLAY_THE_MATCH;
			break;
		case PLAY_THE_MATCH:
			actual = BattleFlow.MATCH_RESULT;
			break;
		case MATCH_RESULT:
			runAnotherMatch();
			return;
		case TOURNAMENT_RESULT:
			actual = BattleFlow.MAIN_MENU;
			break;
		default:
			break;
		}
		getScreen(actual);
	}
	
	abstract public void getScreen(BattleFlow actual);
	
	public void quit(){
		getScreen(BattleFlow.MAIN_MENU);
	}


}
