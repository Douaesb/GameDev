package com.gameDev.consoleUI;

import com.gameDev.entity.Player;
import com.gameDev.entity.Team;
import com.gameDev.service.PlayerService;
import com.gameDev.service.TeamService;
import com.gameDev.util.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

public class TeamUI {

    private static final Logger logger = LoggerFactory.getLogger(TeamUI.class);
    private final TeamService teamService;
    private final PlayerService playerService;
    public TeamUI(ApplicationContext context) {
        // Retrieve TeamService bean from application context
        this.teamService = (TeamService) context.getBean("teamService");
        this.playerService = (PlayerService)  context.getBean("playerService");
    }

    public void start() {
        int choice;
        do {
            printMenu();
            choice = InputValidator.validatePositiveInteger("Choose an option: ");
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
    }

    private void createTeam() {
        System.out.println("===== Create New Team =====");

        List<String> existingNicknames = teamService.getAllTeams().stream()
                .map(Team::getName)
                .collect(Collectors.toList());

        String name = InputValidator.validateUniqueName("Enter team name: ", existingNicknames);

        int rank = InputValidator.validatePositiveInteger("Enter team rank: ");

        Team newTeam = new Team(name, rank);
        teamService.createTeam(newTeam);

        System.out.println("Team created successfully.");
        logger.info("Created new team: {}", newTeam);
    }

    private void findTeamById() {
        int id = InputValidator.validatePositiveInteger("Enter team ID: ");

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
        int id = InputValidator.validatePositiveInteger("Enter the ID of the team you want to update: ");

        Team team = teamService.getTeamById(id);
        if (team == null) {
            System.out.println("Team with ID " + id + " not found.");
            return;
        }

        List<String> existingNicknames = teamService.getAllTeams().stream()
                .map(Team::getName)
                .collect(Collectors.toList());


        String name = InputValidator.validateUniqueName("Enter team name: ", existingNicknames);

        int rank = InputValidator.validatePositiveInteger("Enter new team rank: ");

        team.setName(name);
        team.setRank(rank);

        teamService.updateTeam(team);
        System.out.println("Team updated successfully.");
        logger.info("Updated team: {}", team);
    }

    private void deleteTeam() {
        int id = InputValidator.validatePositiveInteger("Enter the ID of the team you want to delete: ");
        if (getTeamIfExists(id) != null) {
            teamService.deleteTeam(id);
            System.out.println("Team deleted successfully.");
            logger.info("Deleted team with ID: {}", id);
        }
    }

    private void changePlayerTeam() {
        int playerId = InputValidator.validatePositiveInteger("Enter player ID: ");
        int newTeamId = InputValidator.validatePositiveInteger("Enter the new team ID: ");
        if (getPlayerIfExists(playerId) != null && getTeamIfExists(newTeamId) != null) {
            teamService.changePlayerTeam(playerId, newTeamId);
            System.out.println("Player's team changed successfully.");
            logger.info("Changed player ID {} to team ID {}", playerId, newTeamId);
        }
    }

    private Team getTeamIfExists(int teamId) {
        Team team = teamService.getTeamById(teamId);
        if (team == null) {
            System.out.println("team not found.");
        }
        return team;
    }

    private Player getPlayerIfExists(int playerId) {
        Player player = playerService.getPlayerById(playerId);
        if (player == null) {
            System.out.println("player not found.");
        }
        return player;
    }
}
