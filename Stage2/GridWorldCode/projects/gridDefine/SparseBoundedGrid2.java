import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

import java.util.*;

public class SparseBoundedGrid2<E> extends AbstractGrid<E> {
    private Map<Location, E> occupantMap;
    
    private int row;
    private int col;

    /**
     * Constructs an empty unbounded grid.
     */
    public SparseBoundedGrid2(int rows, int cols) {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("cols <= 0");
        }
        occupantMap = new HashMap<Location, E>();
        row = rows;
        col = cols;
    }

    public int getNumRows() {
        return row;
    }

    public int getNumCols() {
        return col;
    }

    public boolean isValid(Location loc) {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }
    
    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> a = new ArrayList<Location>();
        for (Location loc : occupantMap.keySet())
            a.add(loc);
        return a;
    }
    
    public E get(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }

    public E put(Location loc, E obj) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.put(loc, obj);
    }

    public E remove(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.remove(loc);
    }
}
