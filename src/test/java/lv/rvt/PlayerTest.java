package lv.rvt;

import static org.junit.Assert.*;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testPlayerConstructor() {
        Player player = new Player(42, "Wayne Gretzky");
        
        assertEquals(42, player.getNumber());
        assertEquals("Wayne Gretzky", player.getName());
        assertEquals(0, player.getGoals());
        assertEquals(0, player.getAssists());
        assertEquals(0, player.getGames());
    }
    
    @Test
    public void testAddStats() {
        Player player = new Player(99, "The Great One");
        
        // First add
        player.addStats(5, 10, 1);
        assertEquals(5, player.getGoals());
        assertEquals(10, player.getAssists());
        assertEquals(1, player.getGames());
        
        // Add more stats
        player.addStats(3, 7, 2);
        assertEquals(8, player.getGoals());
        assertEquals(17, player.getAssists());
        assertEquals(3, player.getGames());
        
        // Add negative stats (might not be intended behavior but testing anyway)
        player.addStats(-1, -2, -1);
        assertEquals(7, player.getGoals());
        assertEquals(15, player.getAssists());
        assertEquals(2, player.getGames());
    }
    
    @Test
    public void testGetTotalPoints() {
        Player player = new Player(88, "Rookie");
        
        // Initially 0
        assertEquals(0, player.getTotalPoints());
        
        // Add some stats
        player.addStats(3, 5, 10);
        assertEquals(8, player.getTotalPoints()); // 3 goals + 5 assists
        
        // Add more
        player.addStats(10, 20, 5);
        assertEquals(38, player.getTotalPoints()); // 13 goals + 25 assists
    }
    
    @Test
    public void testToString() {
        Player player = new Player(10, "Test Player");
        player.addStats(15, 25, 40);
        
        String result = player.toString(20);
        
        // Verify the toString method formats correctly
        assertTrue(result.contains("10"));          // Player number
        assertTrue(result.contains("Test Player")); // Player name
        assertTrue(result.contains("15"));          // Goals
        assertTrue(result.contains("25"));          // Assists
        assertTrue(result.contains("40"));          // Games
    }
}