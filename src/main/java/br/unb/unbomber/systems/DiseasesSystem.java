package br.unb.unbomber.systems;

import java.util.List;
import java.util.Random;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.DiseaseComponent;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.CreateDiseaseEvent;
import br.unb.unbomber.event.InAnExplosionEvent;

public class DiseasesSystem extends BaseSystem {

	public DiseasesSystem() {
		super();
	}

	public DiseasesSystem(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void update() {

		/* Creating the diseases */
		List<Event> events = getEntityManager().getEvents(
				CreateDiseaseEvent.class);
		for (Event event : events) {
			/* Typecasting to use properly the event */
			CreateDiseaseEvent createDiseaseEvent = (CreateDiseaseEvent) event;
			/* Treating the event */
			createDisease(createDiseaseEvent.getPlacement());
			/* Deleting the event */
			getEntityManager().remove(event);
		}

		/* Checking if someone got any disease in the field */

		/* Checking if any disease in the field should be destroyed by explosion */
		events = getEntityManager().getEvents(InAnExplosionEvent.class);
		for (Event event : events) {
			/* Typecasting to use properly the event */
			InAnExplosionEvent inAnExplosionEvent = (InAnExplosionEvent) event;
			/* Checking if the entity is a disease */
			if (getEntityManager().getComponent(DiseaseComponent.class,
					inAnExplosionEvent.getIdHit()) != null) {
				/* Destroying the disease */
				getEntityManager().removeEntityById(
						inAnExplosionEvent.getIdHit());
				/* Deleting the event */
				getEntityManager().remove(event);
			}
		}

	}

	private void createDisease(CellPlacement cellPlacement) {

		/* Creating ExplosionBarrierComponent */
		ExplosionBarrier explosionBarrier = new ExplosionBarrier();
		explosionBarrier.setType(ExplosionBarrierType.STOPPER);
		/* Creating DiseaseComponent */
		int randomNumber = getRandomNumber();
		DiseaseComponent diseaseComponent = new DiseaseComponent();
		if (randomNumber == 1) {
			diseaseComponent.setDescription("CHANGEPOSITION");
		} else if (randomNumber == 2) {
			diseaseComponent.setDescription("DIARRHEA");
		} else if (randomNumber == 3) {
			diseaseComponent.setDescription("CONSTIPATION");
		} else if (randomNumber == 4) {
			diseaseComponent.setDescription("LOWPOWER");
		} else if (randomNumber == 5) {
			diseaseComponent.setDescription("RAPIDPACE");
		} else if (randomNumber == 6) {
			diseaseComponent.setDescription("SLOWPACE");
		}

		/* Creating the entity */
		Entity disease = getEntityManager().createEntity();
		disease.addComponent(cellPlacement);
		disease.addComponent(explosionBarrier);
		disease.addComponent(diseaseComponent);
		getEntityManager().update(disease);
	}

	private int getRandomNumber() {
		Random random = new Random();
		int min = 1;
		int max = 6;
		return random.nextInt(max - min + 1) + min;
	}

}
