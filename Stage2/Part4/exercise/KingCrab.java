/*
 * coptright@yuxin lin
 * override the getMoveLocations method
*/

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.util.ArrayList;

public class KingCrab extends CrabCritter {

    public void processActors(ArrayList<Actor> actors) {
        for (Actor a : actors) {
            if (!canActorFurtherAway(a)) {
                a.removeSelfFromGrid();
            }
        }
    }
    
    private boolean canActorFurtherAway(Actor a) {
        int angel = getLocation().getDirectionToward(a.getLocation());
        Location furtherLoc = a.getLocation().getAdjacentLocation(angel);
        if (getGrid().isValid(furtherLoc) && getGrid().get(furtherLoc) == null) {
            a.moveTo(furtherLoc);
            return true;
        }
        return false;
    }
}
