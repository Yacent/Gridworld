import info.gridworld.actor.*;
import info.gridworld.grid.Location;


public class JumperRunner {

    private ActorWorld world;
    private Jumper jumper;

    public JumperRunner() {
        world = new ActorWorld();
        jumper = new Jumper();
        world.add(new Location(4, 4), jumper);
        world.add(new Location(3, 4), new Rock());
        world.show();
    }
    
    public Jumper getJumper() {
        return jumper;
    }

    public ActorWorld getActorWorld() {
        return world;
    }

    public static void main(String[] args) {
        new JumperRunner();
    }

}
