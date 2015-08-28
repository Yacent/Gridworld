// @copyright yacent

import info.gridworld.actor.Bug;
import java.util.Arrays;

public class DancingBug extends Bug {
    private int steps;
    private int time;

    // using for define the times of turn before move
    private int[] timeList;
    
    public DancingBug(int[] list) {
        steps = 0;
        time = 0;
        timeList = Arrays.copyOf(list, list.length);
    }

    public void act() {
        if (steps == timeList.length) {
            steps = 0;
        }
        if (time < timeList[steps]) {
            turn();
            time++;
            return;
        }
        time = 0;
        move();
        steps++;
    }
}
