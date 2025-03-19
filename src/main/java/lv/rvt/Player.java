package lv.rvt;

public class Player {
    private int number;
    private String name;
    private int goals;
    private int assists;
    private int gamesPlayed;

    public Player(int number, String name, int goals, int assists, int gamesPlayed) {
        this.number = number;
        this.name = name;
        this.goals = goals;
        this.assists = assists;
        this.gamesPlayed = gamesPlayed;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void addGoals(int goals) {
        this.goals += goals;
    }

    public void addAssists(int assists) {
        this.assists += assists;
    }

    public void addGamesPlayed(int games) {
        this.gamesPlayed += games;
    }

    @Override
    public String toString() {
        return number + " | " + name + " | G: " + goals + " | A: " + assists + " | Games: " + gamesPlayed;
    }
}
