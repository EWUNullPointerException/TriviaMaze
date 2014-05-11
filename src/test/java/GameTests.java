

import static org.junit.Assert.*;

import org.junit.Test;

import com.npe.triviamaze.game.Game;

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
}
