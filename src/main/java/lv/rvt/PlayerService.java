package lv.rvt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class PlayerService {
    private List<Player> players;
    private static final String FILE_PATH = "data/players.json";
    private final Gson gson = new Gson();

    public PlayerService() {
        this.players = new ArrayList<>();
        loadFromFile();
    }

    public void addPlayer(Player player) {
        players.add(player);
        saveToFile();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player findByNumber(int number) {
        for (Player p : players) {
            if (p.getNumber() == number) return p;
        }
        return null;
    }
    public List<Player> getAllPlayers() {
        return players;
    }
    
    public void savePlayers() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(players, writer);
        } catch (IOException e) {
            System.out.println("Kļūda saglabājot failu.");
        }
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(players, writer);
        } catch (IOException e) {
            System.out.println("❌ Neizdevās saglabāt failu: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
            players = gson.fromJson(reader, listType);
            if (players == null) players = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("ℹ️ Nav esoša JSON faila, sāku ar tukšu sarakstu.");
        }
    }
}
