/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains chameleon critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class HerbRunner {
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Location(1, 1), new Rock());
        world.add(new Location(1, 8), new Rock());
        world.add(new Location(8, 8), new Rock(Color.BLUE));
        world.add(new Location(8, 1), new Rock(Color.PINK));
        world.add(new Location(5, 4), new Rock(Color.RED));
        world.add(new Location(9, 0), new Bug());
        world.add(new Bug());
        world.add(new Bug());
        world.add(new Bug());
        world.add(new Location(9, 9), new Bug());
        world.add(new Location(4, 2), new HerbCritter(Color.ORANGE));
        world.add(new Location(5, 8), new HerbCritter(Color.RED));
        world.show();
    }
}