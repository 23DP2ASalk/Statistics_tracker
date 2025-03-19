package lv.rvt;

import java.util.Scanner;

public class ConsoleUI {
    private PlayerService playerService;
    private Scanner scanner;

    public ConsoleUI(PlayerService service) {
        this.playerService = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Statistics Tracker ---");
            System.out.println("1. Pievienot spēlētāju");
            System.out.println("2. Skatīt visus spēlētājus");
            System.out.println("3. Atjaunināt statistiku");
            System.out.println("4. Beigt");
            System.out.print("Izvēlies: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addPlayer();
                case 2 -> listPlayers();
                case 3 -> updateStats();
                case 4 -> {
                    System.out.println("👋 Programma beidzas.");
                    return;
                }
                default -> System.out.println("❌ Nepareiza izvēle!");
            }
        }
    }

    private void addPlayer() {
        System.out.print("Vārds: ");
        String name = scanner.nextLine();
        System.out.print("Numurs: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        playerService.addPlayer(new Player(name, number));
        System.out.println("✅ Spēlētājs pievienots!");
    }

    private void listPlayers() {
        for (Player p : playerService.getPlayers()) {
            System.out.println(p);
        }
    }

    private void updateStats() {
        System.out.print("Ievadi spēlētāja numuru: ");
        int number = scanner.nextInt();
        Player player = playerService.findByNumber(number);

        if (player != null) {
            System.out.println("1. Pievienot vārtus");
            System.out.println("2. Pievienot piespēli");
            int choice = scanner.nextInt();

            if (choice == 1) player.addGoal();
            else if (choice == 2) player.addAssist();

            playerService.saveToFile();
            System.out.println("✅ Statistika atjaunināta!");
        } else {
            System.out.println("❌ Spēlētājs nav atrasts.");
        }
    }
}
