package lv.rvt;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PlayerServicePersistenceTest {
    private final String TEST_DATA_DIR = "data";
    private final String TEST_DATA_PATH = TEST_DATA_DIR + "/players.json";
    private final String TEST_TEMP_PATH = System.getProperty("java.io.tmpdir") + File.separator + "players_temp.json";
    
    @Before
    public void setUp() {
        // Create test directory
        new File(TEST_DATA_DIR).mkdirs();
        
        // Clean existing test files
        try {
            Files.deleteIfExists(Paths.get(TEST_DATA_PATH));
            Files.deleteIfExists(Paths.get(TEST_TEMP_PATH));
        } catch (IOException e) {
            System.err.println("Error cleaning test files: " + e.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        // Clean up after test
        try {
            Files.deleteIfExists(Paths.get(TEST_DATA_PATH));
            Files.deleteIfExists(Paths.get(TEST_TEMP_PATH));
        } catch (IOException e) {
            System.err.println("Error deleting test files: " + e.getMessage());
        }
    }
    
    @Test
    public void testDataPersistence() {
        // Create service and add players
        PlayerService service = new PlayerService();
        service.addOrUpdatePlayer(1, "Test Player", 5, 3, 2);
        service.addOrUpdatePlayer(2, "Another Player", 1, 6, 3);
        
        // Verify players are added
        List<Player> players = service.getPlayers();
        assertEquals(2, players.size());
        
        // Force save
        service.savePlayers();
        
        // Create new service instance which should load from saved file
        PlayerService newService = new PlayerService();
        List<Player> loadedPlayers = newService.getPlayers();
        
        // Verify data was persisted
        assertEquals(2, loadedPlayers.size());
        
        // Check that player data is correct
        Player player1 = null;
        Player player2 = null;
        
        for (Player p : loadedPlayers) {
            if (p.getNumber() == 1) player1 = p;
            if (p.getNumber() == 2) player2 = p;
        }
        
        assertNotNull("Player 1 should be loaded", player1);
        assertNotNull("Player 2 should be loaded", player2);
        
        assertEquals("Test Player", player1.getName());
        assertEquals(5, player1.getGoals());
        assertEquals(3, player1.getAssists());
        assertEquals(2, player1.getGames());
        
        assertEquals("Another Player", player2.getName());
        assertEquals(1, player2.getGoals());
        assertEquals(6, player2.getAssists());
        assertEquals(3, player2.getGames());
    }
    
    @Test
    public void testPreExistingData() throws IOException {
        // Create a JSON file with predefined data
        String preExistingData = "[" +
            "{\"number\":10,\"name\":\"Pre-existing Player\",\"goals\":15,\"assists\":10,\"games\":20}" +
            "]";
            
        // Ensure directory exists
        new File(TEST_DATA_DIR).mkdirs();
        
        // Write test data to file
        try (FileWriter writer = new FileWriter(TEST_DATA_PATH)) {
            writer.write(preExistingData);
        }
        
        // Create service which should load the pre-existing data
        PlayerService service = new PlayerService();
        List<Player> players = service.getPlayers();
        
        // Verify data was loaded
        assertEquals(1, players.size());
        
        Player player = players.get(0);
        assertEquals(10, player.getNumber());
        assertEquals("Pre-existing Player", player.getName());
        assertEquals(15, player.getGoals());
        assertEquals(10, player.getAssists());
        assertEquals(20, player.getGames());
        assertEquals(25, player.getTotalPoints());
    }
    
    @Test
    public void testEmptyDatabase() {
        // Create service with empty database
        PlayerService service = new PlayerService();
        
        // Verify list is empty but not null
        List<Player> players = service.getPlayers();
        assertNotNull(players);
        assertEquals(0, players.size());
        
        // Verify calculations work with empty list
        double avg = service.calculateAverage(NumericFilterCriteria.GOALS);
        assertEquals(0.0, avg, 0.001);
        
        int count = service.countPlayersInRange(NumericFilterCriteria.GOALS, 0, 100);
        assertEquals(0, count);
    }
    
    @Test
    public void testFallbackToTempFile() throws IOException {
        // Create data in temp file but not in data file
        String tempData = "[" +
            "{\"number\":99,\"name\":\"Temp Player\",\"goals\":5,\"assists\":5,\"games\":5}" +
            "]";
            
        // Write to temp file
        try (FileWriter writer = new FileWriter(TEST_TEMP_PATH)) {
            writer.write(tempData);
        }
        
        // Create service which should load from temp file
        PlayerService service = new PlayerService();
        List<Player> players = service.getPlayers();
        
        // Verify data was loaded from temp file
        assertEquals(1, players.size());
        assertEquals(99, players.get(0).getNumber());
        assertEquals("Temp Player", players.get(0).getName());
    }
}