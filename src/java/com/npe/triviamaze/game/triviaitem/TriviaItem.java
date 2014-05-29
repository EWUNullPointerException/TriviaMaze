package com.npe.triviamaze.game.triviaitem;

public class TriviaItem //This was abstract
{
    String correctAnswer;
    String question;
    String[] answers;
    
    //Added this TriviaItem constructor
    public TriviaItem(String[] ara)
    {
        this.question = ara[0];
        //Short answer
        if(ara.length == 2)
        {
            answers = new String[]{""};//Without something this throws a null pointer?
        }
        //True/False
        else if(ara.length == 4)
        {
        answers = new String[]{"1. True", "2. False"};
        }
        //Multiple Choice
        else
        {
            answers = new String[]{"1. " + ara[1], "2. " + ara[2], "3. " + ara[3], "4. " + ara[4]};
        }
        this.correctAnswer = ara[ara.length-1].toLowerCase();//ensure lower case
    }
    

    public String[] getQuestion()
    {
        String[] tmp = new String[answers.length + 1];
        tmp[0] = question;
        for(int i = 0; i < answers.length; i++)
            tmp[i + 1] = answers[i];
        return tmp;
    }

    public boolean answerQuestion(String s)
    {
        return s.toLowerCase().equals(correctAnswer);
    }
}
