package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;


@Wire
public class GridSystem extends EntitySystem {

	private Map<Vector2D<Integer>, List<Entity>> gridMap;
	
	protected ComponentMapper<Position> mPosition;
	
	//TODO control if it is dirt or no (entity was updated)
	private boolean dirty = true;
	
	public GridSystem() {
        super(Aspect.getAspectForAll(Position.class));
    }

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		if(dirty){
			refreshMap(entities);
		}
	}
	
	
	public List<Entity> getInPosition(int x, int y){
		return getInPosition(new Vector2D<Integer>(x, y));
	}
	
	public List<Entity> getInPosition(Vector2D<Integer> index){
		if(gridMap == null){
			throw new IllegalStateException("map not initialized");
		}

		List<Entity> result = gridMap.get(index);
		
		if(result==null){
			result = new ArrayList<Entity>();
		}
		return result;
	}
	
	
	protected void refreshMap(ImmutableBag<Entity> entities){
		/** reset grid map each tick */
		gridMap = new HashMap<>();

		/** add a reference to each entity in the map */
		for(Entity e: entities){
			Vector2D<Integer> index = mPosition.get(e).getIndex();

			if(gridMap.get(index) == null ){
				gridMap.put(index, new ArrayList<Entity>());	
			}
			
			gridMap.get(index).add(e);
		}
	}
	
}
