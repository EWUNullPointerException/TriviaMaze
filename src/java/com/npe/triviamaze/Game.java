package com.npe.triviamaze;

public class Game
{
    private Maze maze;
    private Player player;

    public Game()
    {
        maze = new Maze();
        player = new Player(new Location(0, 0));
    }

    public boolean beenWon()
    {
        return player.getLocation().equals(maze.getGoal());
    }
}
