package com.npe.triviamaze.game;

import com.npe.triviamaze.game.triviaitem.TriviaItem;
import com.npe.triviamaze.game.triviaitem.TrueFalse;

public class Door
{
    private TriviaItem trivia;
    private boolean locked;
    private boolean open;

    Door()
    {
        // TODO replace with real trivia and new constructor
        trivia = new TrueFalse();
    }

    public boolean isLocked()
    {
        return locked;
    }

    public boolean isOpen()
    {
        return open;
    }

    protected String[] getQuestion()
    {
        return trivia.getQuestion();
    }

    protected void answerQuestion(String answer)
    {
        if(trivia.answerQuestion(answer))
            open = true;
        else
            locked = true;
    }
}
