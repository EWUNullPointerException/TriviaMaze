package com.npe.triviamaze.game;

public enum Direction
{
    Up,
    Right,
    Down,
    Left;
    
    public static Direction opposite(Direction dir)
    {
        if (dir == Up) return Down;
        if (dir == Down) return Up;
        if (dir == Left) return Right;
        return Left;
    }
}
