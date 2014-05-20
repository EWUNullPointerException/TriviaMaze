package com.npe.triviamaze.game;

public class Game
{
    private final Maze maze;
    private final Player player;

    public Game()
    {
        this(1, 1);
    }
    
    public Game(int rows, int cols)
    {
        maze = new Maze(rows, cols);
        player = new Player(maze.getStart());
    }

    public boolean beenWon()
    {
        return player.getLocation().equals(maze.getGoal());
    }
}
