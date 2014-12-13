package br.unb.unbomber.gridphysics;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;

public class MovementCalc {

	
	/**
	 * A displacement is the shortest distance from the initial to the final position of a point P.
	 * 
	 * @param direction
	 * @param speed
	 * @return displacement Vector
	 */
	public static Vector2D<Float> displacement(Direction direction, Float speed){
		Vector2D<Float> displacement = new Vector2D<Float>(0.0f, 0.0f);
		displacement.setX(direction.getX() * speed);
		displacement.setY(direction.getY() * speed);
		return displacement;
	}
	
	
	/**
	 * Calculate the final Cell after a sum of displacements.
	 * 
	 * @param originCell
	 * @param displacements
	 * @return final cell
	 */
	@SafeVarargs
	public static CellPlacement finalCellPlacement(CellPlacement originCell, Vector2D<Float>... displacements){
		
		Vector2D<Float> totalDisplacement = new Vector2D<>(0.0f, 0.0f);
		
		for(Vector2D<Float> displacement:displacements){
			totalDisplacement = totalDisplacement.add(displacement);			
		}
		
		int deltaCellx = (int) Math.floor(totalDisplacement.getX());
		int deltaCelly = (int) Math.floor(totalDisplacement.getY());
		
		CellPlacement destination = new CellPlacement();
		destination.setCellX(originCell.getCellX() + deltaCellx);
		destination.setCellY(originCell.getCellY() + deltaCelly);
		
		return destination;
	}
	
	/**
	 * Rebase a vectors from a origin to another
	 * 
	 * new relative position = OriginalPosition + ( oldOrigin - newOrigin)
	 * 
	 * @param oldOrigin
	 * @param newOrigin
	 * @param displacement
	 * @return
	 */
	public static Vector2D<Float> rebase(CellPlacement refA, CellPlacement refB, Vector2D<Float> displacement){
		
		Vector2D<Float> oldOrigin = refA.centerPosition();
		Vector2D<Float> newOrigin = refB.centerPosition();
		
		//diff1 := ( oldOrigin + (newOrigin*-1))
		Vector2D<Float> difference1 = oldOrigin.add(newOrigin.mult(-1.0f));
		//diff2 := ( OriginalPosition + (diff1*-1))			
		Vector2D<Float> difference2 = displacement
				.add(difference1);
		
		return difference2;
	}
	
	public Vector2D<Float> distance(CellPlacement a, CellPlacement b){
		return new Vector2D<Float>((float)b.getCellX() - a.getCellX(), (float) b.getCellY() - a.getCellY());
	}
	
	
	public static boolean isCellChange(Vector2D<Float> displacement){
		return isChangingCellInX(displacement) || isChangingCellInY(displacement) ;
	}
	
	public static boolean isChangingCellInX(Vector2D<Float> displacement){
		return displacement.getX() > 0.5f;
	}
	
	public static boolean isChangingCellInY(Vector2D<Float> displacement){
		return displacement.getY() > 0.5f;
	}
}
