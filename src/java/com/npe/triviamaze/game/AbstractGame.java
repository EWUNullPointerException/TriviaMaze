package com.npe.triviamaze.game;

public abstract class AbstractGame
{
    protected Player player;

    public abstract boolean isGameWon();
    public abstract boolean isGameOver();
    public abstract boolean canMove(Direction to);
    public abstract boolean move(Direction to);
    public abstract boolean directionOpen(Direction to);
    public abstract String[] getQuestion(Direction to);
    public abstract void answerQuestion(String answer, Direction to);
    
    public Player getPlayer()
    {
        return player;
    }
    public abstract Maze getMaze();
    public abstract int getScore();
}
