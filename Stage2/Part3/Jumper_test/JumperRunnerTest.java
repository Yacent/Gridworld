import static org.junit.Assert.*;
import info.gridworld.grid.Location;

import org.junit.Before;
import org.junit.Test;


public class JumperRunnerTest {
    
    private static JumperRunner jumperRunner = new JumperRunner();
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testJumperRunner() {
        Jumper jumper = jumperRunner.getJumper();
        assertEquals(true, jumper.canJump());
        
        jumper.jump();
        assertEquals(new Location(2, 4), jumper.getLocation());
        jumper.act();
        assertEquals(false, jumper.canJump());
        for (int i = 0; i < 4; i++) {
            jumper.act();
        }
        
        assertEquals(false, jumper.canJump());
        
        jumper.act();
        assertEquals(new Location(0, 9), jumper.getLocation());
    }

}