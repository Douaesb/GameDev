package com.gameDev;

import com.gameDev.dao.TeamDao;
import com.gameDev.dao.impl.TeamDaoImpl;
import com.gameDev.entity.Game;
import com.gameDev.entity.Team;
import com.gameDev.service.GameService;
import com.gameDev.service.TeamService;
import com.gameDev.service.impl.TeamServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args)  {

        // Load application context from XML file
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get the GameService bean
        GameService gameService = (GameService) context.getBean("gameService");

        // Example: Create a new Game
        Game newGame = new Game("test2", "Hard", 30.5);
        gameService.createGame(newGame);
        Game newGame2 = new Game("test1", "Hard", 30.5);
        gameService.createGame(newGame2);
        // Example: Retrieve all games
        System.out.println("All Games: " + gameService.findAllGames());

        /*TeamDao teamDao = new TeamDaoImpl();
        TeamService teamService = new TeamServiceImpl(teamDao);

        Team newTeam = new Team("Team l", 6);
        teamService.createTeam(newTeam);
        Team newTeam2 = new Team("Team m", 6);
        teamService.createTeam(newTeam2);

        List<Team> teams = teamService.getAllTeams();
        teams.forEach(System.out::println);*/



    }
}
