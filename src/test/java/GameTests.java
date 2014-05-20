

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.npe.triviamaze.game.Direction;
import com.npe.triviamaze.game.Game;
import com.npe.triviamaze.game.Location;

public class GameTests
{

    @Test
    public void testBeenWon()
    {
        Game game = new Game();
        assertTrue("Game of 1 room is instantly won", game.beenWon());
    }
    
    @Test
    public void testCreateMazes()
    {
        Game game = new Game(1, 2);
        assertFalse("Game of size 1x2 is not instantly won", game.beenWon());
    }
    
    @Test
    public void testCanMove()
    {
        Game game = new Game(1, 1);
        assertFalse("Cannot move in game of size 1", game.canMove(Direction.Left));
        assertFalse("Cannot move in game of size 1", game.canMove(Direction.Right));
        assertFalse("Cannot move in game of size 1", game.canMove(Direction.Up));
        assertFalse("Cannot move in game of size 1", game.canMove(Direction.Down));
        
        game = new Game(2, 2);
        assertFalse("Cannot move left from start", game.canMove(Direction.Left));
        assertFalse("Cannot move up from start", game.canMove(Direction.Up));
        assertTrue("Can move right", game.canMove(Direction.Right));
        assertTrue("Can move down", game.canMove(Direction.Down));
    }
    
    @Test
    public void testMove()
    {
        Game game = new Game(2, 2);
        assertTrue("Can move right from start", game.moveRight());
        assertTrue("Actually moved right", game.getPlayer().getLocation().equals(new Location(1, 2)));
        assertFalse("Cannot move right again", game.canMoveRight());
        assertTrue("Can move down", game.moveDown());
        assertTrue("Have won game", game.beenWon());
    }
}
