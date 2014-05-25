package com.npe.triviamaze.game.triviaitem;

public class TrueFalse extends TriviaItem
{

    public TrueFalse()
    {
        //TODO only for testing. remove once actual questions exist
        this.question = "True?";
        this.answers = new String[] {"1. True", "2. False"};
        this.correctAnswer = "1";
    }
    
}
