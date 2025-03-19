package lv.rvt;

public class Player {
    private String name;
    private int number;
    private int goals;
    private int assists;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        this.goals = 0;
        this.assists = 0;
    }

    public Player() {} // Nepieciešams Gson'am

    public String getName() { return name; }
    public int getNumber() { return number; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }

    public void addGoal() { goals++; }
    public void addAssist() { assists++; }

    @Override
    public String toString() {
        return number + " - " + name + " | Goals: " + goals + ", Assists: " + assists;
    }
}
