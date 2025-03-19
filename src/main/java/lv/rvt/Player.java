package lv.rvt;

public class Player {
    private String name;
    private int goals;
    private int assists;
    private int gamesPlayed;

    public Player(String name, int goals, int assists, int gamesPlayed) {
        this.name = name;
        this.goals = goals;
        this.assists = assists;
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
    
    @Override
    public String toString() {
        return name + " - Vārti: " + goals + ", Piespēles: " + assists + ", Spēles: " + gamesPlayed;
    }
}
