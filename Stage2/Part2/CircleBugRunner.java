import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

public final class CircleBugRunner {
    private CircleBugRunner() {}

    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        CircleBug alice = new CircleBug(2);
        alice.setColor(Color.ORANGE);
        world.add(new Location(5, 2), alice);
        world.show();
    }
}