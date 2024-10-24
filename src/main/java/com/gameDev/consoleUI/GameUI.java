package com.gameDev.consoleUI;

import com.gameDev.entity.Game;
import com.gameDev.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.util.List;
import java.util.Scanner;

public class GameUI {

    private static final Logger logger = LoggerFactory.getLogger(GameUI.class);
    private final GameService gameService;
    private final Scanner scanner = new Scanner(System.in);

    public GameUI(ApplicationContext context) {
        // Retrieve GameService bean from application context
        this.gameService = (GameService) context.getBean("gameService");
    }

    public void start() {
        int choice;
        do {
            printMenu();
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    createGame();
                    break;
                case 2:
                    findGameById();
                    break;
                case 3:
                    listAllGames();
                    break;
                case 4:
                    updateGame();
                    break;
                case 5:
                    deleteGame();
                    break;
                case 6:
                    System.out.println("Exiting Game Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 6);
    }

    private void printMenu() {
        System.out.println("===== Game Management Menu =====");
        System.out.println("1. Create Game");
        System.out.println("2. Find Game by ID");
        System.out.println("3. List All Games");
        System.out.println("4. Update Game");
        System.out.println("5. Delete Game");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private void createGame() {
        System.out.println("===== Create New Game =====");
        System.out.print("Enter game name: ");
        String name = scanner.nextLine();

        System.out.print("Enter game difficulty: ");
        double difficulty = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter average match duration: ");
        double duration = Double.parseDouble(scanner.nextLine());

        Game newGame = new Game(name, difficulty, duration);
        gameService.createGame(newGame);

        System.out.println("Game created successfully.");
        logger.info("Created new game: {}", newGame);
    }

    private void findGameById() {
        System.out.print("Enter game ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Game game = gameService.findGameById(id);
        if (game != null) {
            System.out.println("Game found: " + game);
        } else {
            System.out.println("Game with ID " + id + " not found.");
        }
    }

    private void listAllGames() {
        System.out.println("===== List of All Games =====");
        List<Game> games = gameService.findAllGames();
        if (games.isEmpty()) {
            System.out.println("No games found.");
        } else {
            games.forEach(System.out::println);
        }
    }

    private void updateGame() {
        System.out.print("Enter the ID of the game you want to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Game game = gameService.findGameById(id);
        if (game == null) {
            System.out.println("Game with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new game name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new game difficulty: ");
        double difficulty = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter new average match duration: ");
        double duration = Double.parseDouble(scanner.nextLine());

        game.setName(name);
        game.setDifficulty(difficulty);
        game.setMatchAvgDuration(duration);

        gameService.updateGame(game);
        System.out.println("Game updated successfully.");
        logger.info("Updated game: {}", game);
    }

    private void deleteGame() {
        System.out.print("Enter the ID of the game you want to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        gameService.deleteGameById(id);
        System.out.println("Game deleted successfully.");
        logger.info("Deleted game with ID: {}", id);
    }
}
