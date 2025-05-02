package lv.rvt;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private PlayerService playerService;
    private Scanner scanner;

    public ConsoleUI() {
        playerService = new PlayerService();
        scanner = new Scanner(System.in);
    }

    public void start() {
        Color.clearConsole();
        printAsciiArt();

        while (true) {
            showMenu();
            char choice = scanner.next().charAt(0);

            switch (choice) {
                case '1' -> addPlayer();
                case '2' -> showPlayers();
                case '3' -> deletePlayer();
                case '4' -> {
                    System.out.println(Color.RED + "Exiting..." + Color.RESET);
                    return;
                }
                default -> System.out.println(Color.RED + "Invalid choice!" + Color.RESET);
            }
        }
    }

    private void printAsciiArt() {
        System.out.println(Color.YELLOW +
                "   _____ _        _   _     _   _            _______             _             \n" +
                "  / ____| |      | | (_)   | | (_)          |__   __|           | |            \n" +
                " | (___ | |_ __ _| |_ _ ___| |_ _  ___ ___     | |_ __ __ _  ___| | _____ _ __ \n" +
                "  \\___ \\| __/ _` | __| / __| __| |/ __/ __|    | | '__/ _` |/ __| |/ / _ \\ '__|\n" +
                "  ____) | || (_| | |_| \\__ \\ |_| | (__\\__ \\    | | | | (_| | (__|   <  __/ |   \n" +
                " |_____/ \\__\\__,_|\\__|_|___/\\__|_|\\___|___/    |_|_|  \\__,_|\\___|_|\\_\\___|_|   \n" +
                "                                                                               "
                + Color.RESET);
    }

    private void showMenu() {
        System.out.println(Color.CYAN + "=== STATISTICS TRACKER ===" + Color.RESET);
        System.out.println(Color.GREEN + "[1] Add Player Stats" + Color.RESET);
        System.out.println(Color.GREEN + "[2] Show Players" + Color.RESET);
        System.out.println(Color.GREEN + "[3] Delete Player" + Color.RESET);
        System.out.println(Color.GREEN + "[4] Exit" + Color.RESET);
        System.out.print("Select option: ");
    }

    private void addPlayer() {
        System.out.print("Enter player number (0 to go back): ");
        int number = scanner.nextInt();
        if (number == 0) return;
        scanner.nextLine();

        if (playerService.exists(number)) {
            System.out.println(Color.YELLOW + "Player with this number already exists." + Color.RESET);
            System.out.print("Enter goals to add: ");
            int goals = scanner.nextInt();
            System.out.print("Enter assists to add: ");
            int assists = scanner.nextInt();
            System.out.print("Enter games to add: ");
            int games = scanner.nextInt();
            playerService.addOrUpdatePlayer(number, "", goals, assists, games);
        } else {
            System.out.print("Enter player full name: ");
            String name = scanner.nextLine();
            System.out.print("Enter goals: ");
            int goals = scanner.nextInt();
            System.out.print("Enter assists: ");
            int assists = scanner.nextInt();
            System.out.print("Enter games: ");
            int games = scanner.nextInt();
            playerService.addOrUpdatePlayer(number, name, goals, assists, games);
        }

        System.out.println(Color.GREEN + "Player stats updated!" + Color.RESET);
        scanner.nextLine();
    }

    private void deletePlayer() {
        System.out.print("Enter player number to delete (0 to go back): ");
        int number = scanner.nextInt();
        scanner.nextLine();

        if (number == 0) return;

        if (playerService.deletePlayer(number)) {
            System.out.println(Color.GREEN + "Player deleted successfully!" + Color.RESET);
        } else {
            System.out.println(Color.RED + "Player not found!" + Color.RESET);
        }
    }

    private void showPlayers() {
        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            System.out.println(Color.RED + "No players found!" + Color.RESET);
            return;
        }

        int maxNameLength = "Name".length();
        for (Player p : players) {
            if (p.getName().length() > maxNameLength) {
                maxNameLength = p.getName().length();
            }
        }

        String divider = "+" + "-".repeat(6) + "+" + "-".repeat(maxNameLength + 2) +
                "+-------+---------+-------+";
        String header = String.format("| %-4s | %-" + maxNameLength + "s | %-5s | %-7s | %-5s |",
                "Num", "Name", "Goals", "Assists", "Games");

        System.out.println(Color.BLUE + divider + Color.RESET);
        System.out.println(Color.BLUE + header + Color.RESET);
        System.out.println(Color.BLUE + divider + Color.RESET);

        for (Player p : players) {
            System.out.println(p.toString(maxNameLength));
        }

        System.out.println(Color.BLUE + divider + Color.RESET);
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
