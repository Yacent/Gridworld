import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;


public class Jumper extends Bug {
    public Jumper() {
        this.setColor(Color.RED);
    }

    public Jumper(Color color) {
        setColor(color);
    }

    public boolean canJump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return false;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location nextnext = next.getAdjacentLocation(getDirection());
        if (!gr.isValid(nextnext)) {
            return false;
        }
        Actor neighbor = gr.get(nextnext);
        return (neighbor == null) || (neighbor instanceof Flower);
    }

    public void jump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location nextnext = next.getAdjacentLocation(getDirection());
        if (gr.isValid(nextnext)) {
            super.moveTo(nextnext);
        } else {
            removeSelfFromGrid();
        }
    }

    public void act() {
        if (canJump()) {
            jump();
        } else {
            if (canMove()) {
                move();
            } else {
                turn();
            }
        }
    }
}
