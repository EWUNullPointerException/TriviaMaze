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
    
    private Door getUp()
    {
        return up;
    }
    
    private Door getRight()
    {
        return right;
    }
    
    private Door getDown()
    {
        return down;
    }
    
    private Door getLeft()
    {
        return left;
    }
}
