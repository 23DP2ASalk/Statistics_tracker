package lv.rvt;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PlayerService {
    private final String TEMP_FILE_PATH;
    private final String DATA_FILE_PATH = "Statistics_tracker/data/players.json";
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
    
    // Feature 6: Data sorting by multiple criteria
    public List<Player> sortPlayers(SortCriteria primaryCriteria, boolean ascending) {
        List<Player> sortedPlayers = new ArrayList<>(players);
        Comparator<Player> comparator = getComparator(primaryCriteria, ascending);
        sortedPlayers.sort(comparator);
        return sortedPlayers;
    }
    
    // Overloaded method for multi-criteria sorting
    public List<Player> sortPlayers(SortCriteria primaryCriteria, boolean primaryAscending, 
                                    SortCriteria secondaryCriteria, boolean secondaryAscending) {
        List<Player> sortedPlayers = new ArrayList<>(players);
        Comparator<Player> primaryComparator = getComparator(primaryCriteria, primaryAscending);
        Comparator<Player> secondaryComparator = getComparator(secondaryCriteria, secondaryAscending);
        
        sortedPlayers.sort(primaryComparator.thenComparing(secondaryComparator));
        return sortedPlayers;
    }
    
    private Comparator<Player> getComparator(SortCriteria criteria, boolean ascending) {
        Comparator<Player> comparator = switch (criteria) {
            case NUMBER -> Comparator.comparingInt(Player::getNumber);
            case NAME -> Comparator.comparing(Player::getName, String.CASE_INSENSITIVE_ORDER);
            case GOALS -> Comparator.comparingInt(Player::getGoals);
            case ASSISTS -> Comparator.comparingInt(Player::getAssists);
            case GAMES -> Comparator.comparingInt(Player::getGames);
            case TOTAL_POINTS -> Comparator.comparingInt(Player::getTotalPoints);
        };
        
        return ascending ? comparator : comparator.reversed();
    }
    
    // Feature 7: Data searching and filtering by different criteria
    public List<Player> filterPlayers(FilterCriteria criteria, String value) {
        return players.stream()
            .filter(player -> matchesFilter(player, criteria, value))
            .collect(Collectors.toList());
    }
    
    public List<Player> filterPlayersNumeric(NumericFilterCriteria criteria, int value, ComparisonOperator operator) {
        return players.stream()
            .filter(player -> matchesNumericFilter(player, criteria, value, operator))
            .collect(Collectors.toList());
    }
    
    public List<Player> searchByName(String searchText) {
        return players.stream()
            .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    private boolean matchesFilter(Player player, FilterCriteria criteria, String value) {
        return switch (criteria) {
            case NAME -> player.getName().toLowerCase().contains(value.toLowerCase());
            case NUMBER -> String.valueOf(player.getNumber()).equals(value);
        };
    }
    
    private boolean matchesNumericFilter(Player player, NumericFilterCriteria criteria, int value, ComparisonOperator operator) {
        int playerValue = switch (criteria) {
            case NUMBER -> player.getNumber();
            case GOALS -> player.getGoals();
            case ASSISTS -> player.getAssists();
            case GAMES -> player.getGames();
            case TOTAL_POINTS -> player.getTotalPoints();
        };
        
        return switch (operator) {
            case EQUAL -> playerValue == value;
            case GREATER_THAN -> playerValue > value;
            case LESS_THAN -> playerValue < value;
            case GREATER_THAN_OR_EQUAL -> playerValue >= value;
            case LESS_THAN_OR_EQUAL -> playerValue <= value;
        };
    }
    
    // Feature 8: Data processing
    public int countPlayersInRange(NumericFilterCriteria criteria, int minValue, int maxValue) {
        return (int) players.stream()
            .filter(player -> {
                int value = switch (criteria) {
                    case NUMBER -> player.getNumber();
                    case GOALS -> player.getGoals();
                    case ASSISTS -> player.getAssists();
                    case GAMES -> player.getGames();
                    case TOTAL_POINTS -> player.getTotalPoints();
                };
                return value >= minValue && value <= maxValue;
            })
            .count();
    }
    
    public double calculateAverage(NumericFilterCriteria criteria) {
        if (players.isEmpty()) {
            return 0.0;
        }
        
        double sum = players.stream()
            .mapToDouble(player -> {
                return switch (criteria) {
                    case NUMBER -> player.getNumber();
                    case GOALS -> player.getGoals();
                    case ASSISTS -> player.getAssists();
                    case GAMES -> player.getGames();
                    case TOTAL_POINTS -> player.getTotalPoints();
                };
            })
            .sum();
            
        return sum / players.size();
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

enum SortCriteria {
    NUMBER, NAME, GOALS, ASSISTS, GAMES, TOTAL_POINTS
}

enum FilterCriteria {
    NAME, NUMBER
}

enum NumericFilterCriteria {
    NUMBER, GOALS, ASSISTS, GAMES, TOTAL_POINTS
}

enum ComparisonOperator {
    EQUAL, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL
}