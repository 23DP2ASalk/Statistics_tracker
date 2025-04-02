package lv.rvt;

public class Player {
    private int number;
    private String name;
    private int goals;
    private int assists;
    private int games;

    public Player(int number, String name) {
        this.number = number;
        this.name = name;
        this.goals = 0;
        this.assists = 0;
        this.games = 0;
    }

    public int getNumber() { return number; }
    public String getName() { return name; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getGames() { return games; }

    public void addStats(int goals, int assists, int games) {
        this.goals += goals;
        this.assists += assists;
        this.games += games;
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-13s | %-5d | %-7d | %-5d |",
                number, name, goals, assists, games);
    }
}
