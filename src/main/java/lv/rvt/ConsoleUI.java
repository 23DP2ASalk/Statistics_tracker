package lv.rvt;

import java.util.Scanner;

public class ConsoleUI {
    private final PlayerService service;
    private final Scanner scanner;

    public ConsoleUI(PlayerService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        printAsciiArt();
        while (true) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addPlayer();
                case 2 -> listPlayers();
                case 3 -> saveAndExit();
                default -> System.out.println("Nepareiza izvēle.");
            }
        }
    }

    private void printAsciiArt() {
        System.out.println("""
                 _____ _        _   _       _   _             
                / ____| |      | | (_)     | | (_)            
               | (___ | |_ __ _| |_ _  __ _| |_ _  ___  _ __  
                \\___ \\| __/ _` | __| |/ _` | __| |/ _ \\| '_ \\ 
                ____) | || (_| | |_| | (_| | |_| | (_) | | | |
               |_____/ \\__\\__,_|\\__|_|\\__,_|\\__|_|\\___/|_| |_|
                                                  
                      Statistikas Pārvaldības Sistēma
               """);
    }

    private void printMenu() {
        System.out.println("""
                1. Pievienot spēlētāju
                2. Rādīt visus spēlētājus
                3. Saglabāt un iziet
                """);
        System.out.print("Izvēlies darbību: ");
    }

    private void addPlayer() {
        System.out.print("Spēlētāja vārds: ");
        String name = scanner.nextLine();
        System.out.print("Vārtu skaits: ");
        int goals = Integer.parseInt(scanner.nextLine());
        System.out.print("Rezultativitātes piespēles: ");
        int assists = Integer.parseInt(scanner.nextLine());
        System.out.print("Spēļu skaits: ");
        int games = Integer.parseInt(scanner.nextLine());

        Player player = new Player(name, goals, assists, games);
        service.addPlayer(player);
        System.out.println("Spēlētājs pievienots.");
    }

    private void listPlayers() {
        for (Player player : service.getAllPlayers()) {
            System.out.println(player);
        }
    }

    private void saveAndExit() {
        service.savePlayers();
        System.out.println("Dati saglabāti. Atā!");
        System.exit(0);
    }
}
