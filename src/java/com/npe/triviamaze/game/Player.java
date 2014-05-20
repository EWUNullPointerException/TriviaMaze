package com.npe.triviamaze.game;

public class Player
{
    private Location playerLocation;

    Player()
    {
        this.playerLocation = new Location(0, 0);
    }

    Player(Location playerLocation)
    {
        this.playerLocation = new Location(playerLocation);
    }

    public Location getLocation()
    {
        return new Location(playerLocation);
    }

    void move(Direction direction)
    {
        if (direction == Direction.Left) playerLocation.col -= 1;
        else if (direction == Direction.Right) playerLocation.col += 1;
        else if (direction == Direction.Up) playerLocation.row -= 1;
        else playerLocation.row += 1;
        
    }
}
