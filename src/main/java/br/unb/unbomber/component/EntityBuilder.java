package br.unb.unbomber.component;

import br.unb.unbomber.core.Component;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.gridphysics.Vector2D;

/**
* Build entities of the game.
* 
* Creates an Entity in entity manager with the specified components.
*  
 * @author grodrigues
 *
 */
public class EntityBuilder {
	
	protected final Entity product;
	
	protected final EntityManager em;
	
	/** Create the factory with create() */
	private EntityBuilder(EntityManager entityManager, Entity newProduct){
		this.em = entityManager;
		this.product = newProduct;
	}
	
	/** Create a factory for a existing entity.
	 *  Take care not to construct a existing component **/
	public static EntityBuilder create(EntityManager entityManager, Entity newProduct){
		return new EntityBuilder(entityManager, newProduct);
	}
	
	/** Create a factory for a new Entity.
	 *  The factory will take care of include in the entity for you
	 **/
	public static EntityBuilder create(EntityManager entityManager){
		Entity beingBuild = entityManager.createEntity();
		return new EntityBuilder(entityManager, beingBuild);
	}
	
	public EntityBuilder include(Component component){
		product.addComponent(component);
		return this;
	}
	
	public EntityBuilder withPosition(int cellX, int cellY){
		
		CellPlacement cell = new CellPlacement();
		cell.setCellX(cellX);
		cell.setCellY(cellY);
		return include(cell);		
	}
	
	public EntityBuilder withDropper(int permittedSimultaneousBombs, 
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
		
		return include(bombDropper);		

	}
	
	public EntityBuilder withDropper(int permittedSimultaneousBombs, 
			int explosionRange,
			boolean canRemoteTrigger) {
		return withDropper(permittedSimultaneousBombs, explosionRange, canRemoteTrigger,
				false,false,false,false);
	}
	

	public EntityBuilder withMovable(float speed){
		return withMovable(speed, 0, 0);
	}
	
	public EntityBuilder withMovable(float speed, float dx, float dy){
		
		Movable movable = new Movable();
		movable.setSpeed(speed);
		Vector2D<Float> displacement = new Vector2D<>(dx,dy);
		movable.setCellDisplacement(displacement);
		
		return include(movable);
	}
	
	public EntityBuilder withDraw(String type){
		return include((new Draw(type)));
	}	
	
	//your are welcome to create more ..
	
	
	
	public Entity build(){
		em.update(product);
		return product;
	}
}