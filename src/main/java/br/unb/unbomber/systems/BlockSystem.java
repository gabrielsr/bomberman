package br.unb.unbomber.systems;

import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.event.InAnExplosionEvent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;

@Wire
public class BlockSystem extends VoidEntitySystem {

	ComponentMapper<ExplosionBarrier> cmExplosionBarrier;
	
	UuidEntityManager uuidManager;
	
	@Subscribe
	public void handle(InAnExplosionEvent event) {
		
		Entity target = uuidManager.getEntity(event.getHitUuid());
		
		ExplosionBarrier barrier = cmExplosionBarrier.getSafe(target);
		
		if(barrier != null 
				&& barrier.getType() == ExplosionBarrierType.STOPPER){
			target.deleteFromWorld();
		}
		
	}


	@Override
	protected void processSystem() {
		// TODO Auto-generated method stub
		
	}
}




