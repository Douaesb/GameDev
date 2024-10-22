package com.gameDev;

import com.gameDev.entity.Game;
import com.gameDev.entity.Tournament;
import com.gameDev.entity.enums.Status;
import com.gameDev.service.GameService; // Make sure you have a service to manage games
import com.gameDev.service.TournamentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Load application context from XML file
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get the TournamentService and GameService beans
        TournamentService tournamentService = (TournamentService) context.getBean("tournamentService");
        GameService gameService = (GameService) context.getBean("gameService");

        // ===============================
        // 1. Creating or Fetching a Game
        // ===============================
        // Assuming you have a method in GameService to get a game by id or create a new one
        Game game = gameService.findGameById(1); // Change the ID as necessary
        if (game == null) {
            game = new Game("Sample Game","hard",6.0); // Create a new game if not found
            gameService.createGame(game); // Persist the new game
        }

        // ===============================
        // 2. Creating a new Tournament
        // ===============================
        Tournament newTournament = new Tournament(
                "Spring Championship",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 5),
                10000,
                8,
                2,
                1,
                Status.PLANNED
        );
        newTournament.setGame(game); // Set the game for the tournament
        tournamentService.createTournament(newTournament);
        System.out.println("Tournament created: " + newTournament);

        // ===============================
        // 2. Creating a new Tournament
        // ===============================
        Tournament newTournament2 = new Tournament(
                "Spring",
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 5),
                10000,
                8,
                2,
                1,
                Status.PLANNED
        );
        newTournament2.setGame(game); // Set the game for the tournament
        tournamentService.createTournament(newTournament2);
        System.out.println("Tournament created: " + newTournament2);
        // ===============================
        // 3. Fetching all Tournaments
        // ===============================
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        System.out.println("All Tournaments:");
        tournaments.forEach(System.out::println);




    }
}
