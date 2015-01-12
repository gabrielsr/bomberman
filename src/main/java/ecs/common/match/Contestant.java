package ecs.common.match;

import com.artemis.Component;

public class Contestant extends Component {

	private String contestantId;
	
	/** type of contestant (computer, person) */
	private String type;
	/** id of visual elements like sprites */
	private String visualTheme;
	
	public String getContestantId() {
		return contestantId;
	}

	public void setContestantId(String contestantId) {
		this.contestantId = contestantId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVisualTheme() {
		return visualTheme;
	}

	public void setVisualTheme(String visualTheme) {
		this.visualTheme = visualTheme;
	}


}
