package br.unb.unbomber.misc;

import br.unb.gridphysics.Vector2D;
import br.unb.unbomber.component.BombDropper;
import br.unb.unbomber.component.Draw;
import br.unb.unbomber.component.ExplosionBarrier;
import br.unb.unbomber.component.ExplosionBarrier.ExplosionBarrierType;
import br.unb.unbomber.component.Movable;
import br.unb.unbomber.component.MovementBarrier;
import br.unb.unbomber.component.MovementBarrier.MovementBarrierType;
import br.unb.unbomber.component.Position;

import com.artemis.Component;
import com.artemis.World;

/**
* Build entities of the game.
* 
* Creates an Entity in entity manager with the specified components.
*  
 * @author grodrigues
 *
 */
public class EntityBuilder2 extends com.artemis.utils.EntityBuilder{
	
	/** Create the factory with create() */
	private EntityBuilder2(World world){
		super(world);
	}
	
	/** Create a factory for a new Entity.
	 *  The factory will take care of include in the entity for you
	 **/
	public static EntityBuilder2 create(World world){
		return new EntityBuilder2(world);
	}
	
	public EntityBuilder2 withPosition(int cellX, int cellY){
	
		Position cell = new Position();
		cell.setCellX(cellX);
		cell.setCellY(cellY);
		return includeInProduct(cell);		
	}
	
	public EntityBuilder2 withDropper(int permittedSimultaneousBombs, 
				int explosionRange,
				boolean canRemoteTrigger,
				boolean firstBombInfinite,
				boolean firstBombLandMine,
				boolean areBombsPassThrough,
				boolean areBombsHardPassThrough) {

		BombDropper bombDropper = new BombDropper();
		bombDropper.setPermittedSimultaneousBombs(permittedSimultaneousBombs);
		bombDropper.setExplosionRange(explosionRange);
		bombDropper.setCanRemoteTrigger(canRemoteTrigger);
		bombDropper.setFirstBombInfinite(firstBombInfinite);
		bombDropper.setFirstBombLandMine(firstBombLandMine);
		bombDropper.setAreBombsPassThrough(areBombsPassThrough);
		bombDropper.setAreBombsHardPassThrough(areBombsHardPassThrough);
		
		return includeInProduct(bombDropper);		

	}
	
	public EntityBuilder2 withDropper(int permittedSimultaneousBombs, 
			int explosionRange,
			boolean canRemoteTrigger) {
		return withDropper(permittedSimultaneousBombs, explosionRange, canRemoteTrigger,
				false,false,false,false);
	}
	

	public EntityBuilder2 withMovable(float speed){
		return withMovable(speed, 0.5f, 0.5f);
	}
	
	public EntityBuilder2 withMovable(float speed, float dx, float dy){
		
		Movable movable = new Movable();
		movable.setSpeed(speed);
		Vector2D<Float> displacement = new Vector2D<>(dx,dy);
		movable.setCellPosition(displacement);
		
		return includeInProduct(movable);
	}
	
	public EntityBuilder2 withMovementBarrier(MovementBarrierType type) {
		MovementBarrier barrier = new MovementBarrier(type);
		return includeInProduct(barrier);
	}
	
	public EntityBuilder2 withExplosionBarrier(ExplosionBarrierType type) {
		ExplosionBarrier barrier = new ExplosionBarrier(type);
		return includeInProduct(barrier);
	}
	
	public EntityBuilder2 withDraw(String type){
		return includeInProduct((new Draw(type)));
	}	
	
	public EntityBuilder2 includeInProduct(Component component){
		return (EntityBuilder2) with(component);
	}
	
	//your are welcome to create more ..
}