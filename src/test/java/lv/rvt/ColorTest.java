package lv.rvt;

import static org.junit.Assert.*;
import org.junit.Test;

public class ColorTest {

    @Test
    public void testColorConstants() {
        // Verify all color constants are defined
        assertNotNull("RED should be defined", Color.RED);
        assertNotNull("GREEN should be defined", Color.GREEN);
        assertNotNull("YELLOW should be defined", Color.YELLOW);
        assertNotNull("BLUE should be defined", Color.BLUE);
        assertNotNull("PURPLE should be defined", Color.PURPLE);
        assertNotNull("CYAN should be defined", Color.CYAN);
        assertNotNull("WHITE should be defined", Color.WHITE);
        assertNotNull("RESET should be defined", Color.RESET);
        
        // Verify color constants are ANSI escape codes
        assertTrue("RED should be ANSI escape code", Color.RED.startsWith("\u001B["));
        assertTrue("GREEN should be ANSI escape code", Color.GREEN.startsWith("\u001B["));
        assertTrue("YELLOW should be ANSI escape code", Color.YELLOW.startsWith("\u001B["));
        assertTrue("BLUE should be ANSI escape code", Color.BLUE.startsWith("\u001B["));
        assertTrue("PURPLE should be ANSI escape code", Color.PURPLE.startsWith("\u001B["));
        assertTrue("CYAN should be ANSI escape code", Color.CYAN.startsWith("\u001B["));
        assertTrue("WHITE should be ANSI escape code", Color.WHITE.startsWith("\u001B["));
        assertTrue("RESET should be ANSI escape code", Color.RESET.startsWith("\u001B["));
    }
    
    @Test
    public void testClearConsoleMethodExists() {
        // Just verify the method exists without actually running it
        // since we can't easily test its actual behavior in unit tests
        try {
            // This will throw NoSuchMethodException if the method doesn't exist
            Color.class.getMethod("clearConsole");
        } catch (NoSuchMethodException e) {
            fail("clearConsole method should exist in Color class");
        }
    }
}