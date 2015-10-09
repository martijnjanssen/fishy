package nl.github.martijn9612.fishy;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.newdawn.slick.GameContainer;

import junit.framework.TestCase;
import nl.github.martijn9612.fishy.models.NonPlayer;
import nl.github.martijn9612.fishy.models.Vector;
import nl.github.martijn9612.fishy.opponents.LinearOpponent;

public class LinearOpponentTest extends TestCase {
    private final GameContainer gc = mock(GameContainer.class);

    /**
     * Test case for objectLogic method.
     * Spawns the opponent at the left side of the screen.
     */
    @Test
    public void testLinearOpponentMoveRight() {
        Vector position = new Vector(0,0);
        Vector dimensions = new Vector(10,10);
        Vector velocity = new Vector(1,0);
        Vector acceleration = new Vector(0,0);
    	NonPlayer opp = new LinearOpponent(dimensions, position, velocity, acceleration, false);
        opp.objectLogic(gc, 0);
        assertEquals(1.0, opp.position.x, 0.1);
    }
    
    /**
     * Test case for objectLogic method.
     * Spawns the opponent at the right side of the screen.
     */
    @Test
    public void testLinearOpponentMoveLeft() {
        Vector position = new Vector(10,10);
        Vector dimensions = new Vector(10,10);
        Vector velocity = new Vector(-1,0);
        Vector acceleration = new Vector(0,0);
    	NonPlayer opp = new LinearOpponent(dimensions, position, velocity, acceleration, false);
        opp.objectLogic(gc, 0);
        assertEquals(9.0, opp.position.x, 0.1);
    }
}
