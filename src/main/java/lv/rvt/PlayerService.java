package lv.rvt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private final String FILE_PATH = "data\\players.json";
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
        players.add(new Player(number, name));
        savePlayers();
    }

    public List<Player> getPlayers() {
        return players;
    }
}
