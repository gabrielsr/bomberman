package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ExplosionStartedEvent;
import br.unb.unbomber.event.InAnExplosionEvent;

public class ExplosionSystem extends BaseSystem {

	/* List of already treated events */

	List<ExplosionStartedEvent> treatedEvents;

	public ExplosionSystem() {
		super();
	}

	public ExplosionSystem(EntityManager entityManager) {

		super(entityManager);

	}

	@Override
	public void start() {

		treatedEvents = new ArrayList<ExplosionStartedEvent>();
		super.start();

	}

	@Override
	public void update() {

		/* flag to see if an event was already treated */
		int flag;

		/* Get all ExplosionStartedEvents from EntityManager */

		List<Event> events = getEntityManager().getEvents(
				ExplosionStartedEvent.class);

		/* treat all non treated events */
		for (Event event : events) {

			/*
			 * check if this event was already treated
			 */

			flag = 0;
			for (int i = 0; i < treatedEvents.size(); i++) {

				if (treatedEvents.get(i).getEventId() == event.getEventId()) {

					flag = 1;
					break;

				}
			}

			/* if event was not treated */
			if (flag == 0) {

				/* typecasting to use the specific event */
				ExplosionStartedEvent explosionStartedEvent = (ExplosionStartedEvent) event;
				/* creating explosion */
				createExplosion(explosionStartedEvent.getInitialPosition(),
						explosionStartedEvent.getExplosionRange(), explosionStartedEvent.getOwnerId());
				/* put the treated event on the treatedEvents list */
				treatedEvents.add(explosionStartedEvent);
			}

			/* checks if someone entered an explosion */
			enteredExplosion();
			
			tickExplosions();

		}

	}

	/**
	 * Makes the time pass for the explosion and removes explosion if elapsed time is over
	 */
	private void tickExplosions() {
		List<Component> explosions = getEntityManager().getComponents(Explosion.class);
		for (Component component : explosions) {
			Explosion explosion = (Explosion) component;
			Timer timer = (Timer) getEntityManager().getComponent(Timer.class, explosion.getEntityId());
			timer.tick();
			if(timer.isOver()){
				getEntityManager().removeEntityById(timer.getEntityId());
			}
		}
	}

	public void createExplosion(CellPlacement expPlacement, int expRange, int ownerId) {

		Entity explosionEntity = new Entity();
		getEntityManager().addEntity(explosionEntity);

		Explosion exp = new Explosion();
		exp.setEntityId(explosionEntity.getEntityId());
		exp.setExplosionRange(expRange);
		exp.setOwnerId(ownerId);

		expPlacement.setEntityId(explosionEntity.getEntityId());

		Timer expTimer = new Timer(16, null);
		
		CellPlacement cellPlacement = new CellPlacement();
		cellPlacement.setCellX(expPlacement.getCellX());
		cellPlacement.setCellY(expPlacement.getCellY());
		cellPlacement.setEntityId(explosionEntity.getEntityId());

		explosionEntity.addComponent(exp);
		explosionEntity.addComponent(cellPlacement);
		explosionEntity.addComponent(expTimer);

		exp.setPropagationDirection(Direction.UP);
		propagateExplosion(exp, expPlacement, expRange);

		exp.setPropagationDirection(Direction.DOWN);
		propagateExplosion(exp, expPlacement, expRange);

		exp.setPropagationDirection(Direction.LEFT);
		propagateExplosion(exp, expPlacement, expRange);

		exp.setPropagationDirection(Direction.RIGHT);
		propagateExplosion(exp, expPlacement, expRange);

	}

	public void propagateExplosion(Explosion exp, CellPlacement cellPlacement,
			int range) {

		if (range != 0 && detectExplosionCollision(exp, cellPlacement)) {

			Entity explosionEntity = new Entity();
			getEntityManager().addEntity(explosionEntity);

			Explosion newExp = new Explosion();
			newExp.setEntityId(explosionEntity.getEntityId());
			newExp.setExplosionRange(range);
			newExp.setOwnerId(exp.getOwnerId());

			CellPlacement newExpPlacement = new CellPlacement();
			newExpPlacement.setEntityId(explosionEntity.getEntityId());

			if (exp.getPropagationDirection() == Direction.UP) {
				newExpPlacement.setCellX(cellPlacement.getCellX());
				newExpPlacement.setCellY(cellPlacement.getCellY() + 1);
			} else if (exp.getPropagationDirection() == Direction.DOWN) {
				newExpPlacement.setCellX(cellPlacement.getCellX());
				newExpPlacement.setCellY(cellPlacement.getCellY() - 1);
			} else if (exp.getPropagationDirection() == Direction.LEFT) {
				newExpPlacement.setCellX(cellPlacement.getCellX() - 1);
				newExpPlacement.setCellY(cellPlacement.getCellY());
			} else {
				newExpPlacement.setCellX(cellPlacement.getCellX() + 1);
				newExpPlacement.setCellY(cellPlacement.getCellY());
			}

			Timer expTimer = new Timer(16, null);

			explosionEntity.addComponent(exp);
			explosionEntity.addComponent(newExpPlacement);
			explosionEntity.addComponent(expTimer);

			--range;
			propagateExplosion(newExp, newExpPlacement, range);
		}
	}

