package com.gameDev;

import com.gameDev.entity.Player;
import com.gameDev.entity.Team;
import com.gameDev.service.PlayerService;
import com.gameDev.service.TeamService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Load application context from XML file
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get the PlayerService and TeamService beans
        PlayerService playerService = (PlayerService) context.getBean("playerService");
        TeamService teamService = (TeamService) context.getBean("teamService");

        // ===============================
        // 1. Creating a new Team
        // ===============================
        Team team = new Team("Alpha", 5);
        teamService.createTeam(team);
        System.out.println("Team created: " + team);

        // ===============================
        // 2. Creating a new Player
        // ===============================
        Player newPlayer = new Player("Gamer", 24);
        newPlayer.setTeam(team); // Assign the team to the player
        playerService.savePlayer(newPlayer);
        System.out.println("Player created: " + newPlayer);

        // ===============================
        // 3. Fetching and displaying all Players
        // ===============================
        List<Player> players = playerService.getAllPlayers();
        System.out.println("All Players:");
        players.forEach(System.out::println);

        // ===============================
        // 4. Fetching a Player by ID
        // ===============================
        Player retrievedPlayer = playerService.getPlayerById(newPlayer.getId());
        if (retrievedPlayer != null) {
            System.out.println("Player retrieved by ID: " + retrievedPlayer);
        } else {
            System.out.println("Player not found with ID: " + newPlayer.getId());
        }

        // ===============================
        // 5. Updating a Player
        // ===============================
        retrievedPlayer.setNickName("GamerPro");
        retrievedPlayer.setAge(26);
        playerService.updatePlayer(retrievedPlayer);
        System.out.println("Player updated: " + retrievedPlayer);


    }
}
