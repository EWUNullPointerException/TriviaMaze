

import static org.junit.Assert.*;

import org.junit.Test;

import com.npe.triviamaze.Game;

public class GameTests
{

    @Test
    public void testBeenWon()
    {
        Game game = new Game();
        assertTrue("Game of 1 room is instantly won", game.beenWon());
    }

}
