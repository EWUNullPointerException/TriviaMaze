package com.npe.triviamaze.game;

public class Maze
{
    private final Room[][] rooms;
    private Location goal;
    private Location start;
    public final int rows, cols;

    Maze()
    {
        this(1, 1);
    }

    Maze(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        // rooms array is padded by empty cells
        rooms = new Room[rows + 2][cols + 2];
        goal = new Location(rows, cols);
        start = new Location(1, 1);
        for(int y = 1; y <= rows; y++)
        {
            for(int x = 1; x <= cols; x++)
            {
                rooms[y][x] = new Room(rooms[y - 1][x], rooms[y][x + 1], rooms[y + 1][x],
                        rooms[y][x - 1]);
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

    protected boolean canMove(Location loc, Direction to)
    {
        if(to == Direction.Up && loc.row - 1 < 1)
            return false;
        if(to == Direction.Down && loc.row + 1 > rows)
            return false;
        if(to == Direction.Left && loc.col - 1 < 1)
            return false;
        if(to == Direction.Right && loc.col + 1 > rows)
            return false;

        return rooms[loc.row][loc.col].isDoorTraversable(to);
    }
    
    protected boolean directionOpen(Location loc, Direction to)
    {
        if(to == Direction.Up && loc.row - 1 < 1)
            return false;
        if(to == Direction.Down && loc.row + 1 > rows)
            return false;
        if(to == Direction.Left && loc.col - 1 < 1)
            return false;
        if(to == Direction.Right && loc.col + 1 > rows)
            return false;

        return rooms[loc.row][loc.col].isDoorOpen(to);
    }

    /**
     * 
     * @param loc
     * @param to
     * @return Returns null if there is no room in the direction requested
     */
    protected String[] getQuestion(Location loc, Direction to)
    {
        if(!canMove(loc, to))
            return null;
        return rooms[loc.row][loc.col].getQuestion(to);
    }

    protected void answerQuestion(Location loc, Direction to, String answer)
    {
        if(!canMove(loc, to))
            return;
        rooms[loc.row][loc.col].answerQuestion(to, answer);
    }

    public Room getRoom(int row, int col)
    {
        return rooms[row][col];
    }
}
