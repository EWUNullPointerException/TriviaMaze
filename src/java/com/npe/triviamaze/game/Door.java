package com.npe.triviamaze.game;

import java.util.Stack;

import com.npe.triviamaze.game.triviaitem.TriviaItem;

public class Door
{
    private TriviaItem trivia;
    private boolean locked;
    private boolean open;

    Door(Stack<TriviaItem> questionStack)
    {
        trivia = questionStack.pop(); 
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
