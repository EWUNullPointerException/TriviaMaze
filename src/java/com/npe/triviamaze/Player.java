package com.npe.triviamaze;

public class Player
{
    private Location playerLocation;

    public Player()
    {
        this.playerLocation = new Location(0, 0);
    }

    public Player(Location playerLocation)
    {
        this.playerLocation = playerLocation;
    }

    public Location getLocation()
    {
        return playerLocation;
    }
}
