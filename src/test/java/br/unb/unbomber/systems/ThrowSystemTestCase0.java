

package br.unb.unbomber.systems;

import static junit.framework.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.Explosive;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.EntityManagerImpl;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.InAnExplosionEvent;
import br.unb.unbomber.event.MovementCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.MovementCommandEvent.MovementType;
import br.unb.unbomber.event.ExplosionStartedEvent;

public class ThrowSystemTestCase0 {
	
	EntityManager entityManager;
	ThrowSystem throwsystem;
	BombSystem bombsystem;
	MovimentSystem movimentsystem;
	CollisionSystem collisionsystem;
	@Before
	public void setUp() throws Exception {
		//init a new system for each test case
		EntityManagerImpl.init();
		entityManager = EntityManagerImpl.getInstance();
		throwsystem = new ThrowSystem();
		bombsystem = new BombSystem();
		movimentsystem = new MovimentSystem();
		collisionsystem = new CollisionSystem();
	}
	
	@Test
	public void trhowTest(){
		int ENTITY_ID_CHAR = 0;
		int ENTITY_ID_BOMB = 1;
		int x = 5;
		int y = 5;
		Entity anEntityChar = new Entity(ENTITY_ID_CHAR);
		Entity anEntityBomb = new Entity(ENTITY_ID_BOMB);
		
		
		PowerUp powerup = new PowerUp(PowerType.BOXINGGLOVEACQUIRED);
		
		CellPlacement charPlacement = new CellPlacement();
		charPlacement.setEntityId(ENTITY_ID_CHAR);
		charPlacement.setCellX(x);
		charPlacement.setCellY(y);
		
		CellPlacement bombPlacement = new CellPlacement();
		bombPlacement.setEntityId(ENTITY_ID_BOMB);
		bombPlacement.setCellX(x);
		bombPlacement.setCellY(y+3);
		
		Movable charMovable = new Movable();
		charMovable.setEntityId(ENTITY_ID_CHAR);
		charMovable.setSpeed(3);
		Explosion explosion = new Explosion();
		
		anEntityBomb.addComponent(explosion);
		anEntityBomb.addComponent(bombPlacement);
		
		anEntityChar.addComponent(powerup);
		anEntityChar.addComponent(charPlacement);
		anEntityChar.addComponent(charMovable);
		
		entityManager.addEntity(anEntityChar);
		entityManager.addEntity(anEntityBomb);
		
		final MovementCommandEvent movementCommand = new MovementCommandEvent(
				MovementType.MOVE_UP, charMovable.getEntityId());
		final ActionCommandEvent commandEvent = new ActionCommandEvent(ActionType.DROP_BOMB, ENTITY_ID_CHAR);
		
		entityManager.addEvent(movementCommand);
		
		movimentsystem.update();
		//collisionsystem.update();
		throwsystem.update();
		
		final CellPlacement newBombPlacement = (CellPlacement) entityManager
				.getComponent(CellPlacement.class, ENTITY_ID_BOMB);
		
		
		assertEquals(y+3,newBombPlacement.getCellY());// y +3 + 5
		
		
			
	}
}
