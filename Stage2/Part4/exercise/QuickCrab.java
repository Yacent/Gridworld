/*
 * coptright@yuxin lin
 * override the getMoveLocations method
*/

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

public class QuickCrab extends CrabCritter {

    public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> locs = new ArrayList<Location>();
        int[] dirs =
            { Location.LEFT, Location.RIGHT };
        for (Location loc : getLocationsInTwoSides(dirs)) {
            if (getGrid().get(loc) == null) {
                locs.add(loc);
            }
        }
        if (locs.size() == 0) {
            return super.getMoveLocations();
        }
        return locs;
    }

    public ArrayList<Location> getLocationsInTwoSides(int[] directions)
    {
        ArrayList<Location> locTmp = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc) && getGrid().get(neighborLoc) == null) {
                Location neightborLoc2 = neighborLoc.getAdjacentLocation(getDirection() + d);
                if (gr.isValid(neightborLoc2)) {
                    locTmp.add(neightborLoc2);
                }
            }
        }
        return locTmp;
    }
}
