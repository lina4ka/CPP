package com.company;

/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    public boolean equals (Object obj)
    {
        return obj instanceof Location && (((Location) obj).xCoord == this.xCoord && ((Location) obj).yCoord == this.yCoord);
    }

    public int hashCode()
    {
        return 3+((int)'X'*xCoord)*((int)'Y'*xCoord);
    }
}
