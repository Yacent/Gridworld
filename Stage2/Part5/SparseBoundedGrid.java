import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * reference the boundedgrid
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E> {
	private ArrayList<LinkedList<OccupantInCol>> occupantList = new ArrayList<LinkedList<OccupantInCol>>();

	private int row;
	private int col;
	
	public SparseBoundedGrid() {
		row = 15;
		col = 15;
		for (int i = 0; i < row; i++) {
			occupantList.add(new LinkedList<OccupantInCol>());
		}
	}
	
	/**
	 * init the rows and cols of the grid
	 * we will add some obj after that
	 */
	public SparseBoundedGrid(int rows, int cols) {
		if (rows <= 0) {
			throw new IllegalArgumentException("rows <= 0");
		}
		if (cols <= 0) {
			throw new IllegalArgumentException("cols <= 0");
		}
		row = rows;
		col = cols;
		for (int i = 0; i < rows; i++) {
			// an occupantIncol will point to the next auto
			occupantList.add(new LinkedList<OccupantInCol>());
		}
	}

	public int getNumCols() {
		return col;
	}

	public int getNumRows() {
		return row;
	}

	public ArrayList<Location> getOccupiedLocations() {
		ArrayList<Location> locs = new ArrayList<Location>();
		
		/** 
		 * in list, object<OccupantInCol> are linked to a list
		 * the OccupantInCol store the obj and its col
		 * so we should scan the row and get the obj's col
		 * to get the location
		 */ 
        for (int r = 0; r < getNumRows(); r++) {
        	LinkedList<OccupantInCol> list = occupantList.get(r);
        	if (list != null) {
        		for (OccupantInCol o : list) {
        			Location loc = new Location(r, o.getColNum());
        			locs.add(loc);
        		}
        	}
        }
        return locs;
	}

	public boolean isValid(Location loc) {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
	}
	
	public E put(Location loc, E obj) {
		if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        /**
         * if use get()loc, we should tell a lot of situation
         * such as the value is null or the list of the loc is null
         * so we use remove to remove thr obj then Add the object to the grid.
         */
        LinkedList<OccupantInCol> row = occupantList.get(loc.getRow());
        E oldOccupant = remove(loc);
        row.add(new OccupantInCol(obj, loc.getCol()));
        return oldOccupant;
	}
	
	public E get(Location loc) {
		if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
		
		/**
		 * tell the row not null or return null only
		 */
		if (occupantList.get(loc.getRow()) != null) {
	        for (OccupantInCol o : occupantList.get(loc.getRow())) {
	        	if (o.getColNum() == loc.getCol()) {
	        		return (E)o.getOccupant();
	        	}
	        }
		}
        return null;
	}

	public E remove(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        /**Remove the object from the grid
         * since one row is a list, there are many OccupantInCol
         * we should find out which col equals to loc.getCol*/
        E oldOccupant = get(loc);
        
        if (oldOccupant == null) {
        	return null;
        }
        E occupant = null;
        int index =-1;
        LinkedList<OccupantInCol> row = occupantList.get(loc.getRow());
        for (OccupantInCol o : row) {
        	if (o.getColNum() == loc.getCol()) {
        		occupant = (E)o.getOccupant();
        		index = row.indexOf(o);
        		break;
        	}
        }
        
        if (index != -1) {
        	row.remove(index);
        }
        
        return oldOccupant;
	}
	
}