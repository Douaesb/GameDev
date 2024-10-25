package com.gameDev.consoleUI;

import com.gameDev.entity.Game;
import com.gameDev.entity.Team;
import com.gameDev.entity.Tournament;
import com.gameDev.entity.enums.Status;
import com.gameDev.service.GameService;
import com.gameDev.service.TeamService;
import com.gameDev.service.TournamentService;
import com.gameDev.util.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.util.List;

public class TournamentUI {
    private static final Logger logger = LoggerFactory.getLogger(TournamentUI.class);
    private final TournamentService tournamentService;
    private final TeamService teamService;

    private final GameService gameService;

    public TournamentUI(ApplicationContext context) {
        this.tournamentService = (TournamentService) context.getBean("tournamentService");
        this.gameService = (GameService) context.getBean("gameService");
        this.teamService = (TeamService) context.getBean("teamService");

    }

    public void start() {
        int choice;
        do {
            System.out.println("\nTournament Management");
            System.out.println("1. Create Tournament");
            System.out.println("2. View All Tournaments");
            System.out.println("3. View Tournament by ID");
            System.out.println("4. Update Tournament");
            System.out.println("5. Delete Tournament");
            System.out.println("6. Assign Team to Tournament");
            System.out.println("7. Remove Team from Tournament");
            System.out.println("8. Calculate Estimated Tournament Duration");
            System.out.println("9. Exit");

            choice = InputValidator.validatePositiveInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    createTournament();
                    break;
                case 2:
                    viewAllTournaments();
                    break;
                case 3:
                    viewTournamentById();
                    break;
                case 4:
                    updateTournament();
                    break;
                case 5:
                    deleteTournament();
                    break;
                case 6:
                    assignTeamToTournament();
                    break;
                case 7:
                    removeTeamFromTournament();
                    break;
                case 8:
                    calculateEstimatedDuration();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (choice != 9);
    }

    private void createTournament() {
        Tournament tournament = new Tournament();

        tournament.setTitle(InputValidator.validateString("Enter tournament title: "));
        tournament.setStartDate(InputValidator.validateDate("Enter start date"));
        tournament.setEndDate(InputValidator.validateEndDateAfterStartDate(tournament.getStartDate(), "Enter end date"));
        tournament.setNumSpectators(InputValidator.validatePositiveInteger("Enter number of spectators: "));
        tournament.setDuration(InputValidator.validatePositiveInteger("Enter duration (in hours): "));
        tournament.setBreakTime(InputValidator.validatePositiveInteger("Enter break time (in minutes): "));
        tournament.setCeremonyTime(InputValidator.validatePositiveInteger("Enter ceremony time (in minutes): "));
        Game game = selectGame();

        if (game != null) {
            tournament.setGame(game);
        }
        tournament.setStatus(readStatus());

        tournamentService.createTournament(tournament);
        System.out.println("Tournament created successfully!");
    }

    private void viewAllTournaments() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments available.");
        } else {
            tournaments.forEach(this::displayTournament);
        }
    }

    private void viewTournamentById() {
        int id = InputValidator.validatePositiveInteger("Enter tournament ID: ");
        Tournament tournament = getTournamentIfExists(id);
        if (tournament != null) {
            displayTournament(tournament);
        }
    }

    private void updateTournament() {
        int id = InputValidator.validatePositiveInteger("Enter tournament ID to update: ");
        Tournament tournament = getTournamentIfExists(id);
        if (tournament != null) {
            System.out.println("Updating details for tournament: " + tournament.getTitle());
            tournament.setTitle(InputValidator.validateString("Enter new title or press Enter to keep [" + tournament.getTitle() + "]: "));
            tournament.setStartDate(InputValidator.validateDate("Enter new start date or press Enter to keep [" + tournament.getStartDate() + "]: "));
            tournament.setEndDate(InputValidator.validateEndDateAfterStartDate(tournament.getStartDate(), "Enter new end date"));
            tournament.setNumSpectators(InputValidator.validatePositiveInteger("Enter new number of spectators: "));
            tournament.setDuration(InputValidator.validatePositiveInteger("Enter new duration (in hours): "));
            tournament.setBreakTime(InputValidator.validatePositiveInteger("Enter new break time (in minutes): "));
            tournament.setCeremonyTime(InputValidator.validatePositiveInteger("Enter new ceremony time (in minutes): "));
            tournament.setStatus(readStatus());
            Game game = selectGame();
            if (game != null) {
                tournament.setGame(game);
            }
            tournamentService.updateTournament(tournament);
            System.out.println("Tournament updated successfully!");
        }
    }

    private void deleteTournament() {
        int id = InputValidator.validatePositiveInteger("Enter tournament ID to delete: ");
        Tournament tournament = getTournamentIfExists(id);
        if (tournament != null) {
            tournamentService.deleteTournament(id);
            System.out.println("Tournament deleted successfully.");
        }
    }

    private void assignTeamToTournament() {
        int tournamentId = InputValidator.validatePositiveInteger("Enter tournament ID: ");
        int teamId = InputValidator.validatePositiveInteger("Enter team ID to assign: ");
        if (getTournamentIfExists(tournamentId) != null && getTeamIfExists(teamId) != null) {
            tournamentService.assignTeamToTournament(tournamentId, teamId);
            System.out.println("Team assigned to tournament successfully.");
        }
    }

    private void removeTeamFromTournament() {
        int tournamentId = InputValidator.validatePositiveInteger("Enter tournament ID: ");
        int teamId = InputValidator.validatePositiveInteger("Enter team ID to remove: ");
        if (getTournamentIfExists(tournamentId) != null && getTeamIfExists(teamId) != null) {
            tournamentService.removeTeamFromTournament(tournamentId, teamId);
            System.out.println("Team removed from tournament successfully.");
        }
    }

    private void calculateEstimatedDuration() {
        int tournamentId = InputValidator.validatePositiveInteger("Enter Tournament ID: ");
        Tournament tournament = getTournamentIfExists(tournamentId);
        if (tournament != null) {
            double estimatedDuration = tournamentService.getEstimatedDurationTournament(tournamentId);
            System.out.printf("The estimated duration for the tournament is: %.2f hours\n", estimatedDuration);
        }
    }

    private Tournament getTournamentIfExists(int tournamentId) {
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        if (tournament == null) {
            System.out.println("Tournament not found.");
        }
        return tournament;
    }

    private Team getTeamIfExists(int teamId) {
        Team team = teamService.getTeamById(teamId);
        if (team == null) {
            System.out.println("Tournament not found.");
        }
        return team;
    }

    private void displayTournament(Tournament tournament) {
        System.out.println("\nTournament ID: " + tournament.getId());
        System.out.println("Title: " + tournament.getTitle());
        System.out.println("Start Date: " + tournament.getStartDate());
        System.out.println("End Date: " + tournament.getEndDate());
        System.out.println("Number of Spectators: " + tournament.getNumSpectators());
        System.out.println("Duration: " + tournament.getDuration() + " hours");
        System.out.println("Break Time: " + tournament.getBreakTime() + " minutes");
        System.out.println("Ceremony Time: " + tournament.getCeremonyTime() + " minutes");
        System.out.println("Status: " + tournament.getStatus());
        System.out.println("Game: " + tournament.getGame());
    }

    private Status readStatus() {
        while (true) {
            try {
                return Status.valueOf(InputValidator.validateString("Enter tournament status (PLANNED, ONGOING, FINISHED, CANCELLED): ").toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter one of PLANNED, ONGOING, FINISHED, CANCELLED.");
            }
        }
    }

    private Game selectGame() {
        List<Game> games = gameService.findAllGames();
        if (games.isEmpty()) {
            System.out.println("No games available. Please add a game first.");
            return null;
        }

        System.out.println("Available Games:");
        for (int i = 0; i < games.size(); i++) {
            System.out.printf("%d. %s (Difficulty: %.2f, Avg Match Duration: %.2f)\n",
                    i + 1, games.get(i).getName(), games.get(i).getDifficulty(), games.get(i).getMatchAvgDuration());
        }

        int choice = InputValidator.validatePositiveInteger("Select a game by number: ");
        if (choice < 1 || choice > games.size()) {
            System.out.println("Invalid selection. No game updated.");
            return null;
        }

        return games.get(choice - 1);
    }
}
