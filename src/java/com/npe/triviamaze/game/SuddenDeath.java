package com.npe.triviamaze.game;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;

import com.npe.triviamaze.database.Database;
import com.npe.triviamaze.game.triviaitem.TriviaItem;

public class SuddenDeath extends AbstractGame
{
    private Deque<TriviaItem> trivia;
    private final Maze maze;
    private boolean failedQuestion;
    private int score;
    private TriviaItem curTrivia;

    public SuddenDeath(String[] databases)
    {
        player = new Player(new Location(1, 1));
        maze = new Maze();
        score = 0;

        String[][] questions;
        Database db = new Database();
        questions = db.getAllQuestions(databases);

        Collections.shuffle(Arrays.asList(questions));
        trivia = new ArrayDeque<TriviaItem>();

        for(int i = 0; i < questions.length; i++)
        {
            trivia.add(new TriviaItem(questions[i]));
        }
    }

    @Override
    public boolean isGameWon()
    {
        return trivia.isEmpty();
    }

    @Override
    public boolean canMove(Direction to)
    {
        return true;
    }

    @Override
    public boolean move(Direction to)
    {
        return true;
    }

    @Override
    public boolean isGameOver()
    {
        return failedQuestion;
    }

    @Override
    public boolean directionOpen(Direction to)
    {
        return false;
    }

    @Override
    public String[] getQuestion(Direction to)
    {
        curTrivia = trivia.pop();
        return curTrivia.getQuestion();
    }

    @Override
    public void answerQuestion(String answer, Direction to)
    {
        boolean correct = curTrivia.answerQuestion(answer);
        if(correct)
        {
            score++;
        }
        else
        {
            failedQuestion = true;
        }
    }

    @Override
    public Maze getMaze()
    {
        return maze;
    }

    public int getScore()
    {
        return score;
    }

}
