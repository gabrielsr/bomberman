package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.List;

import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;


public class GridSystem extends EntitySystem {

	
	private static final int MAX_GRID_SIZE = 30;
	
	private List<Entity>[][] gridMap;
	
	protected ComponentMapper<Position> mPosition;
	
	public GridSystem() {
        super(Aspect.getAspectForAll(Position.class));
    }

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		/** reset grid map each tick */
		gridMap = (List<Entity>[][]) new List[MAX_GRID_SIZE][MAX_GRID_SIZE];

		/** add a reference to each entity in the map */
		for(Entity e: entities){
			Vector2D<Integer> index = mPosition.get(e).getIndex();

			if(gridMap[index.getX()][index.getX()] == null ){
				gridMap[index.getX()][index.getX()] = new ArrayList<Entity>();	
			}
			
			gridMap[index.getX()][index.getX()].add(e);
		}
		
	}
	
	public List<Entity> getInPosition(Vector2D<Integer> index){
		return gridMap[index.getX()][index.getY()];
	}
	
}
