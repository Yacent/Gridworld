import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.grid.UnboundedGrid;

import java.awt.Color;

public final class SpiralBugRunner {
    private SpiralBugRunner() {}

    public static void main(String[] args) {
        //init the Gridworld to UnboundedGrid
        ActorWorld world = new ActorWorld(new UnboundedGrid());
        SpiralBug alice = new SpiralBug(3);
        alice.setColor(Color.RED);
        world.add(new Location(12, 12), alice);
        world.show();
    }
}