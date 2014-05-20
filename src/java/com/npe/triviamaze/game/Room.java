package com.npe.triviamaze.game;


public class Room
{
    private Door up;
    private Door right;
    private Door down;
    private Door left;
    
    Room()
    {
        //all doors do not exist
    }
    
    Room(Room up, Room right, Room down, Room left)
    {
        this.up = (up == null) ? new Door() : up.getDown();
        this.right = (right == null) ? new Door() : right.getLeft();
        this.down = (down == null) ? new Door() : down.getUp();
        this.left = (left == null) ? new Door() : left.getRight();
    }
    
    protected Door getUp()
    {
        return up;
    }
    
    protected Door getRight()
    {
        return right;
    }
    
    protected Door getDown()
    {
        return down;
    }
    
    protected Door getLeft()
    {
        return left;
    }

    protected Door getDoor(Direction direction)
    {
        if (direction == Direction.Up) return up;
        if (direction == Direction.Down) return down;
        if (direction == Direction.Left) return left;
        else return right;
    }

    public boolean isDoorLocked(Direction direction)
    {
        if (direction == Direction.Up) return up == null ? false : up.isLocked();
        if (direction == Direction.Down) return down == null ? false : down.isLocked();
        if (direction == Direction.Left) return left == null ? false : left.isLocked();
        else return right == null ? false : right.isLocked();
    }
    
    public boolean isDoorTraversable(Direction direction)
    {
        if (direction == Direction.Up) return up == null ? false : !up.isLocked();
        if (direction == Direction.Down) return down == null ? false : !down.isLocked();
        if (direction == Direction.Left) return left == null ? false : !left.isLocked();
        else return right == null ? false : !right.isLocked();
    }
}
