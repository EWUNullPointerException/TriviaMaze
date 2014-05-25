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

    public Player getPlayer()
    {
        return player;
    }

    public boolean beenWon()
    {
        return player.getLocation().equals(maze.getGoal());
    }

    public boolean canMove(Direction direction)
    {
        Location loc = player.getLocation();
        return maze.canMove(loc, direction);
    }
    
    public boolean directionOpen(Direction direction)
    {
        return maze.directionOpen(player.getLocation(), direction);
    }

    public boolean move(Direction direction)
    {
        Location loc = player.getLocation();
        boolean moved = maze.canMove(loc, direction);

        if(moved)
        {
            player.move(direction);
        }
        return moved;
    }

    public Maze getMaze()
    {
        return maze;
    }

    public String[] getQuestion(Direction direction)
    {
        return maze.getQuestion(player.getLocation(), direction);
    }

    public void answerQuestion(String answer, Direction direction)
    {
        maze.answerQuestion(player.getLocation(), direction, answer);
    }
}
