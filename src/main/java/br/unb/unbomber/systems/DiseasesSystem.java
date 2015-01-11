package br.unb.unbomber.systems;

import java.util.Random;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.unbomber.component.DiseaseComponent;
import br.unb.unbomber.component.DiseaseComponent.DiseaseType;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.event.AquiredDiseaseEvent;
import br.unb.unbomber.event.CollisionEvent;
import br.unb.unbomber.event.CreateDiseaseEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.misc.EntityBuilder2;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.systems.VoidEntitySystem;


@Wire
public class DiseasesSystem extends VoidEntitySystem {

	
	EventManager em;
	
	ComponentMapper<LifeType> cmLifeType;
	ComponentMapper<DiseaseComponent> cmDiseaseComponent;
	
	UuidEntityManager uuidManager;

	@Override
	protected void processSystem() {
		// TODO Auto-generated method stub
		
	}
	
	@Subscribe
	public void handle(CreateDiseaseEvent createDiseaseEvent) {
		createDisease(createDiseaseEvent.getPlacement());
	}

	/**
	 *  Checking if someone got any disease in the field.
	 */
	@Subscribe
	public void handle(CollisionEvent collisionEvent) {
		/* Checking if it was a collision char - disease */
		
		Entity source = uuidManager.getEntity(collisionEvent.getSourceUuid());
		LifeType sourceLifeType = cmLifeType.getSafe(source);
		
		Entity target = uuidManager.getEntity(collisionEvent.getTargetUuid());
		
		/* Getting the DiseaseComponent */
		DiseaseComponent diseaseComponent = cmDiseaseComponent.getSafe(target);
		
		if (sourceLifeType.getType() == Type.CHAR
				&& diseaseComponent!=null ) {
			
			DiseaseType disease = diseaseComponent.getDiseaseType();
			/* Creating an event according to the disease */
			AquiredDiseaseEvent aquiredDiseaseEvent = new AquiredDiseaseEvent();
			aquiredDiseaseEvent.setOwnerEntityId(collisionEvent.getSourceUuid());
			
			if (disease == DiseaseType.DIARRHEA) {
				aquiredDiseaseEvent.setDiseaseType(DiseaseType.DIARRHEA);
			} else if (disease == DiseaseType.CONSTIPATION) {
				aquiredDiseaseEvent.setDiseaseType(DiseaseType.CONSTIPATION);
			} else if (disease == DiseaseType.LOWPOWER) {
				aquiredDiseaseEvent.setDiseaseType(DiseaseType.LOWPOWER);
			} else if (disease == DiseaseType.RAPIDPACE) {
				aquiredDiseaseEvent.setDiseaseType(DiseaseType.RAPIDPACE);
			} else if (disease == DiseaseType.SLOWPACE) {
				aquiredDiseaseEvent.setDiseaseType(DiseaseType.SLOWPACE);
			}
			
			em.dispatch(aquiredDiseaseEvent);
			
			target.deleteFromWorld();
		}
	}
	
	
	@Subscribe
	public void handle(InAnExplosionEvent inAnExplosionEvent) {
		/* Checking if any disease in the field should be destroyed by explosion */
		Entity hit = uuidManager.getEntity(inAnExplosionEvent.getHitUuid());
		if(cmDiseaseComponent.getSafe(hit)!=null){
			/* Destroying the disease */
			hit.deleteFromWorld();
		}
	}
	

	private void createDisease(Position cellPlacement) {

		/* Creating ExplosionBarrierComponent */
		ExplosionBarrier explosionBarrier = new ExplosionBarrier();
		explosionBarrier.setType(ExplosionBarrierType.STOPPER);
		/* Creating DiseaseComponent */
		int randomNumber = getRandomNumber();
		DiseaseComponent diseaseComponent = new DiseaseComponent();
		if (randomNumber == 1) {
			diseaseComponent.setDiseaseType(DiseaseType.DIARRHEA);
		} else if (randomNumber == 2) {
			diseaseComponent.setDiseaseType(DiseaseType.CONSTIPATION);
		} else if (randomNumber == 3) {
			diseaseComponent.setDiseaseType(DiseaseType.LOWPOWER);
		} else if (randomNumber == 4) {
			diseaseComponent.setDiseaseType(DiseaseType.RAPIDPACE);
		} else if (randomNumber == 5) {
			diseaseComponent.setDiseaseType(DiseaseType.SLOWPACE);
		}

		Draw draw = new Draw("desease");
		
		/* Creating the entity */
		EntityBuilder2.create(world)	
			.with(cellPlacement)
			.with(explosionBarrier)
			.with(diseaseComponent)
			.with(draw)
			.build();
	}

	private int getRandomNumber() {
		Random random = new Random();
		int min = 1;
		int max = 5;
		return random.nextInt(max - min + 1) + min;
	}



}
