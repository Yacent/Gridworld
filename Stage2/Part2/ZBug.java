// @copyright yacent

import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

public class ZBug extends Bug {
    private int steps;
    private int sideLength;

    /* sideNumber using for record the time it turns and which
    direction, and wherther to stop or not
    */
    private int sideNumber;

    public ZBug(int length)
    {
        steps = 0;
        sideLength = length;
        sideNumber = 0;
        setDirection(Location.EAST);
    }

    public void act() {
        if (sideNumber <= 2 && steps < sideLength) {
            if (canMove()) {
                move();
                steps++;
            }
        } else if (sideNumber == 0) {
            setDirection(Location.SOUTHWEST);
            steps = 0;
            sideNumber++;
        } else if (sideNumber == 1) {
            setDirection(Location.EAST);
            steps = 0;
            sideNumber++;
        }
    }
}
