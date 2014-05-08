package com.npe.triviamaze;

public class Maze
{
    private Room[][] rooms;
    private Location goal;
    private Location start;

    public Maze()
    {
        this(1, 1);
    }
    
    public Maze(int rows, int cols)
    {
        //rooms array is padded by empty cells
        rooms = new Room[rows+2][cols+2];
        goal = new Location(rows, cols);
        start = new Location(1, 1);
        for(int y = 1; y <= rows; y++)
        {
            for(int x = 1; x <= cols; x++)
            {
                rooms[y][x] = new Room(rooms[y-1][x], rooms[y][x+1], rooms[y-1][x], rooms[y][x-1]);
            }
        }
    }

    public Location getStart()
    {
        return new Location(start);
    }

    public Location getGoal()
    {
        return new Location(goal);
    }
}
