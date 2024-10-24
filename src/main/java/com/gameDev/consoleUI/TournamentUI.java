package com.gameDev.consoleUI;

import com.gameDev.entity.Game;
import com.gameDev.entity.Team;
import com.gameDev.entity.Tournament;
import com.gameDev.entity.enums.Status;
import com.gameDev.service.GameService;
import com.gameDev.service.TeamService;
import com.gameDev.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TournamentUI {
    private static final Logger logger = LoggerFactory.getLogger(TournamentUI.class);
    private final TournamentService tournamentService;
    private final GameService gameService;

    private final Scanner scanner = new Scanner(System.in);

    public TournamentUI(ApplicationContext context) {
        this.tournamentService = (TournamentService) context.getBean("tournamentService");
        this.gameService = (GameService) context.getBean("gameService");
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

            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

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

        System.out.print("Enter tournament title: ");
        tournament.setTitle(scanner.nextLine());

        tournament.setStartDate(readDate("start date"));
        tournament.setEndDate(readDate("end date"));

        System.out.print("Enter number of spectators: ");
        tournament.setNumSpectators(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter duration (in hours): ");
        tournament.setDuration(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter break time (in minutes): ");
        tournament.setBreakTime(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter ceremony time (in minutes): ");
        tournament.setCeremonyTime(Integer.parseInt(scanner.nextLine()));

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
        System.out.print("Enter tournament ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Tournament tournament = tournamentService.getTournamentById(id);
        if (tournament != null) {
            displayTournament(tournament);
        } else {
            System.out.println("Tournament not found.");
        }
    }

    private void updateTournament() {
        System.out.print("Enter tournament ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Tournament tournament = tournamentService.getTournamentById(id);
        if (tournament != null) {
            System.out.println("Updating details for tournament: " + tournament.getTitle());
            tournament.setTitle(promptForUpdate("title", tournament.getTitle()));
            tournament.setStartDate(readDate("start date", tournament.getStartDate()));
            tournament.setEndDate(readDate("end date", tournament.getEndDate()));
            tournament.setNumSpectators(promptForUpdate("number of spectators", tournament.getNumSpectators()));
            tournament.setDuration(promptForUpdate("duration", tournament.getDuration()));
            tournament.setBreakTime(promptForUpdate("break time", tournament.getBreakTime()));
            tournament.setCeremonyTime(promptForUpdate("ceremony time", tournament.getCeremonyTime()));

            tournament.setStatus(readStatus());
            Game game = selectGame();
            if (game != null) {
                tournament.setGame(game);
            }
            tournamentService.updateTournament(tournament);
            System.out.println("Tournament updated successfully!");
        } else {
            System.out.println("Tournament not found.");
        }
    }

    private void deleteTournament() {
        System.out.print("Enter tournament ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        tournamentService.deleteTournament(id);
        System.out.println("Tournament deleted successfully.");
    }

    private void assignTeamToTournament() {
        System.out.print("Enter tournament ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter team ID to assign: ");
        int teamId = Integer.parseInt(scanner.nextLine());

        tournamentService.assignTeamToTournament(tournamentId, teamId);
        System.out.println("Team assigned to tournament successfully.");
    }

    private void removeTeamFromTournament() {
        System.out.print("Enter tournament ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter team ID to remove: ");
        int teamId = Integer.parseInt(scanner.nextLine());

        tournamentService.removeTeamFromTournament(tournamentId, teamId);
        System.out.println("Team removed from tournament successfully.");
    }

    private void calculateEstimatedDuration() {
        System.out.print("Enter Tournament ID: ");
        int tournamentId = Integer.parseInt(scanner.nextLine());

        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        if (tournament != null) {
            double estimatedDuration = tournamentService.getEstimatedDurationTournament(tournamentId);
            System.out.printf("The estimated duration for the tournament is: %.2f hours\n", estimatedDuration);
        } else {
            System.out.println("Tournament not found.");
        }

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

    private LocalDate readDate(String field) {
        while (true) {
            System.out.print("Enter " + field + " (YYYY-MM-DD): ");
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    private LocalDate readDate(String field, LocalDate current) {
        System.out.print("Enter " + field + " (YYYY-MM-DD) [" + current + "]: ");
        String input = scanner.nextLine();
        return input.isEmpty() ? current : LocalDate.parse(input);
    }

    private Status readStatus() {
        while (true) {
            System.out.print("Enter tournament status (PLANNED, ONGOING, FINISHED, CANCELLED): ");
            try {
                return Status.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter one of PLANNED, ONGOING, FINISHED, CANCELLED.");
            }
        }
    }

    private String promptForUpdate(String field, String currentValue) {
        System.out.print("Enter " + field + " [" + currentValue + "]: ");
        String input = scanner.nextLine();
        return input.isEmpty() ? currentValue : input;
    }

    private int promptForUpdate(String field, int currentValue) {
        System.out.print("Enter " + field + " [" + currentValue + "]: ");
        String input = scanner.nextLine();
        return input.isEmpty() ? currentValue : Integer.parseInt(input);
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

        System.out.print("Select a game by number: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 1 || choice > games.size()) {
            System.out.println("Invalid selection. No game updated.");
            return null;
        }

        return games.get(choice - 1);
    }

}
