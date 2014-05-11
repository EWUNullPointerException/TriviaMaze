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
}
