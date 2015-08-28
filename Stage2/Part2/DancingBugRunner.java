import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

public final class DancingBugRunner {
    private DancingBugRunner() {}

    public static void main(String[] args) {
        int[] turnlist = new int[]{7, 2, 2, 2, 8, 6, 6, 6};
        ActorWorld world = new ActorWorld();
        DancingBug alice = new DancingBug(turnlist);
        alice.setColor(Color.ORANGE);
        world.add(new Location(5, 5), alice);
        world.show();
    }
}