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
            scanner.nextLine();

            switch (choice) {
                case '1' -> addPlayer();
                case '2' -> showPlayers();
                case '3' -> deletePlayer();
                case '4' -> sortPlayers();
                case '5' -> filterPlayers();
                case '6' -> showStatistics();
                case '7' -> {
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
        System.out.println(Color.GREEN + "[4] Sort Players" + Color.RESET);
        System.out.println(Color.GREEN + "[5] Filter Players" + Color.RESET);
        System.out.println(Color.GREEN + "[6] Show Statistics" + Color.RESET);
        System.out.println(Color.GREEN + "[7] Exit" + Color.RESET);
        System.out.print("Select option: ");
    }

    private void addPlayer() {
        int number = getValidIntInput("Enter player number (0 to go back): ");
        if (number == 0) return;

        if (playerService.exists(number)) {
            System.out.println(Color.YELLOW + "Player with this number already exists." + Color.RESET);
            int goals = getValidIntInput("Enter goals to add: ");
            int assists = getValidIntInput("Enter assists to add: ");
            int games = getValidIntInput("Enter games to add: ");
            playerService.addOrUpdatePlayer(number, "", goals, assists, games);
        } else {
            System.out.print("Enter player full name: ");
            String name = scanner.nextLine();
            int goals = getValidIntInput("Enter goals: ");
            int assists = getValidIntInput("Enter assists: ");
            int games = getValidIntInput("Enter games: ");
            playerService.addOrUpdatePlayer(number, name, goals, assists, games);
        }

        System.out.println(Color.GREEN + "Player stats updated!" + Color.RESET);
    }

    private void deletePlayer() {
        int number = getValidIntInput("Enter player number to delete (0 to go back): ");
        if (number == 0) return;

        if (playerService.deletePlayer(number)) {
            System.out.println(Color.GREEN + "Player deleted successfully!" + Color.RESET);
        } else {
            System.out.println(Color.RED + "Player not found!" + Color.RESET);
        }
    }

    private void showPlayers() {
        displayPlayers(playerService.getPlayers());
    }

    private void displayPlayers(List<Player> players) {
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
                "+-------+---------+-------+-------+";
        String header = String.format("| %-4s | %-" + maxNameLength + "s | %-5s | %-7s | %-5s | %-5s |",
                "Num", "Name", "Goals", "Assists", "Games", "Total");

        System.out.println(Color.BLUE + divider + Color.RESET);
        System.out.println(Color.BLUE + header + Color.RESET);
        System.out.println(Color.BLUE + divider + Color.RESET);

        for (Player p : players) {
            String rowFormat = String.format("| %-4d | %-" + maxNameLength + "s | %-5d | %-7d | %-5d | %-5d |",
                    p.getNumber(), p.getName(), p.getGoals(), p.getAssists(), p.getGames(), p.getTotalPoints());
            System.out.println(rowFormat);
        }

        System.out.println(Color.BLUE + divider + Color.RESET);
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    private void sortPlayers() {
        System.out.println(Color.CYAN + "=== SORT PLAYERS ===" + Color.RESET);
        System.out.println(Color.GREEN + "[1] Sort by Number" + Color.RESET);
        System.out.println(Color.GREEN + "[2] Sort by Name" + Color.RESET);
        System.out.println(Color.GREEN + "[3] Sort by Goals" + Color.RESET);
        System.out.println(Color.GREEN + "[4] Sort by Assists" + Color.RESET);
        System.out.println(Color.GREEN + "[5] Sort by Games" + Color.RESET);
        System.out.println(Color.GREEN + "[6] Sort by Total Points" + Color.RESET);
        System.out.println(Color.GREEN + "[7] Advanced Sort (Multiple Criteria)" + Color.RESET);
        System.out.println(Color.GREEN + "[0] Back" + Color.RESET);
        System.out.print("Select option: ");
        
        int choice = getValidIntInput("");
        if (choice == 0) return;
        
        if (choice >= 1 && choice <= 6) {
            System.out.println(Color.GREEN + "[1] Ascending" + Color.RESET);
            System.out.println(Color.GREEN + "[2] Descending" + Color.RESET);
            System.out.print("Select order: ");
            int orderChoice = getValidIntInput("");
            boolean ascending = orderChoice == 1;
            
            SortCriteria criteria = SortCriteria.NUMBER;
            switch (choice) {
                case 1 -> criteria = SortCriteria.NUMBER;
                case 2 -> criteria = SortCriteria.NAME;
                case 3 -> criteria = SortCriteria.GOALS;
                case 4 -> criteria = SortCriteria.ASSISTS;
                case 5 -> criteria = SortCriteria.GAMES;
                case 6 -> criteria = SortCriteria.TOTAL_POINTS;
            }
            
            List<Player> sortedPlayers = playerService.sortPlayers(criteria, ascending);
            displayPlayers(sortedPlayers);
        } else if (choice == 7) {
            System.out.println(Color.CYAN + "=== PRIMARY SORT CRITERIA ===" + Color.RESET);
            System.out.println(Color.GREEN + "[1] Number" + Color.RESET);
            System.out.println(Color.GREEN + "[2] Name" + Color.RESET);
            System.out.println(Color.GREEN + "[3] Goals" + Color.RESET);
            System.out.println(Color.GREEN + "[4] Assists" + Color.RESET);
            System.out.println(Color.GREEN + "[5] Games" + Color.RESET);
            System.out.println(Color.GREEN + "[6] Total Points" + Color.RESET);
            int primaryChoice = getValidIntInput("Select primary criteria: ");
            
            System.out.println(Color.GREEN + "[1] Ascending" + Color.RESET);
            System.out.println(Color.GREEN + "[2] Descending" + Color.RESET);
            int primaryOrderChoice = getValidIntInput("Select primary order: ");
            boolean primaryAscending = primaryOrderChoice == 1;
            
            System.out.println(Color.CYAN + "=== SECONDARY SORT CRITERIA ===" + Color.RESET);
            System.out.println(Color.GREEN + "[1] Number" + Color.RESET);
            System.out.println(Color.GREEN + "[2] Name" + Color.RESET);
            System.out.println(Color.GREEN + "[3] Goals" + Color.RESET);
            System.out.println(Color.GREEN + "[4] Assists" + Color.RESET);
            System.out.println(Color.GREEN + "[5] Games" + Color.RESET);
            System.out.println(Color.GREEN + "[6] Total Points" + Color.RESET);
            int secondaryChoice = getValidIntInput("Select secondary criteria: ");
            
            System.out.println(Color.GREEN + "[1] Ascending" + Color.RESET);
            System.out.println(Color.GREEN + "[2] Descending" + Color.RESET);
            int secondaryOrderChoice = getValidIntInput("Select secondary order: ");
            boolean secondaryAscending = secondaryOrderChoice == 1;
            
            SortCriteria primaryCriteria = getSortCriteriaFromChoice(primaryChoice);
            SortCriteria secondaryCriteria = getSortCriteriaFromChoice(secondaryChoice);
            
            List<Player> sortedPlayers = playerService.sortPlayers(primaryCriteria, primaryAscending, 
                                                                secondaryCriteria, secondaryAscending);
            displayPlayers(sortedPlayers);
        }
    }
    
    private SortCriteria getSortCriteriaFromChoice(int choice) {
        return switch (choice) {
            case 1 -> SortCriteria.NUMBER;
            case 2 -> SortCriteria.NAME;
            case 3 -> SortCriteria.GOALS;
            case 4 -> SortCriteria.ASSISTS;
            case 5 -> SortCriteria.GAMES;
            case 6 -> SortCriteria.TOTAL_POINTS;
            default -> SortCriteria.NUMBER;
        };
    }
    
    private void filterPlayers() {
        System.out.println(Color.CYAN + "=== FILTER PLAYERS ===" + Color.RESET);
        System.out.println(Color.GREEN + "[1] Filter by Name" + Color.RESET);
        System.out.println(Color.GREEN + "[2] Filter by Number" + Color.RESET);
        System.out.println(Color.GREEN + "[3] Filter by Goals" + Color.RESET);
        System.out.println(Color.GREEN + "[4] Filter by Assists" + Color.RESET);
        System.out.println(Color.GREEN + "[5] Filter by Games" + Color.RESET);
        System.out.println(Color.GREEN + "[6] Filter by Total Points" + Color.RESET);
        System.out.println(Color.GREEN + "[0] Back" + Color.RESET);
        System.out.print("Select option: ");
        
        int choice = getValidIntInput("");
        if (choice == 0) return;
        
        if (choice == 1) {
            System.out.print("Enter name to search: ");
            String searchText = scanner.nextLine();
            List<Player> filteredPlayers = playerService.searchByName(searchText);
            displayPlayers(filteredPlayers);
        } else if (choice == 2) {
            int number = getValidIntInput("Enter player number: ");
            List<Player> filteredPlayers = playerService.filterPlayers(FilterCriteria.NUMBER, String.valueOf(number));
            displayPlayers(filteredPlayers);
        } else if (choice >= 3 && choice <= 6) {
            NumericFilterCriteria criteria = switch (choice) {
                case 3 -> NumericFilterCriteria.GOALS;
                case 4 -> NumericFilterCriteria.ASSISTS;
                case 5 -> NumericFilterCriteria.GAMES;
                case 6 -> NumericFilterCriteria.TOTAL_POINTS;
                default -> NumericFilterCriteria.GOALS;
            };
            
            System.out.println(Color.CYAN + "=== COMPARISON OPERATOR ===" + Color.RESET);
            System.out.println(Color.GREEN + "[1] Equal to (=)" + Color.RESET);
            System.out.println(Color.GREEN + "[2] Greater than (>)" + Color.RESET);
            System.out.println(Color.GREEN + "[3] Less than (<)" + Color.RESET);
            System.out.println(Color.GREEN + "[4] Greater than or equal to (>=)" + Color.RESET);
            System.out.println(Color.GREEN + "[5] Less than or equal to (<=)" + Color.RESET);
            int opChoice = getValidIntInput("Select operator: ");
            
            ComparisonOperator operator = switch (opChoice) {
                case 1 -> ComparisonOperator.EQUAL;
                case 2 -> ComparisonOperator.GREATER_THAN;
                case 3 -> ComparisonOperator.LESS_THAN;
                case 4 -> ComparisonOperator.GREATER_THAN_OR_EQUAL;
                case 5 -> ComparisonOperator.LESS_THAN_OR_EQUAL;
                default -> ComparisonOperator.EQUAL;
            };
            
            int value = getValidIntInput("Enter value: ");
            List<Player> filteredPlayers = playerService.filterPlayersNumeric(criteria, value, operator);
            displayPlayers(filteredPlayers);
        }
    }
    
    private void showStatistics() {
        System.out.println(Color.CYAN + "=== PLAYER STATISTICS ===" + Color.RESET);
        
        if (playerService.getPlayers().isEmpty()) {
            System.out.println(Color.RED + "No players found for statistics!" + Color.RESET);
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        System.out.println(Color.GREEN + "[1] Count players with goals in range" + Color.RESET);
        System.out.println(Color.GREEN + "[2] Count players with assists in range" + Color.RESET);
        System.out.println(Color.GREEN + "[3] Count players with games in range" + Color.RESET);
        System.out.println(Color.GREEN + "[4] Count players with total points in range" + Color.RESET);
        System.out.println(Color.GREEN + "[5] Show average statistics" + Color.RESET);
        System.out.println(Color.GREEN + "[0] Back" + Color.RESET);
        System.out.print("Select option: ");
        
        int choice = getValidIntInput("");
        if (choice == 0) return;
        
        if (choice >= 1 && choice <= 4) {
            NumericFilterCriteria criteria = switch (choice) {
                case 1 -> NumericFilterCriteria.GOALS;
                case 2 -> NumericFilterCriteria.ASSISTS;
                case 3 -> NumericFilterCriteria.GAMES;
                case 4 -> NumericFilterCriteria.TOTAL_POINTS;
                default -> NumericFilterCriteria.GOALS;
            };
            
            String criteriaName = switch (criteria) {
                case GOALS -> "goals";
                case ASSISTS -> "assists";
                case GAMES -> "games";
                case TOTAL_POINTS -> "total points";
                default -> "stats";
            };
            
            int minValue = getValidIntInput("Enter minimum " + criteriaName + ": ");
            int maxValue = getValidIntInput("Enter maximum " + criteriaName + ": ");
            
            int count = playerService.countPlayersInRange(criteria, minValue, maxValue);
            
            System.out.println(Color.YELLOW + "Number of players with " + criteriaName + 
                              " between " + minValue + " and " + maxValue + ": " + count + Color.RESET);
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        } else if (choice == 5) {
            System.out.println(Color.CYAN + "=== AVERAGE STATISTICS ===" + Color.RESET);
            double avgGoals = playerService.calculateAverage(NumericFilterCriteria.GOALS);
            double avgAssists = playerService.calculateAverage(NumericFilterCriteria.ASSISTS);
            double avgGames = playerService.calculateAverage(NumericFilterCriteria.GAMES);
            double avgPoints = playerService.calculateAverage(NumericFilterCriteria.TOTAL_POINTS);
            
            System.out.println(Color.YELLOW + "Average Goals: " + String.format("%.2f", avgGoals) + Color.RESET);
            System.out.println(Color.YELLOW + "Average Assists: " + String.format("%.2f", avgAssists) + Color.RESET);
            System.out.println(Color.YELLOW + "Average Games: " + String.format("%.2f", avgGames) + Color.RESET);
            System.out.println(Color.YELLOW + "Average Total Points: " + String.format("%.2f", avgPoints) + Color.RESET);
            
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    private int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Invalid number. Please enter a valid integer." + Color.RESET);
            }
        }
    }
}