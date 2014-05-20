package com.npe.triviamaze.game;

import com.npe.triviamaze.game.triviaitem.TriviaItem;

public class Door
{
    private TriviaItem trivia;
    private boolean locked;
    private boolean open;

    Door()
    {
        
    }
    
    public boolean isLocked()
    {
        return locked;
    }
    
    public boolean isOpen()
    {
        return open;
    }
}
