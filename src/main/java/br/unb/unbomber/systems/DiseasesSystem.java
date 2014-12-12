package br.unb.unbomber.systems;

import java.util.List;
import java.util.Random;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.DiseaseComponent;
import br.unb.unbomber.component.DiseaseComponent.DiseaseType;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.component.LifeType;
import br.unb.unbomber.component.LifeType.Type;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.AquiredDiseaseEvent;
import br.unb.unbomber.event.CollisionEvent;
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
		events = getEntityManager().getEvents(CollisionEvent.class);
		for (Event event : events) {
			/* Typecasting to use properly the event */
			CollisionEvent collisionEvent = (CollisionEvent) event;
			/* Checking if it was a collision char - disease */
			LifeType sourceLifeType = (LifeType) getEntityManager()
					.getComponent(LifeType.class,
							collisionEvent.getSourceId());
			LifeType targetLifeType = (LifeType) getEntityManager()
					.getComponent(LifeType.class,
							collisionEvent.getTargetId());
			if (sourceLifeType.getType() == Type.CHAR
					&& targetLifeType.getType() == Type.DISEASE) {
				
				/* Getting the DiseaseComponent */
				DiseaseComponent diseaseComponent = (DiseaseComponent) getEntityManager()
						.getComponent(DiseaseComponent.class,
								collisionEvent.getTargetId());
				DiseaseType disease = diseaseComponent.getDiseaseType();
				/* Creating an event according to the disease */
				AquiredDiseaseEvent aquiredDiseaseEvent = new AquiredDiseaseEvent();
				aquiredDiseaseEvent.setOwnerId(collisionEvent.getSourceId());
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
				getEntityManager().addEvent(aquiredDiseaseEvent);
				getEntityManager().removeEntityById(collisionEvent.getSourceId());
			}
		}

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
		/* Creating LifeType component*/
		LifeType lifeType = new LifeType(Type.DISEASE);

		Draw draw = new Draw(Type.DISEASE.name());
		
		/* Creating the entity */
		Entity disease = getEntityManager().createEntity();
		disease.addComponent(cellPlacement);
		disease.addComponent(explosionBarrier);
		disease.addComponent(diseaseComponent);
		disease.addComponent(lifeType);
		disease.addComponent(draw);
		getEntityManager().update(disease);
	}

	private int getRandomNumber() {
		Random random = new Random();
		int min = 1;
		int max = 5;
		return random.nextInt(max - min + 1) + min;
	}

}
