package ecs.common.match;

import java.util.ArrayList;
import java.util.List;

import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.component.Score;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;

import ecs.common.match.MatchResult.MatchStatus;

/**
 * Listen the game execution and reports to the Match Controller. 
 * 
 */
@Wire
public class MatchSystem extends EntitySystem{

	private MatchResultListener matchListener;
	
	private List<ContestantCampaign> contestantScores = new ArrayList<>();
	
	private ComponentMapper<Score> cmScore;
	
	private ComponentMapper<Contestant> cmContestant;

	private List<ContestantCampaign> scores;
	
	public MatchSystem(MatchResultListener matchListener){
		super(Aspect.getAspectForAll(Contestant.class, Score.class ));
		this.matchListener = matchListener;
	}


	@Subscribe
	public void handle(GameOverEvent gameOverEvent){

			
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> contestants) {
		//the match en
		if(endMatchCriteria(contestants)){
			matchListener.endMatchHandle(getResult(contestants));
		}
		
	}


	private boolean endMatchCriteria(ImmutableBag<Entity> contestants) {
		return (contestants.size() < 2);
	}


	private MatchResult getResult(ImmutableBag<Entity> contestants) {
		MatchStatus status = null;
		Contestant winner = null;
		
		if(contestants.size() == 0){
			status = MatchStatus.DRAW;			
		}else if(contestants.size() == 1) {
			status = MatchStatus.WIN;
			Entity winnerEntity = contestants.get(0);
			saveScore(winnerEntity);
			winner = cmContestant.get(winnerEntity);
		}
		
		MatchResult result = new MatchResult();
		result.setScores(scores);
		result.setStatus(status);
		result.setWinner(winner);
		
		return result;
	}
	
	
	protected void saveScore(Entity entity){
		Score score = cmScore.get(entity);		
		Contestant contestant = cmContestant.get(entity);
		contestantScores.add(
				new ContestantCampaign(contestant, score.getScore()));
	}
}
