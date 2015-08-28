import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class HerbCritterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEatFlower() {
		ActorWorld world = new ActorWorld();
		Flower flower = new Flower();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(5, 4), herbCritter);
		world.add(new Location(4, 4), flower);
		world.step();
		assertEquals(null, flower.getGrid());
	}
	@Test
	public void testLifeUp() {
		ActorWorld world = new ActorWorld();
		Flower flower = new Flower();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(5, 4), herbCritter);
		world.add(new Location(4, 4), flower);
		world.step();
		assertEquals(13, herbCritter.getLife());
	}
	@Test
	public void testLifeDown() {
		ActorWorld world = new ActorWorld();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(5, 4), herbCritter);
		world.step();
		assertEquals(11, herbCritter.getLife());
	}
	@Test
	public void testTeleport() {
		ActorWorld world = new ActorWorld();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(5, 4), herbCritter);
		world.add(new Location(4, 4), new Rock());
		world.add(new Location(8, 8), new Rock());
		herbCritter.act();
		assertEquals(new Location(9, 8), herbCritter.getLocation());
	}
	@Test
	public void testTeleportColdTime() {
		ActorWorld world = new ActorWorld();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(5, 4), herbCritter);
		world.add(new Location(4, 4), new Rock());
		world.add(new Location(8, 8), new Rock());
		world.step();
		assertEquals(10, herbCritter.getTime());
	}
	@Test
	public void testTeleportColdTimeDown() {
		ActorWorld world = new ActorWorld();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(5, 4), herbCritter);
		world.add(new Location(4, 4), new Rock());
		world.add(new Location(8, 8), new Rock());
		world.step();
		world.step();
		assertEquals(9, herbCritter.getTime());
	}
	@Test
	public void teststarveTodeath() {
		ActorWorld world = new ActorWorld();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(5, 4), herbCritter);
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		world.step();
		assertEquals(null, herbCritter.getGrid());
	}
	@Test
	public void testTurn() {
		ActorWorld world = new ActorWorld();
		HerbCritter herbCritter = new HerbCritter();
		world.add(new Location(0, 0), herbCritter);
		world.add(new Location(1, 0), new Flower());
		world.add(new Location(0, 1), new Flower());
		world.add(new Location(1, 1), new Flower());
		world.step();
		assertEquals(Location.SOUTH, herbCritter.getDirection());
	}
}
