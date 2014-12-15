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
	
	/**
	 * The result verctor will have 1 for a crossed center L-R or D-U. -1 R-L or U-D. 
	 * If not crossed, it will have 0. 
	 * @param orig
	 * @param dest
	 * @return
	 */

	public static Vector2D<Integer> getCrossVector(Vector2D<Float> orig, Vector2D<Float> dest) {
		int crossX = 0;
		if(orig.getX().floatValue()< 0f && dest.getX().floatValue()>0){
			crossX=1;
		}else if(orig.getX().floatValue()> 0f && dest.getX().floatValue()<0){
			crossX=-1;
		}
		int crossY = 0;
		if(orig.getY().floatValue()< 0f && dest.getY().floatValue()>0){
			crossY=1;
		}else if(orig.getY().floatValue()> 0f && dest.getY().floatValue()<0){
			crossY=-1;
		}
		return new Vector2D<Integer>(crossX, crossY);
	}


	public static Vector2D<Integer> getCell(Vector2D<Integer> orig,
			Vector2D<Integer> displ) {
		return orig.add(displ);
	}

	
}
