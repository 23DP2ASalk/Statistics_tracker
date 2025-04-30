package lv.rvt;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PlayerService {
    private final String FILE_PATH = "data/players.json";
    private final Gson gson = new Gson();
    private List<Player> players;

    public PlayerService() {
        loadPlayers();
    }

    private void loadPlayers() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
            players = gson.fromJson(reader, listType);
            if (players == null) players = new ArrayList<>();
        } catch (Exception e) {
            players = new ArrayList<>();
        }
    }

    public void savePlayers() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(players, writer);
        } catch (Exception e) {
            System.out.println("Failed to save players.");
        }
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
            if (p.getNumber() == number) {
                return true;
            }
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
