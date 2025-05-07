package lv.rvt;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AppTest {
    private PlayerService playerService;
    private final String TEST_TEMP_PATH = System.getProperty("java.io.tmpdir") + File.separator + "players_temp.json";
    private final String TEST_DATA_PATH = "data/players.json";

    @Before
    public void setUp() {
        // Create data directory if it doesn't exist
        new File("data").mkdirs();
        
        // Clear any existing test data
        try {
            Files.deleteIfExists(Paths.get(TEST_TEMP_PATH));
            Files.deleteIfExists(Paths.get(TEST_DATA_PATH));
        } catch (IOException e) {
            System.err.println("Error cleaning test files: " + e.getMessage());
        }
        
        playerService = new PlayerService();
    }
    
    @After
    public void tearDown() {
        // Clean up test files
        try {
            Files.deleteIfExists(Paths.get(TEST_TEMP_PATH));
        } catch (IOException e) {
            System.err.println("Error deleting test files: " + e.getMessage());
        }
    }

    @Test
    public void testPlayerCreation() {
        Player player = new Player(99, "Test Player");
        assertEquals(99, player.getNumber());
        assertEquals("Test Player", player.getName());
        assertEquals(0, player.getGoals());
        assertEquals(0, player.getAssists());
        assertEquals(0, player.getGames());
        assertEquals(0, player.getTotalPoints());
    }
    
    @Test
    public void testPlayerAddStats() {
        Player player = new Player(99, "Test Player");
        player.addStats(5, 10, 15);
        
        assertEquals(5, player.getGoals());
        assertEquals(10, player.getAssists());
        assertEquals(15, player.getGames());
        assertEquals(15, player.getTotalPoints()); // Should be goals + assists
    }
    
    @Test
    public void testAddPlayer() {
        playerService.addOrUpdatePlayer(1, "Player One", 5, 3, 2);
        List<Player> players = playerService.getPlayers();
        
        assertEquals(1, players.size());
        Player player = players.get(0);
        assertEquals(1, player.getNumber());
        assertEquals("Player One", player.getName());
        assertEquals(5, player.getGoals());
        assertEquals(3, player.getAssists());
        assertEquals(2, player.getGames());
        assertEquals(8, player.getTotalPoints());
    }
    
    @Test
    public void testUpdateExistingPlayer() {
        playerService.addOrUpdatePlayer(1, "Player One", 5, 3, 2);
        playerService.addOrUpdatePlayer(1, "", 2, 1, 1); // Update same player
        
        List<Player> players = playerService.getPlayers();
        assertEquals(1, players.size());
        
        Player player = players.get(0);
        assertEquals(1, player.getNumber());
        assertEquals("Player One", player.getName());
        assertEquals(7, player.getGoals());      // 5 + 2
        assertEquals(4, player.getAssists());    // 3 + 1
        assertEquals(3, player.getGames());      // 2 + 1
        assertEquals(11, player.getTotalPoints()); // 7 + 4
    }
    
    @Test
    public void testDeletePlayer() {
        playerService.addOrUpdatePlayer(1, "Player One", 5, 3, 2);
        playerService.addOrUpdatePlayer(2, "Player Two", 1, 2, 3);
        
        assertEquals(2, playerService.getPlayers().size());
        
        boolean result = playerService.deletePlayer(1);
        assertTrue(result);
        assertEquals(1, playerService.getPlayers().size());
        assertEquals("Player Two", playerService.getPlayers().get(0).getName());
        
        result = playerService.deletePlayer(99); // Non-existent player
        assertFalse(result);
    }
    
    @Test
    public void testPlayerExists() {
        playerService.addOrUpdatePlayer(1, "Player One", 5, 3, 2);
        
        assertTrue(playerService.exists(1));
        assertFalse(playerService.exists(99));
    }
    
    @Test
    public void testSortPlayersByNumber() {
        setupMultiplePlayers();
        
        List<Player> sortedAsc = playerService.sortPlayers(SortCriteria.NUMBER, true);
        assertEquals(3, sortedAsc.size());
        assertEquals(1, sortedAsc.get(0).getNumber());
        assertEquals(2, sortedAsc.get(1).getNumber());
        assertEquals(3, sortedAsc.get(2).getNumber());
        
        List<Player> sortedDesc = playerService.sortPlayers(SortCriteria.NUMBER, false);
        assertEquals(3, sortedDesc.size());
        assertEquals(3, sortedDesc.get(0).getNumber());
        assertEquals(2, sortedDesc.get(1).getNumber());
        assertEquals(1, sortedDesc.get(2).getNumber());
    }
    
    @Test
    public void testSortPlayersByName() {
        setupMultiplePlayers();
        
        List<Player> sortedAsc = playerService.sortPlayers(SortCriteria.NAME, true);
        assertEquals(3, sortedAsc.size());
        assertEquals("Alice", sortedAsc.get(0).getName());
        assertEquals("Bob", sortedAsc.get(1).getName());
        assertEquals("Charlie", sortedAsc.get(2).getName());
        
        List<Player> sortedDesc = playerService.sortPlayers(SortCriteria.NAME, false);
        assertEquals(3, sortedDesc.size());
        assertEquals("Charlie", sortedDesc.get(0).getName());
        assertEquals("Bob", sortedDesc.get(1).getName());
        assertEquals("Alice", sortedDesc.get(2).getName());
    }
    
    @Test
    public void testSortPlayersByGoals() {
        setupMultiplePlayers();
        
        List<Player> sorted = playerService.sortPlayers(SortCriteria.GOALS, false);
        assertEquals(3, sorted.size());
        assertEquals(10, sorted.get(0).getGoals());
        assertEquals(7, sorted.get(1).getGoals());
        assertEquals(3, sorted.get(2).getGoals());
    }
    
    @Test
    public void testSortPlayersByAssists() {
        setupMultiplePlayers();
        
        List<Player> sorted = playerService.sortPlayers(SortCriteria.ASSISTS, false);
        assertEquals(3, sorted.size());
        assertEquals(8, sorted.get(0).getAssists());
        assertEquals(5, sorted.get(1).getAssists());
        assertEquals(2, sorted.get(2).getAssists());
    }
    
    @Test
    public void testSortPlayersByGames() {
        setupMultiplePlayers();
        
        List<Player> sorted = playerService.sortPlayers(SortCriteria.GAMES, false);
        assertEquals(3, sorted.size());
        assertEquals(15, sorted.get(0).getGames());
        assertEquals(12, sorted.get(1).getGames());
        assertEquals(10, sorted.get(2).getGames());
    }
    
    @Test
    public void testSortPlayersByTotalPoints() {
        setupMultiplePlayers();
        
        List<Player> sorted = playerService.sortPlayers(SortCriteria.TOTAL_POINTS, false);
        assertEquals(3, sorted.size());
        assertEquals(18, sorted.get(0).getTotalPoints());
        assertEquals(12, sorted.get(1).getTotalPoints());
        assertEquals(5, sorted.get(2).getTotalPoints());
    }
    
    @Test
    public void testMultiCriteriaSorting() {
        // Add players with same goals but different assists
        playerService.addOrUpdatePlayer(1, "Player A", 5, 3, 10);
        playerService.addOrUpdatePlayer(2, "Player B", 5, 7, 10);
        playerService.addOrUpdatePlayer(3, "Player C", 5, 1, 10);
        
        // Sort by goals (primary) ascending, assists (secondary) descending
        List<Player> sorted = playerService.sortPlayers(
            SortCriteria.GOALS, true, 
            SortCriteria.ASSISTS, false
        );
        
        assertEquals(3, sorted.size());
        assertEquals("Player B", sorted.get(0).getName());
        assertEquals("Player A", sorted.get(1).getName());
        assertEquals("Player C", sorted.get(2).getName());
    }
    
    @Test
    public void testSearchByName() {
        setupMultiplePlayers();
        
        List<Player> results = playerService.searchByName("li");
        assertEquals(1, results.size());
        assertEquals("Charlie", results.get(0).getName());
        
        results = playerService.searchByName("b");
        assertEquals(1, results.size());
        assertEquals("Bob", results.get(0).getName());
        
        results = playerService.searchByName("z");
        assertEquals(0, results.size());
    }
    
    @Test
    public void testFilterByNumber() {
        setupMultiplePlayers();
        
        List<Player> results = playerService.filterPlayers(FilterCriteria.NUMBER, "2");
        assertEquals(1, results.size());
        assertEquals(2, results.get(0).getNumber());
        assertEquals("Bob", results.get(0).getName());
    }
    
    @Test
    public void testNumericFiltering() {
        setupMultiplePlayers();
        
        // Test goals greater than 5
        List<Player> results = playerService.filterPlayersNumeric(
            NumericFilterCriteria.GOALS, 
            5, 
            ComparisonOperator.GREATER_THAN
        );
        assertEquals(2, results.size());
        
        // Test assists equal to 5
        results = playerService.filterPlayersNumeric(
            NumericFilterCriteria.ASSISTS, 
            5, 
            ComparisonOperator.EQUAL
        );
        assertEquals(1, results.size());
        assertEquals("Bob", results.get(0).getName());
        
        // Test games less than or equal to 12
        results = playerService.filterPlayersNumeric(
            NumericFilterCriteria.GAMES, 
            12, 
            ComparisonOperator.LESS_THAN_OR_EQUAL
        );
        assertEquals(2, results.size());
    }
    
    @Test
    public void testCountPlayersInRange() {
        setupMultiplePlayers();
        
        // Count players with goals between 3 and 8
        int count = playerService.countPlayersInRange(NumericFilterCriteria.GOALS, 3, 8);
        assertEquals(2, count);
        
        // Count players with assists between 0 and 3
        count = playerService.countPlayersInRange(NumericFilterCriteria.ASSISTS, 0, 3);
        assertEquals(1, count);
    }
    
    @Test
    public void testCalculateAverage() {
        setupMultiplePlayers();
        
        // Test average goals
        double avg = playerService.calculateAverage(NumericFilterCriteria.GOALS);
        assertEquals(6.67, avg, 0.01); // Expected (3+7+10)/3 with small delta for floating point
        
        // Test average assists
        avg = playerService.calculateAverage(NumericFilterCriteria.ASSISTS);
        assertEquals(5.0, avg, 0.01); // Expected (2+5+8)/3
        
        // Test average games
        avg = playerService.calculateAverage(NumericFilterCriteria.GAMES);
        assertEquals(12.33, avg, 0.01); // Expected (10+12+15)/3
        
        // Test average total points
        avg = playerService.calculateAverage(NumericFilterCriteria.TOTAL_POINTS);
        assertEquals(11.67, avg, 0.01); // Expected (5+12+18)/3
    }
    
    @Test
    public void testColorConstants() {
        // Test that color constants are defined correctly
        assertNotNull(Color.RED);
        assertNotNull(Color.GREEN);
        assertNotNull(Color.BLUE);
        assertNotNull(Color.YELLOW);
        assertNotNull(Color.PURPLE);
        assertNotNull(Color.CYAN);
        assertNotNull(Color.WHITE);
        assertNotNull(Color.RESET);
    }
    
    // Helper method to set up multiple players for testing
    private void setupMultiplePlayers() {
        playerService.addOrUpdatePlayer(1, "Alice", 3, 2, 10);
        playerService.addOrUpdatePlayer(2, "Bob", 7, 5, 12);
        playerService.addOrUpdatePlayer(3, "Charlie", 10, 8, 15);
    }
}