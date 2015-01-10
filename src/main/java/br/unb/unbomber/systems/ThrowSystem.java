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

import net.mostlyoriginal.api.event.common.EventManager;
import net.mostlyoriginal.api.event.common.Subscribe;
import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.Ballistic;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.Position;
import br.unb.unbomber.component.PowerUp;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.component.PowerUp.PowerType;
import br.unb.unbomber.component.Velocity;
import br.unb.unbomber.event.ActionCommandEvent;
import br.unb.unbomber.event.BallisticMovementCompleted;
import br.unb.unbomber.event.ActionCommandEvent.ActionType;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
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
	private ComponentMapper<PowerUp> cmPowerUp;
	
	UuidEntityManager uuidEm;

	/** used to dispatch events */
	EventManager em;


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
		Position position = cmPosition.get(source);
		Movable movable = cmMovable.get(source);
		PowerUp inventory = cmPowerUp.get(source);

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
		
		throwEntity(target, targetOrigin, movable.getFaceDirection());
				
	}

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
	 * @param origen
	 * @param displ
	 */
	private void throwEntity(Entity entity, Vector2D<Integer> orig, Vector2D<Integer> displ) {
		
		BallisticMovementCompleted completEvent = new BallisticMovementCompleted();
		completEvent.setTarget(entity.getUuid());
		Timer timer = new Timer(THROW_MOVEMENT_DURATION, completEvent);

		Ballistic ballisctic = new Ballistic(orig, displ);
		
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
		if(checkIfItKicked(targetPoisition)){
			throwEntity(entity, targetPoisition, ballistic.getDispl());
		}else{
			finishBallisticMovement(entity, targetPoisition);
		}
	}

	private void finishBallisticMovement(Entity entity, Vector2D<Integer> targetPoisition) {
		entity.edit()
			.remove(Ballistic.class)
			.add(new Position(targetPoisition));
	}

	private boolean checkIfItKicked(Vector2D<Integer> targetPoisition) {
		
		return false;
	}

}