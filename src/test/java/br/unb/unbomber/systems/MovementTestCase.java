package br.unb.unbomber.systems;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import br.unb.gridphysics.Vector2D;

@RunWith(Parameterized.class)
public class MovementTestCase {
	
    private Vector2D<Float> originalDisplacement;
    private Vector2D<Float> position;
    private Vector2D<Float> resultDisp;

    public MovementTestCase(Vector2D<Float> originalDisplacement, Vector2D<Float> position, Vector2D<Float> resultDisp) {
        this.originalDisplacement = originalDisplacement;
        this.position = position;
        this.resultDisp = resultDisp;
    }

    @Test
    public void move() {
    	Vector2D<Float> centralized = MovementSystem.limiteDisplacementAlongAxe(this.originalDisplacement, this.position);
    	
        assertTrue(centralized.equals(this.resultDisp));
    }

    @Parameterized.Parameters
    public static Iterable data() {
        return Arrays.asList(
                new Object[][]{
                        {vector(0.125f, 0f), vector(0.5f, 0.5f), vector(0.125f, 0f) },
                        {vector(0.125f, 0f), vector(0.7f, 0.5f), vector(0.125f, 0f) },
                        {vector(0.125f, 0f), vector(0.5f, 0.625f), vector(0.0f, -0.125f) }
                }
        );
    }
    
    private static Vector2D<Float> vector(float x, float y){
    	return new Vector2D<Float>(x, y);
    }
}
