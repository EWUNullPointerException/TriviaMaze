package com.npe.triviamaze.game.triviaitem;

import java.util.Random;

import com.npe.triviamaze.database.Database;

public class TrueFalse extends TriviaItem
{
    Database d = new Database();
    String[][] ara = d.getTrueFalse("CompSci", 19);
    public TrueFalse()
    {
        Random r = new Random();
        int i = r.nextInt(19);
        System.out.println(i);
        //TODO only for testing. remove once actual questions exist
//        this.question = "True?";
//        this.answers = new String[] {"1. True", "2. False"};
//        this.correctAnswer = "1";
        this.question = ara[i][0];
        this.answers = new String[] {"1. " +ara[i][1],"2. "+ ara[i][2]};
      this.correctAnswer = ara[i][3];
        
    }
    
}
