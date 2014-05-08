package com.npe.triviamaze;

public class Maze
{
    //2d array or 1d array or some type of list?
    private Room[][] rooms;
    private Location goal;
    
    public Maze() 
    {
        rooms = new Room[1][1];
        rooms[0][0] = new Room();
        goal = new Location(0, 0);
    }
    
    public Room getStart()
    {
        return rooms[0][0];
    }
    
    public Location getGoal()
    {
        return goal;
    }
}
