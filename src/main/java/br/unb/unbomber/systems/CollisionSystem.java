package br.unb.unbomber.systems;

import java.util.List;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.MovedEntityEvent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;

/**
 * System that check movements an identify collisions.
 * @author grodrigues
 *
 */

@Wire
public class CollisionSystem extends VoidEntitySystem {

	ComponentMapper<Movable> cmMovable;
	
	GridSystem gridSystem;
	
	UuidEntityManager uuidManager;
	
	EventManager em;

	@Subscribe
	public void handle(MovedEntityEvent movedEntityEvent) {
		
		Vector2D<Integer> dest = movedEntityEvent.getDestinationCell();
		
		List<Entity> entitiesInDest = gridSystem.getInPosition(dest);
		
		for(Entity entity: entitiesInDest){
			//it´s the same entity
			if(entity.getUuid().equals(
					movedEntityEvent.getMovedEntityUUID())){
				continue;
			}
			
			CollisionEvent event = new CollisionEvent(
					movedEntityEvent.getMovedEntityUUID(), entity.getUuid());
			em.dispatch(event);
		}
	}


	@Override
	protected void processSystem() {

	}
}
