package com.gameDev.consoleUI;

import org.springframework.context.ApplicationContext;

import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);
    private PlayerUI playerUI;
    private GameUI gameUI;
    private TeamUI teamUI;
    private TournamentUI tournamentUI;

    public ConsoleUI(ApplicationContext context) {
        this.gameUI = new GameUI(context);
        this.playerUI = new PlayerUI(context);
        this.teamUI = new TeamUI(context);
        this.tournamentUI = new TournamentUI(context);
    }

    public void start() {
        int choice;
        do {
            System.out.println("\nMain Menu - Tournament Management System");
            System.out.println("1. Manage Players");
            System.out.println("2. Manage Games");
            System.out.println("3. Manage Teams");
            System.out.println("4. Manage Tournaments");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    playerUI.start();  // Calls the Player management UI
                    break;
                case 2:
                    gameUI.start();  // Calls the Game management UI
                    break;
                case 3:
                    teamUI.start();  // Calls the Team management UI
                    break;
                case 4:
                    tournamentUI.start();  // Calls the Tournament management UI
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (choice != 5);
    }

}
