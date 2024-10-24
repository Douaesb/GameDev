package com.gameDev.consoleUI;

import com.gameDev.entity.Team;
import com.gameDev.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

public class TeamUI {

    private static final Logger logger = LoggerFactory.getLogger(TeamUI.class);
    private final TeamService teamService;
    private final Scanner scanner = new Scanner(System.in);

    public TeamUI(ApplicationContext context) {
        // Retrieve TeamService bean from application context
        this.teamService = (TeamService) context.getBean("teamService");
    }

    public void start() {
        int choice;
        do {
            printMenu();
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    createTeam();
                    break;
                case 2:
                    findTeamById();
                    break;
                case 3:
                    listAllTeams();
                    break;
                case 4:
                    updateTeam();
                    break;
                case 5:
                    deleteTeam();
                    break;
                case 6:
                    changePlayerTeam();
                    break;
                case 7:
                    System.out.println("Exiting Team Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 7);
    }

    private void printMenu() {
        System.out.println("===== Team Management Menu =====");
        System.out.println("1. Create Team");
        System.out.println("2. Find Team by ID");
        System.out.println("3. List All Teams");
        System.out.println("4. Update Team");
        System.out.println("5. Delete Team");
        System.out.println("6. Change Player's Team");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private void createTeam() {
        System.out.println("===== Create New Team =====");
        System.out.print("Enter team name: ");
        String name = scanner.nextLine();

        System.out.print("Enter team rank: ");
        int rank = Integer.parseInt(scanner.nextLine());

        Team newTeam = new Team(name, rank);
        teamService.createTeam(newTeam);

        System.out.println("Team created successfully.");
        logger.info("Created new team: {}", newTeam);
    }

    private void findTeamById() {
        System.out.print("Enter team ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Team team = teamService.getTeamById(id);
        if (team != null) {
            System.out.println("Team found: " + team);
        } else {
            System.out.println("Team with ID " + id + " not found.");
        }
    }

    private void listAllTeams() {
        System.out.println("===== List of All Teams =====");
        List<Team> teams = teamService.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams found.");
        } else {
            teams.forEach(System.out::println);
        }
    }

    private void updateTeam() {
        System.out.print("Enter the ID of the team you want to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Team team = teamService.getTeamById(id);
        if (team == null) {
            System.out.println("Team with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new team name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new team rank: ");
        int rank = Integer.parseInt(scanner.nextLine());

        team.setName(name);
        team.setRank(rank);

        teamService.updateTeam(team);
        System.out.println("Team updated successfully.");
        logger.info("Updated team: {}", team);
    }

    private void deleteTeam() {
        System.out.print("Enter the ID of the team you want to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        teamService.deleteTeam(id);
        System.out.println("Team deleted successfully.");
        logger.info("Deleted team with ID: {}", id);
    }

    private void changePlayerTeam() {
        System.out.print("Enter player ID: ");
        int playerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the new team ID: ");
        int newTeamId = Integer.parseInt(scanner.nextLine());

        teamService.changePlayerTeam(playerId, newTeamId);
        System.out.println("Player's team changed successfully.");
        logger.info("Changed player ID {} to team ID {}", playerId, newTeamId);
    }
}
