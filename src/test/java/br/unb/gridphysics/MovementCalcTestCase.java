package br.unb.gridphysics;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import br.unb.gridphysics.MovementCalc;
import br.unb.gridphysics.Vector2D;

public class MovementCalcTestCase {


	@Test
	public void crossVectorNoCrossTest() {
		Vector2D<Float> orig = new Vector2D<Float>(0.3f, 0f);
		Vector2D<Float> dest = new Vector2D<Float>(0.4f, 0f);
		
		Vector2D<Integer> cross = MovementCalc.getCrossVector(orig, dest);
		
		assertEquals(0, (int)cross.getX());
		assertEquals(0, (int)cross.getY());
	}


	@Test
	public void crossVectorUpDownCrossTest() {
		Vector2D<Float> orig = new Vector2D<Float>(0.0f, 0.4f);
		Vector2D<Float> dest = new Vector2D<Float>(0.0f, -0.3f);
		
		Vector2D<Integer> cross = MovementCalc.getCrossVector(orig, dest);
		
		assertEquals(0, (int)cross.getX());
		assertEquals(-1, (int)cross.getY());
	}
	@Test
	public void crossVectorRightLeftCrossTest() {
		Vector2D<Float> orig = new Vector2D<Float>(0.1f, 0.4f);
		Vector2D<Float> dest = new Vector2D<Float>(-0.3f, 0.3f);
		
		Vector2D<Integer> cross = MovementCalc.getCrossVector(orig, dest);
		
		assertEquals(-1, (int)cross.getX());
		assertEquals(0, (int)cross.getY());
	}
	@Test
	public void crossVectorDownUpCrossTest() {
		Vector2D<Float> orig = new Vector2D<Float>(0.0f, 0.4f);
		Vector2D<Float> dest = new Vector2D<Float>(0.0f, -0.3f);
		
		Vector2D<Integer> cross = MovementCalc.getCrossVector(orig, dest);
		
		assertEquals(0, (int)cross.getX());
		assertEquals(-1, (int)cross.getY());
	}
	
	@Test
	public void getCellAheadPositionTest() {
		Vector2D<Integer> orig = new Vector2D<>(3, 4);
		Vector2D<Integer> displ = new Vector2D<>(-1, 0);		
		
		Vector2D<Integer> cellPosition = MovementCalc.getCell(orig, displ);
		
		assertEquals(2, (int)cellPosition.getX());
		assertEquals(4, (int)cellPosition.getY());

	}

}
