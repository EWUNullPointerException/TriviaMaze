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

    public boolean canMoveLeft()
    {
        return canMove(Direction.Left);
    }

    public boolean canMoveRight()
    {
        return canMove(Direction.Right);
    }

    public boolean canMoveUp()
    {
        return canMove(Direction.Up);
    }

    public boolean canMoveDown()
    {
        return canMove(Direction.Down);
    }

    public boolean canMove(Direction direction)
    {
        Location loc = player.getLocation();
        return maze.canMove(loc, direction);
    }

    public boolean moveLeft()
    {
        return move(Direction.Left);
    }

    public boolean moveRight()
    {
        return move(Direction.Right);
    }

    public boolean moveUp()
    {
        return move(Direction.Up);
    }

    public boolean moveDown()
    {
        return move(Direction.Down);
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
}
