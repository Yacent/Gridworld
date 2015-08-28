/*
copyright@ yuxin lin

override the processActors() method and getActors() method
*/

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.util.ArrayList;

import java.awt.Color;

public class BlusterCritter extends Critter {

    private int courage;

    private static final double DARKENING_FACTOR = 0.2;

    public BlusterCritter(int c) {
        super();
        courage = c;
        setColor(Color.ORANGE);
    }

    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();

        Location loc = getLocation();
        
        
        // check for the variable, add to actors arraylist
        for (int i = loc.getRow() - 2; i < loc.getRow() + 3; i++) {
            for (int j = loc.getCol() - 2; j < loc.getCol() + 3; j++) {
                Location newLoc = new Location(i, j);
                if (getGrid().isValid(newLoc)) {
                    Actor a = getGrid().get(newLoc);
                    if (a != null) {
                        actors.add(a);
                    }
                }
            }
        }
        return actors;
    }

    public void processActors(ArrayList<Actor> actors) {
        int n = actors.size();
        if (n == 0) {
            return;
        }
        
        // calculate the number of Critter
        int count = 0;
        for (Actor a : actors) {
            if (a instanceof Critter) {
                count++;
            }
        }
        if (count > courage) {
            darker();
        } else {
            brighter();
        }
    }

    private void darker() {
        Color c = getColor();
        int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

        setColor(new Color(red, green, blue));
    }

    private void brighter() {
        Color c = getColor();

        int red = (int) (c.getRed() + DARKENING_FACTOR * 30);
        int green = (int) (c.getGreen() + DARKENING_FACTOR * 30);
        int blue = (int) (c.getBlue() + DARKENING_FACTOR * 30);

        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        

        setColor(new Color(red, green, blue));
    }
}
