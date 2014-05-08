package com.npe.triviamaze;

public class Location
{
    int row, col;
    
    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(other == null || !(other instanceof Location))
        {
            return false;
        }
        
        Location tmp = (Location) other;
        return (this.row == tmp.row && this.col == tmp.col);
    }
}
