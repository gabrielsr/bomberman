package br.unb.unbomber.component;

import com.artemis.Component;

/**
 * Visual representation of an Entity.
 * 
 * It's a generic class that can be used in any renderization library
 * 
 * @author grodrigues
 *
 */
public class Draw extends Component {
	
	private String type;
	
	private Object drawable;
	
	public Draw(){
	}

	public Draw(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get a drawable object to be drawn using an appropriate library.
	 *  
	 * @return E drawable object
	 */
	public Object getDrawable() {
		return drawable;
	}

	/**
	 * Sets a drawable object.
	 *  
	 * @return E drawable object
	 */
	public void setDrawable(Object drawable) {
		this.drawable = drawable;
	}
	
}
