package com.gameDev;

import com.gameDev.entity.Game;
import com.gameDev.entity.Team;
import com.gameDev.entity.Tournament;
import com.gameDev.entity.enums.Status;
import com.gameDev.service.GameService;
import com.gameDev.service.TeamService;
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
        TeamService teamService = (TeamService) context.getBean("teamService");


        Tournament tournament = tournamentService.getTournamentById(2);
        Team team = teamService.getTeamById(1);
        Team team2 = teamService.getTeamById(2);


        // Assign team to tournament
        tournamentService.assignTeamToTournament(tournament.getId(), team.getId());
        tournamentService.assignTeamToTournament(tournament.getId(), team2.getId());

        System.out.println("Team assigned to tournament: " + team.getName());

        // Optionally, display the teams in the tournament
        System.out.println("Teams in tournament: " + tournament.getTeams());
    }
}

