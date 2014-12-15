package br.unb.unbomber.gridphysics;

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
	
	public String toString(){
		return "("+ this.x + "," + this.y + ")";
	}
	
}