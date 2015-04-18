/**
 * @file TrowSystem.java 
 * @brief Este modulo trata dos Movimentos Relacionados ao Arremeco de bombas de jogo bomberman, criado pelo grupo 6 da turma de programacao sistematica 2-2014 ministrada pela professora janaina
 *
 * @author Igor Chaves Sodre
 * @author Pedro Borges Pio
 * @author Kilmer Luiz Aleluia
 * @since 3/11/2014
 * @version 1.0.1
 */

package br.unb.unbomber.systems;


import java.util.List;
import java.util.UUID;

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Ballistic;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.component.PowerUpInventory;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.component.Velocity;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;
import br.unb.unbomber.event.BallisticMovementCompleted;
import br.unb.unbomber.event.TimeOverEvent;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.managers.UuidEntityManager;
import com.artemis.utils.ImmutableBag;

@Wire
public class ThrowSystem extends EntitySystem {

	private static final int TRHOW_DISTANCE = 3;
	private static final int THROW_MOVEMENT_DURATION = 12;
	
	/** will be injected */
	GridSystem gridSystem;

	/** used to access components */
	ComponentMapper<Position> cmPosition;

	ComponentMapper<Movable> cmMovable;
	
	ComponentMapper<Velocity> cmVelocity;

	ComponentMapper<MovementBarrier> barrierMapper;

	private ComponentMapper<Ballistic> cmBallistic;
	private ComponentMapper<PowerUpInventory> cmPowerUpInventory;
	
	UuidEntityManager uuidEm;
	
	/** Stores the direction of the character*/
	Direction faceDir;

	/** used to dispatch events */
	EventManager em;
	
	/** Stores the timer to let the bomb explode after thrown */
	Timer bombTimer;


	public ThrowSystem() {
		super(Aspect.getAspectForAll(Ballistic.class));
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(Entity entity : entities){
			
		}
		
	}
	
	/**
	 * Take an object to throw
	 * @param command
	 */
	@Subscribe
	public void handle(ActionCommandEvent command) {

		Entity source = uuidEm.getEntity(command.getEntityUuid());
		Position position = cmPosition.getSafe(source);
		Movable movable = cmMovable.getSafe(source);
		PowerUpInventory inventory = cmPowerUpInventory.getSafe(source);

		//validate if can throw
		if(!ActionType.THROW.equals(command.getType())
				|| inventory ==null || !inventory.hasType(PowerType.BOXINGGLOVEACQUIRED) 
				|| movable == null || position == null){
			return;
		}
		//try at the same cell

		Vector2D<Integer> targetOrigin = position.getIndex();

		Entity target = takeAt(source, targetOrigin);

		if(target == null){
			//nothing to take
			return;
		}
		
		faceDir = movable.getFaceDirection();
		bombTimer = target.getComponent(Timer.class);
		
		throwEntity(target, targetOrigin, movable.getFaceDirection());
				
	}

	/** */
	private Entity takeAt(Entity exclude, Vector2D<Integer> position) {
		for(Entity entity: gridSystem.getInPosition(position)){
			if(entity.getUuid().equals(exclude.getUuid())){
				continue;
			}else{
				return entity;
			}
		}
		return null;
	}
	
	/**
	 * Throw an entity taken
	 * 
	 * @param entity	 
	 * @param origen
	 * @param direction
	 */
	private void throwEntity(Entity entity, Vector2D<Integer> orig, Direction direction) {
		
		Vector2D<Integer> displ = (direction.asVector().mult(TRHOW_DISTANCE))
				.toInteger();
		throwEntity(entity, orig, displ);
	}
	
	/**
	 * Throw an entity taken
	 * 
	 * @param entity	 
	 * @param origin
	 * @param displ
	 */
	private void throwEntity(Entity entity, Vector2D<Integer> orig, Vector2D<Integer> displ) {
		
		BallisticMovementCompleted completEvent = new BallisticMovementCompleted();
		completEvent.setTarget(entity.getUuid());
		Timer timer = new Timer(THROW_MOVEMENT_DURATION, completEvent);

		Ballistic ballisctic = new Ballistic(orig, displ);
		
		/*EntityEdit edit = entity.edit();
		edit = edit.add(ballisctic);
		edit = edit.add(timer);
		edit.remove(Position.class);*/
		
		entity.edit()
			.add(ballisctic)
			.add(timer)
			.remove(Position.class);
	}

	/**
	 * Take an object to throw
	 * @param command
	 */
	@Subscribe
	public void handle(BallisticMovementCompleted event) {
		Entity entity = uuidEm.getEntity(event.getTarget());
		Ballistic ballistic = cmBallistic.get(entity);
	
		
		Vector2D<Integer> targetPoisition = ballistic.getOrig().add(ballistic.getDispl());
		
		//re throw entity
		while(checkIfItKicked(targetPoisition)){
			if(faceDir == Direction.UP){
				targetPoisition.setY(targetPoisition.getY()+1);
			}
			else if (faceDir == Direction.DOWN){
				targetPoisition.setY(targetPoisition.getY()-1);
			}
			else if (faceDir == Direction.LEFT){
				targetPoisition.setX(targetPoisition.getX()-1);
			}
			else if (faceDir == Direction.RIGHT){
				targetPoisition.setX(targetPoisition.getX()+1);
			}
			//entity.edit().remove(Position.class);
			//targetPoisition.
			//throwEntity(entity, targetPoisition, ballistic.getDispl());
			//finishBallisticMovement(entity, targetPoisition);
		}
		
		finishBallisticMovement(entity, targetPoisition);

	}
	
	
	/** 
	 * Finishes the throwing movement
	 * 
	 * @param entity 
	 * @param targetPoisition
	 * */
	private void finishBallisticMovement(Entity entity, Vector2D<Integer> targetPoisition) {
		
		
		//TimeOverEvent<UUID> triggeredBombEvent
		//= new TimeOverEvent<UUID>("BOMB_TRIGGERED", entity.getUuid()); //got from BombSystem
		// create a new timer component to change events
		//Timer bombTimer = new Timer(90, triggeredBombEvent);
		

		entity.edit()
			.remove(Ballistic.class)
			.remove(Timer.class)
			.add(new Position(targetPoisition))
			.add(bombTimer); //Although it is added here, it is not called a second time - so it doesn't explode...
	}

	/**
	 * Checks if the bomb was thrown in a cell with another entity in it already.
	 * 
	 * @param targetPosition
	 * */
	private boolean checkIfItKicked(Vector2D<Integer> targetPosition) {

		List<Entity> componnetsOfBlock = gridSystem.getInPosition(targetPosition);
		if (componnetsOfBlock.size() == 0) {
			return false;
		} else {
			return true;
		}
		
	}

}