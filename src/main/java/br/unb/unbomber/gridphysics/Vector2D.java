package br.unb.unbomber.gridphysics;

import java.util.ArrayList;
import java.util.List;

public class Vector2D<E extends Number>{
	
	private E x;
	
	private E y;
	
	public Vector2D(E x, E y){
		this.x = x;
		this.y = y;
	}

	public E getX() {
		return x;
	}

	public void setX(E x) {
		this.x = x;
	}

	public E getY() {
		return y;
	}

	public void setY(E y) {
		this.y = y;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector2D<E> add(Vector2D<E> vector) {
		if(vector.getX() instanceof Integer){
			return new Vector2D(vector.getX().intValue() + this.getX().intValue(),
					vector.getY().intValue() + this.getY().intValue());
		}
		
		if(vector.getX() instanceof Float){
			return new Vector2D( vector.getX().floatValue() + this.getX().floatValue(),
					vector.getY().floatValue() + this.getY().floatValue());
		}
		throw new IllegalArgumentException(vector.getClass().getName() + "not supported");

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector2D<E> mult(float scalar){
		if(this.getX() instanceof Float){
			return new Vector2D( this.getX().floatValue() * scalar,
					this.getY().floatValue() * scalar );
		}
		if(this.getX() instanceof Integer){
			return new Vector2D( this.getX().intValue() * scalar,
					this.getY().intValue() * scalar );
		}
		throw new IllegalArgumentException("not implemented for this type");
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector2D<E> mult(Vector2D<E> vector){
		if(vector.getX() instanceof Float){
			return new Vector2D(vector.getX().floatValue() * this.getX().floatValue(),
					vector.getY().floatValue() * this.getY().floatValue());
		}
		throw new IllegalArgumentException("not implemented for this type");
	}
	
	@SuppressWarnings("unchecked")
	public List<Vector2D<E>> components(){
		
		List<Vector2D<E>> components = new ArrayList<>();
		components.add(new Vector2D<E>(this.x, (E) getZero()));
		components.add(new Vector2D<E>((E) getZero(), this.y));
		
		return components;
	}
	
	
	public Vector2D<Float> toFloatVector(){
		return new Vector2D<Float>(this.x.floatValue(), this.y.floatValue());
	}
	
	/**
	 * Get a Generic Zero
	 * @return
	 */
	private Number getZero(){
		/** Is there a better way of get a generic 0? */
		if(this.x instanceof Integer){
			return 0;
		}else if(this.x instanceof Float){
			return 0f;
		}else{
			throw new IllegalStateException("trying to operate an unsuported number");
		}
	}
	
	public String toString(){
		return "("+ this.x + "," + this.y + ")";
	}
	
	/**
	 * Compare if same position. 
	 * Don't compare EntityId or Owner
	 */
	public boolean equals(Object o){
		if(o==null || ! (o instanceof Vector2D<?>)){
			return false;
		}else {
			Vector2D<?> c = (Vector2D<?>) o;
			if(c.x != this.x || 
					c.y != this.y){
				return false;
			}else{
				return true;				
			}
		}
	}
}