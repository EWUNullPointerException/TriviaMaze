package com.npe.triviamaze;

public class Game
{
    private Maze maze;
    private Player player;

    public Game()
    {
        maze = new Maze();
        player = new Player(maze.getStart());
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
