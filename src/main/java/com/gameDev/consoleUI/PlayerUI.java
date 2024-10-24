package com.gameDev.consoleUI;

import com.gameDev.entity.Team;
import com.gameDev.entity.Player;
import com.gameDev.entity.Team;
import com.gameDev.service.PlayerService;
import com.gameDev.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

public class PlayerUI {

    private static final Logger logger = LoggerFactory.getLogger(PlayerUI.class);
    private final PlayerService playerService;
    private final TeamService teamService;
    private final Scanner scanner = new Scanner(System.in);

    public PlayerUI(ApplicationContext context) {
        // Retrieve PlayerService and TeamService beans from application context
        this.playerService = (PlayerService) context.getBean("playerService");
        this.teamService = (TeamService) context.getBean("teamService");
    }

    public void start() {
        int choice;
        do {
            printMenu();
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    createPlayer();
                    break;
                case 2:
                    findPlayerById();
                    break;
                case 3:
                    listAllPlayers();
                    break;
                case 4:
                    updatePlayer();
                    break;
                case 5:
                    deletePlayer();
                    break;
                case 6:
                    System.out.println("Exiting Player Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 6);
    }

    private void printMenu() {
        System.out.println("===== Player Management Menu =====");
        System.out.println("1. Create Player");
        System.out.println("2. Find Player by ID");
        System.out.println("3. List All Players");
        System.out.println("4. Update Player");
        System.out.println("5. Delete Player");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private void createPlayer() {
        System.out.println("===== Create New Player =====");

        System.out.print("Enter player nickname: ");
        String nickName = scanner.nextLine().trim();

        System.out.print("Enter player age: ");
        int age;
        while (true) {
            try {
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid age. Please enter a positive integer for age: ");
            }
        }

        Player newPlayer = new Player(nickName, age);

        Team team = selectTeam();

        if (team != null) {
            newPlayer.setTeam(team);
        } else {
            System.out.println("No team assigned to player.");
        }

        playerService.savePlayer(newPlayer);

        System.out.println("Player created successfully.");
        logger.info("Created new player: {}", newPlayer);
    }

    private void findPlayerById() {
        System.out.print("Enter player ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Player player = playerService.getPlayerById(id);
        if (player != null) {
            System.out.println("Player found: " + player);
        } else {
            System.out.println("Player with ID " + id + " not found.");
        }
    }

    private void listAllPlayers() {
        System.out.println("===== List of All Players =====");
        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found.");
        } else {
            players.forEach(System.out::println);
        }
    }

    private void updatePlayer() {
        System.out.print("Enter the ID of the player you want to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Player player = playerService.getPlayerById(id);
        if (player == null) {
            System.out.println("Player with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new nickname: ");
        String nickName = scanner.nextLine();

        System.out.print("Enter new age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter new team ID for the player: ");
        int teamId = Integer.parseInt(scanner.nextLine());

        Team team = teamService.getTeamById(teamId);
        if (team == null) {
            System.out.println("Team with ID " + teamId + " not found.");
            return;
        }

        player.setNickName(nickName);
        player.setAge(age);
        player.setTeam(team);

        playerService.updatePlayer(player);
        System.out.println("Player updated successfully.");
        logger.info("Updated player: {}", player);
    }

    private void deletePlayer() {
        System.out.print("Enter the ID of the player you want to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        playerService.deletePlayer(id);
        System.out.println("Player deleted successfully.");
        logger.info("Deleted player with ID: {}", id);
    }

    private Team selectTeam() {
        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams available. Please add a team first.");
            return null;
        }

        System.out.println("Available Teams:");
        for (int i = 0; i < teams.size(); i++) {
            System.out.printf("%d. %s (rank: %d)\n",
                    i + 1, teams.get(i).getName(), teams.get(i).getRank());
        }

        System.out.print("Select a team by number: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 1 || choice > teams.size()) {
            System.out.println("Invalid selection. No team updated.");
            return null;
        }

        return teams.get(choice - 1);
    }

}
