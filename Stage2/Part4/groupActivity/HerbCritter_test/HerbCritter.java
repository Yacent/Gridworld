/**
 * copyright@ GA_169
 * 
 * @author Zhang Ming
 * @author Zhang Yuyi
 * @author Zhou Qixian
 * @author Lin Yuxin
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A HerbCritter looks at a limited set of neighbors when it eat and moves
 * It can move move immediatly to another rock when colddown is 0 and near the rock
 * It will die when the life becomes 0
 * Eat the flower will increase the life, but move without eat anything will decrease life
 */
public class HerbCritter extends Critter {

    /**
     * Life initial to 12, life increase 1 when eat a flower, others decrease 1
     * meet_rock to judge whether it is near the rock after called the getActors method
     * Arraylist<Location> rockLoc to record the location of the rock in the grid
     * The colddown time is 10 after move to another rock each time
     */
    private int life = 12;
    private boolean meet_rock;
    private ArrayList<Location> rockLoc;
    private static final double DARKENING_FACTOR = 0.2;
    private static final double BRIGHT_FACTOR = 0.4;
    private int time = 0;

    /**
     * A default constructor of HerbCritter 
     * called the constructor of Critter
     * called the getRockLocation to store the location of rock in the grid
     */
    public HerbCritter() {
        super();
    }
    
    /**
     * A constructor of HerbCritter
     * accept a parameter to change the color of HerbCritter
     */
    public HerbCritter(Color a) {
        super();
        setColor(a);
    }

    /**
     * look for the rock in the grid
     * store the location in the Arratlist<Location> rockLoc
     */
    private void getRockLocation() {
    	if (rockLoc == null) {
    		ArrayList<Location> rockLoc1 = new ArrayList<Location>();
            ArrayList<Location> rockLoc2 = new ArrayList<Location>();
            rockLoc1 = getGrid().getOccupiedLocations();
            for (Location rock : rockLoc1) {
                if (getGrid().get(rock) instanceof Rock) {
                    rockLoc2.add(rock);
                }
            }
            rockLoc = rockLoc2;
    	}
    }

    /**
     * A crab gets the actors in the three locations immediately in front, to its
     * front-right and to its front-left
     * @return a list of actors that the locations are occupied by flower
     */
    public ArrayList<Actor> getActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs =
            { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null && a instanceof Flower)
                actors.add(a);
        }

        return actors;
    }

    /**
     * Finds the valid adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are neighbors of the current
     * location in the given directions
     */
    public ArrayList<Location> getLocationsInDirections(int[] directions) {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid<Actor> gr = getGrid();
        Location loc = getLocation();
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
        }
        return locs;
    }   

    /**
     * Processes the elements of actors
     * Postcondition: (1) The state of all actors in the grid other than this
     * critter and the elements of <code>actors</code> is unchanged. (2) The
     * location of this critter is unchanged.
     * @param actors the actors to be processe
     */
    public void processActors(ArrayList<Actor> actors) {
        int n = actors.size();
        if (n == 0) {
            darker();
            life--;
            System.out.println("life reduce: " + life);
            return;
        }
        for (Actor a : actors) {
            a.removeSelfFromGrid();
            life++;
            brighter();
            System.out.println("life increase: " + life);
        }
    }

    /**
     * Causes the color of this HerbCritter to darken.
     */
    private void darker() {
        Color c = getColor();
        int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

        setColor(new Color(red, green, blue));
    }

    /**
     * Causes the color of this HerbCritter to brighter.
     */
    private void brighter() {
        Color c = getColor();

        int red = (int) (c.getRed() * (BRIGHT_FACTOR + 1));
        int green = (int) (c.getGreen() * (BRIGHT_FACTOR + 1));
        int blue = (int) (c.getBlue() * (BRIGHT_FACTOR + 1));

        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        setColor(new Color(red, green, blue));
    }

    /**
     * Gets a list of possible locations for the next move or the location of the rock near itself.
     * These locations must be valid in the grid of this Herbcritter.
     * Postcondition: The state of all actors is unchanged.
     * @return a list of possible locations of rockLocation when actors.size != 0
     * @return called the getMoveLocation to get a list of possible location
     * for the next move of thsuper class 
     */
    public ArrayList<Location> getMoveLocations() {
        System.out.println("cold time: " + time);
        ArrayList<Actor> actors1 = getGrid().getNeighbors(getLocation());
        ArrayList<Actor> actors = new ArrayList<Actor>();
        ArrayList<Location> loc = new ArrayList<Location>();
        for (Actor a: actors1) {
            if ((a instanceof Rock)) {
                actors.add(a);
            }
        }
        
        if (actors.size() == 0 || time > 0) {
            meet_rock = false;
            return super.getMoveLocations();
        } else {
            for (Actor a : actors) {
                loc.add(a.getLocation());
            }
            meet_rock = true;
            return loc;
        }
    }
    

    /**
     * Selects the location for the next move.
     * to return the current location if locs has size 0.
     * @param locs the possible locations for the next move or the locs of the rock
     * @return the location that was selected for the next movement
     */
    public Location selectMoveLocation(ArrayList<Location> locs) {
        int n = locs.size();
        if (n == 0)
            return getLocation();
        if (meet_rock) {
        	getRockLocation();
            for (Location l : locs) {
                rockLoc.remove(l);
            }
            Location newLoc = getLocation();
            if (rockLoc.size() > 0) {
            	int r = (int) (Math.random() * rockLoc.size());
                Location newRock = rockLoc.get(r);
                
                int angel = locs.get(0).getDirectionToward(getLocation());
                newLoc = newRock.getAdjacentLocation(angel);
            }
            for (Location k : locs) {
                rockLoc.add(k);
            }
            time = 10;
            return newLoc;
        } else {
            if (time > 0) {
                time--;
            }
            return super.selectMoveLocation(locs);
        }
    }
    
    /**
     * Moves this critter to the given location loc
     * or setDirections of this critter when loc equals this.getLocations()
     * or removes this critter from its grid if life reduce to zero.
     * An actor may be added to the old location. If there is a different actor
     * at location loc, that actor is removed from the grid.
     * Override this method in subclasses that want to carry out other actions
     * (for example, turning this critter or adding an occupant in its previous
     * location). <br />
     * Postcondition: (1) <code>getLocation() == loc</code>. (2) The state of
     * all actors other than those at the old and new locations is unchanged.
     * @param loc the location to move to
     */
    public void makeMove(Location loc) {
        if (loc.equals(getLocation())) {
            setDirection((getDirection() + 180) % 360);
        } else {
            setDirection(getLocation().getDirectionToward(loc));
            super.makeMove(loc);
        }
        if (life == 0) {
            removeSelfFromGrid();
        }
    }
    public int getLife() {
    	return life;
    }
    public int getTime() {
    	return time;
    }
}