	/* checks if someone entered an explosion */
	private void enteredExplosion() {

		List<Component> cellPlacements = getEntityManager().getComponents(
				CellPlacement.class);

		List<Component> explosions = getEntityManager().getComponents(
				Explosion.class);

		CellPlacement cellPlacement;

		Explosion explosion;

		CellPlacement explosionPlacement;

		for (Component componentExplosion : explosions) {

			for (Component componentCellPlacement : cellPlacements) {

				cellPlacement = (CellPlacement) componentCellPlacement;
				explosion = (Explosion) componentExplosion;
				explosionPlacement = (CellPlacement) getEntityManager()
						.getComponent(CellPlacement.class,
								explosion.getEntityId());
				if (explosionPlacement.getCellX() == cellPlacement.getCellX()
						&& explosionPlacement.getCellY() == cellPlacement
								.getCellY()) {

					InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
					inAnExplosionEvent.setOwnerId(explosion.getEntityId());
					inAnExplosionEvent.setIdHit(cellPlacement.getEntityId());
					getEntityManager().addEvent(inAnExplosionEvent);

				}
			}
		}

	}

	/*
	 * creates InAnExplosion event when an powerUp, character, monster, bomb or
	 * softBlock collides with the explosion. Returns true if the explosion
	 * should propagate and false otherwise
	 */
	public boolean processExplosionCollision(int entityId, Explosion explosion) {

		ExplosionBarrier explosionBarrier = (ExplosionBarrier) getEntityManager()
				.getComponent(ExplosionBarrier.class, entityId);

		if (explosionBarrier.getType() == ExplosionBarrierType.BLOCKER) {

			return false;

		} else if (explosionBarrier.getType() == ExplosionBarrierType.STOPPER) {

			InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
			inAnExplosionEvent.setIdHit(entityId);
			inAnExplosionEvent.setOwnerId(explosion.getOwnerId());
			getEntityManager().addEvent(inAnExplosionEvent);
			return false;

		} else {

			InAnExplosionEvent inAnExplosionEvent = new InAnExplosionEvent();
			inAnExplosionEvent.setIdHit(entityId);
			inAnExplosionEvent.setOwnerId(explosion.getEntityId());
			getEntityManager().addEvent(inAnExplosionEvent);
			return true;

		}
	}

	/* returns true if the explosion should propagate and false otherwise */
	public boolean detectExplosionCollision(Explosion explosion,
			CellPlacement explosionPlacement) {

		Direction direction = explosion.getPropagationDirection();
		CellPlacement nextSpace = new CellPlacement();
		if (direction == Direction.LEFT) {

			nextSpace.setCellY(explosionPlacement.getCellY());
			nextSpace.setCellX(explosionPlacement.getCellX() - 1);

		} else if (direction == Direction.RIGHT) {

			nextSpace.setCellY(explosionPlacement.getCellY());
			nextSpace.setCellX(explosionPlacement.getCellX() + 1);

		} else if (direction == Direction.UP) {

			nextSpace.setCellY(explosionPlacement.getCellY() + 1);
			nextSpace.setCellX(explosionPlacement.getCellX());

		} else {

			nextSpace.setCellY(explosionPlacement.getCellY() - 1);
			nextSpace.setCellX(explosionPlacement.getCellX());

		}

		List<Component> components = getEntityManager().getComponents(
				CellPlacement.class);

		CellPlacement cellPlacement;

		for (Component component : components) {

			cellPlacement = (CellPlacement) component;
			if (nextSpace.getCellX() == cellPlacement.getCellX()
					&& nextSpace.getCellY() == cellPlacement.getCellY()) {

				return processExplosionCollision(component.getEntityId(),
						explosion);

			}
		}

		return true;
	}

}
