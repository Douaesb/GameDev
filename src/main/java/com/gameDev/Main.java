package com.gameDev;

import com.gameDev.entity.Game;
import com.gameDev.entity.Player;
import com.gameDev.entity.Team;
import com.gameDev.entity.Tournament;
import com.gameDev.entity.enums.Status;
import com.gameDev.service.GameService;
import com.gameDev.service.PlayerService;
import com.gameDev.service.TeamService;
import com.gameDev.service.TournamentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        TournamentService tournamentService = (TournamentService) context.getBean("tournamentService");
        TeamService teamService = (TeamService) context.getBean("teamService");
        PlayerService playerService = (PlayerService) context.getBean("playerService");
        GameService gameService = (GameService) context.getBean("gameService");

        // Create a game
        Game game = new Game();
        game.setName("pubg");
        game.setDifficulty(10.0); // Example difficulty
        game.setMatchAvgDuration(30.0); // Example average duration in minutes
        gameService.createGame(game); // Assuming a save method exists

        Team team = new Team("Team test",7);
        Team team2 = new Team("Team test2",5);

        teamService.createTeam(team);
        teamService.createTeam(team2);

        // Create players
        Player player1 = new Player("Player5", 20);
        Player player2 = new Player("Player8", 22);
        player1.setTeam(team);
        player2.setTeam(team);
        playerService.savePlayer(player1); // Assuming a save method exists
        playerService.savePlayer(player2); // Assuming a save method exists

        // Create a team

        // Create a tournament
        Tournament tournament = new Tournament();
        tournament.setTitle("Summer Tournament");
        tournament.setGame(game);
        tournament.setStartDate(LocalDate.now());
        tournament.setEndDate(LocalDate.now().plusDays(5));
        tournament.setStatus(Status.PLANNED);
        tournament.setBreakTime(10); // Break time in minutes
        tournament.setCeremonyTime(15); // Ceremony time in minutes

        // Save the tournament
        tournamentService.createTournament(tournament); // Assuming a save method exists

        tournamentService.assignTeamToTournament(tournament.getId(), team.getId());
        tournamentService.assignTeamToTournament(tournament.getId(), team2.getId());

        // Now calculate estimated duration using the base calculation
        double estimatedDuration = tournamentService.getEstimatedDurationTournament(tournament.getId());
        System.out.println("Estimated Duration : " + estimatedDuration + " minutes");

    }
}
