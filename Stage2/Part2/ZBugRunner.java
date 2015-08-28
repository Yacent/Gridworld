import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

public final class ZBugRunner {
    private ZBugRunner() {}

    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        ZBug alice = new ZBug(4);
        alice.setColor(Color.ORANGE);
        world.add(new Location(0, 0), alice);
        world.show();
    }
}