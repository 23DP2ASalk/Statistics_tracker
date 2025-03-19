package lv.rvt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.xml.crypto.Data;

public class PlayerService {
    private final ArrayList<Player> players = new ArrayList<>();
    private final String fileName = "player.json";
    private final Gson gson = new Gson();

    public PlayerService() {
        loadPlayers();
    }

    public void addPlayer(Player newPlayer) {
        for (Player p : players) {
            if (p.getNumber() == newPlayer.getNumber()) {
                p.addGoals(newPlayer.getGoals());
                p.addAssists(newPlayer.getAssists());
                p.addGamesPlayed(newPlayer.getGamesPlayed());
                return;
            }
        }
        players.add(newPlayer);
    }

    public ArrayList<Player> getAllPlayers() {
        return players;
    }

    public void savePlayers() {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(players, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayers() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
            ArrayList<Player> loadedPlayers = gson.fromJson(reader, listType);
            if (loadedPlayers != null) players.addAll(loadedPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
