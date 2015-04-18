package br.unb.unbomber.match;

import java.util.ArrayList;
import java.util.List;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Spec of a Stage. 
 * 
 * StageSpec is the spec of a match, loaded before a match is used
 * to initialize the entities, music and sprites as the stage theme 
 * specify.
 * 
 * It could be load from a file in Single Player Mode or by menu
 * configurations in battle mode.
 * 
 * @author grodrigues
 *
 */
public class StageSpec {
	
	/** Name of Stage */
	private String name;

	/** Music name */
	private String music;

	/** An ascii representation of stage,
	 * 	. ## for hard blocks
	 * 	. @@ for a Softblock */
	private String mapRepresentation;
	
	
	/** Entities of the stage. Like Characters, Monsters */
	private List<EntitySpec> entities;

	/** Initial Events */
	private List<Event> events;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}
	
	public String getMapRepresentation() {
		return mapRepresentation;
	}

	public void setMapRepresentation(String mapRepresentation) {
		this.mapRepresentation = mapRepresentation;
	}

	public List<EntitySpec> getEntities() {
		if(entities == null){
			this.entities = new ArrayList<EntitySpec>();
		}
		return entities;
	}

	public void setEntities(List<EntitySpec> entities) {
		this.entities = entities;
	}

	public List<Event> getEvents() {
		if(events == null){
			this.events = new ArrayList<Event>();
		}
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	

}



