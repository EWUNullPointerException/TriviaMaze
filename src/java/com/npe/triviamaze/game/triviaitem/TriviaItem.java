package com.npe.triviamaze.game.triviaitem;

public abstract class TriviaItem
{
    String correctAnswer;
    String question;
    String[] answers;

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
