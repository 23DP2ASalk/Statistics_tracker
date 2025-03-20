package lv.rvt;

import java.util.Scanner;

public class ConsoleUI {
    private final PlayerService service = new PlayerService();
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new ConsoleUI().start();
    }

    public void start() {
        printAsciiArt();
        while (true) {
            System.out.println("\n1. Pievienot spēlētāju");
            System.out.println("2. Parādīt visus spēlētājus");
            System.out.println("3. Iziet");
            System.out.print("Izvēlies darbību: ");
            String input = sc.nextLine();

            switch (input) {
                case "1" -> addPlayer();
                case "2" -> showPlayers();
                case "3" -> {
                    service.savePlayers();
                    System.out.println("Dati saglabāti. Uz redzēšanos!");
                    return;
                }
                default -> System.out.println("Nederīga izvēle.");
            }
        }
    }

    private void addPlayer() {
        System.out.print("Spēlētāja numurs: ");
        int number = Integer.parseInt(sc.nextLine());
        System.out.print("Vārds: ");
        String name = sc.nextLine();
        System.out.print("Gūto vārtu skaits: ");
        int goals = Integer.parseInt(sc.nextLine());
        System.out.print("Piespēļu skaits: ");
        int assists = Integer.parseInt(sc.nextLine());
        System.out.print("Spēļu skaits: ");
        int gamesPlayed = Integer.parseInt(sc.nextLine());

        Player player = new Player(number, name, goals, assists, gamesPlayed);
        service.addPlayer(player);
        System.out.println(Color.GREEN + "Spēlētājs pievienots vai atjaunināts veiksmīgi!" + Color.RESET);
    }

    private void showPlayers() {
        for (Player p : service.getAllPlayers()) {
            System.out.println(p);
        }
    }

    private void printAsciiArt() {
        System.out.println(Color.YELLOW + "   _____ _        _   _     _   _            _______             _             ");
        System.out.println("  / ____| |      | | (_)   | | (_)          |__   __|           | |            ");
        System.out.println(" | (___ | |_ __ _| |_ _ ___| |_ _  ___ ___     | |_ __ __ _  ___| | _____ _ __ ");
        System.out.println("  \\___ \\| __/ _` | __| / __| __| |/ __/ __|    | | '__/ _` |/ __| |/ / _ \\ '__|");
        System.out.println("  ____) | || (_| | |_| \\__ \\ |_| | (__\\__ \\    | | | | (_| | (__|   <  __/ |   ");
        System.out.println(" |_____/ \\__\\__,_|\\__|_|___/\\__|_|\\___|___/    |_|_|  \\__,_|\\___|_|\\_\\___|_|   ");
        System.out.println("                                                                               " + Color.RESET);
    }
}
