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
            try {
                char choice = (char) System.in.read();
                System.in.read(); // Patērē jauno rindu (\n)

                switch (choice) {
                    case '1' -> addPlayer();
                    case '2' -> showPlayers();
                    case '3' -> removePlayer();
                    case '4' -> {
                        System.out.println(Color.RED + "Exiting..." + Color.RESET);
                        return;
                    }
                    default -> System.out.println(Color.RED + "Invalid choice!" + Color.RESET);
                }
            } catch (Exception e) {
                System.out.println(Color.RED + "Error reading input!" + Color.RESET);
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
        System.out.println(Color.GREEN + "[3] Remove Player" + Color.RESET);
        System.out.println(Color.GREEN + "[4] Exit" + Color.RESET);
        System.out.print("Select option: ");
    }

    private void addPlayer() {
        System.out.print("Enter player number: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter player name: ");
        String name = scanner.nextLine();

        System.out.print("Enter goals: ");
        int goals = scanner.nextInt();

        System.out.print("Enter assists: ");
        int assists = scanner.nextInt();

        System.out.print("Enter games: ");
        int games = scanner.nextInt();

        playerService.addOrUpdatePlayer(number, name, goals, assists, games);
        System.out.println(Color.GREEN + "Player stats updated!" + Color.RESET);
        scanner.nextLine();
    }

    private void removePlayer() {
        System.out.print("Enter player number to remove: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        playerService.removePlayer(number);
        System.out.println(Color.RED + "Player removed!" + Color.RESET);
    }

    private void showPlayers() {
        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            System.out.println(Color.RED + "No players found!" + Color.RESET);
            return;
        }

        System.out.println(Color.BLUE + "+------+---------------+-------+---------+-------+" + Color.RESET);
        System.out.println(Color.BLUE + "| Num  | Name          | Goals | Assists | Games |" + Color.RESET);
        System.out.println(Color.BLUE + "+------+---------------+-------+---------+-------+" + Color.RESET);

        for (Player p : players) {
            System.out.printf("| %-4d | %-13s | %-5d | %-7d | %-5d |\n",
                    p.getNumber(), p.getName(), p.getGoals(), p.getAssists(), p.getGames());
        }

        System.out.println(Color.BLUE + "+------+---------------+-------+---------+-------+" + Color.RESET);
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
    