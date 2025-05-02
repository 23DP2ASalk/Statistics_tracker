package lv.rvt;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PlayerService {
    private final String TEMP_FILE_PATH;
    private final String DATA_FILE_PATH = "data/players.json";
    private final Gson gson = new Gson();
    private List<Player> players;

    public PlayerService() {
        TEMP_FILE_PATH = System.getProperty("java.io.tmpdir") + File.separator + "players_temp.json";
        loadPlayers();
    }

    private void loadPlayers() {
        players = loadFromFile(TEMP_FILE_PATH);
        if (players == null || players.isEmpty()) {
            players = loadFromFile(DATA_FILE_PATH);
        }
        if (players == null) {
            players = new ArrayList<>();
        }
    }

    private List<Player> loadFromFile(String path) {
        try (FileReader reader = new FileReader(path)) {
            Type listType = new TypeToken<ArrayList<Player>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            return null;
        }
    }

    private void saveToFile(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(players, writer);
        } catch (Exception e) {
            System.out.println("Failed to save to: " + path);
        }
    }

    public void savePlayers() {
        saveToFile(DATA_FILE_PATH);
        saveToFile(TEMP_FILE_PATH);
    }

    public void addOrUpdatePlayer(int number, String name, int goals, int assists, int games) {
        for (Player p : players) {
            if (p.getNumber() == number) {
                p.addStats(goals, assists, games);
                savePlayers();
                return;
            }
        }
        Player player = new Player(number, name);
        player.addStats(goals, assists, games);
        players.add(player);
        savePlayers();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean exists(int number) {
        for (Player p : players) {
            if (p.getNumber() == number) return true;
        }
        return false;
    }

    public boolean deletePlayer(int number) {
        for (Player p : players) {
            if (p.getNumber() == number) {
                players.remove(p);
                savePlayers();
                return true;
            }
        }
        return false;
    }
}
