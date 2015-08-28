package info.gridworld.maze;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd;
	public Stack<Location> crossLocation = new Stack<Location>();
	public Integer stepCount;
	boolean hasShown;//final message has been shown
	
	private ArrayList<Location> viewed;
	
	private boolean isBack;
	private int[] validDirection = {Location.NORTH, Location.EAST,
			Location.SOUTH, Location.WEST};

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
		stepCount = new Integer(0);
		isEnd = false;
		viewed = new ArrayList<Location>();
		hasShown = false;
		isBack = false;
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		
		if (viewed.size() == 0) {
			viewed.add(getLocation());
			last = getLocation();
		}
		
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		}
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return null;
		}
		Location loc_current = getLocation();
		ArrayList<Location> valid = new ArrayList<Location>();
		
		for (int angel : validDirection) {
			Location target = loc_current.getAdjacentLocation(angel);
			if (gr.isValid(target)) {
				valid.add(target);
			}
		}
		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		Location  loc = getLocation();
		Location target = null;
		
		Grid<Actor> gr = getGrid();
		
		for (Location l : getValid(loc)) {
			if (gr.get(l) instanceof Rock) {
				if (gr.get(l).getColor().equals(Color.RED)) {
					isEnd = true;
				} else {
					continue;
				}
			}
			
			boolean isViewed = false;
			
			for (Location viewLocation : viewed) {
				if (l.equals(viewLocation)) {
					isViewed = true;
				}
			}
			
			if (!isViewed) {
				target = new Location(l.getRow(), l.getCol());
				next = target;
				isBack = false;
				return true;
			}
		}
		
		if (target == null) {
			next = crossLocation.pop();
			isBack = true;
			return true;
		}
		return false;
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			if (last != null && !isBack) {
				crossLocation.push(last);
			}
			moveTo(next);
			viewed.add(next);
			last = next;
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
}
