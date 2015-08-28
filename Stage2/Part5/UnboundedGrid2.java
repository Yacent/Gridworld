import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

import java.util.*;

public class UnboundedGrid2<E> extends AbstractGrid<E>
{
	private Object[][] occupantArray;
	private int length;

    /**
     * Constructs an empty unbounded grid.
     */
    public UnboundedGrid2() {
    	length = 16;
        occupantArray = new Object[length][length];
    }

    public int getNumRows() {
        return -1;
    }

    public int getNumCols() {
        return -1;
    }

    public boolean isValid(Location loc)
    {
        if (loc.getRow() >= 0 && loc.getCol() >= 0) {
        	return true;
        } else {
        	return false;
        }
    }

    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> locs = new ArrayList<Location>();
        
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, c);
                if (get(loc) != null) {
                    locs.add(loc);
                }
            }
        }
        
        return locs;
    }

    public E get(Location loc) {
        if (loc == null)
            throw new NullPointerException("loc == null");
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (loc.getRow() >= length || loc.getCol() >= length) {
        	return null;
        } else {
            return (E) occupantArray[loc.getRow()][loc.getCol()];	
        }
    }

    public E put(Location loc, E obj) {
    	if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (loc == null)
            throw new NullPointerException("loc == null");
        if (obj == null)
            throw new NullPointerException("obj == null");
        
        if (loc.getRow() >= length || loc.getCol() >= length) {
        	resizeGrid(loc);
        }
        
        // resize the Grid and then put the obj to the loc
        E oldOccupant = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }

    public E remove(Location loc) {
    	if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (loc == null)
            throw new NullPointerException("loc == null");
        
        if (loc.getRow() >= length || loc.getCol() >= length) {
        	return null;
        }
        E r = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return r;
    }
    
    /**
     *  When a call is made to the put method with a row or column index
     *  that is outside the current array bounds,
     *  double both array bounds until they are large enough.
     *  
     *  construct a new square array with those bounds, 
     *  and place the existing occupants into the new array.*/
    private void resizeGrid(Location loc) {
    	int newLength = length;
    	System.out.println("asdasdas : " + newLength);
    	while (loc.getRow() >= newLength || loc.getCol() >= newLength) {
    		newLength *= 2;
    	}
    	
    	Object[][] newArray = new Object[newLength][newLength];
    	
    	for (int r = 0; r < length; r++) {
    		for (int c = 0; c < length; c++) {
    			newArray[r][c] = occupantArray[r][c];
    		}
    	}
    	
    	System.out.println("seawdewasdas :");
    	
    	/**
    	 * init the length and the array to new.
    	 * double the size and length
    	 */
    	length = newLength;
    	occupantArray = newArray;
    	
    }
}
