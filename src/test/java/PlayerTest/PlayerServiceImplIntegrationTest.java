package PlayerTest;

import com.gameDev.dao.impl.PlayerDaoImpl;
import com.gameDev.entity.Player;
import com.gameDev.entity.Team;
import com.gameDev.service.impl.PlayerServiceImpl;
import com.gameDev.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerServiceImplIntegrationTest {

    private PlayerServiceImpl playerService;
    private TeamServiceImpl teamService;
    private static final Logger logger = LoggerFactory.getLogger(PlayerDaoImpl.class);

    @BeforeAll
    public static void init() {
        System.setProperty("puName", "TestGameDev");
    }

    @BeforeEach
    void setUp() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        playerService = (PlayerServiceImpl) context.getBean("testPlayerService");
        teamService = (TeamServiceImpl) context.getBean("testTeamService");

        logger.info("Using in-memory database for testing.");
    }

    @Test
    void testCreateAndFindPlayerWithTeam() {
        // Create a new team
        Team team = new Team("Team A", 1);
        teamService.createTeam(team); // Assuming you have a method to save teams

        // Create a new player
        Player player = new Player("John", 18);
        player.setTeam(team); // Assign the team to the player
        playerService.savePlayer(player);

        // Retrieve the player by ID
        Player foundPlayer = playerService.getPlayerById(player.getId());

        assertEquals("John", foundPlayer.getNickName());
        assertEquals(18, foundPlayer.getAge());
        assertEquals("Team A", foundPlayer.getTeam().getName()); // Check the team name
    }

    @Test
    void testFindAllPlayers() {
        // Create a team for players
        Team team = new Team("Team B", 2);

        teamService.createTeam(team); // Save the team

        // Create some players
        Player player = new Player("Alice", 1);
        player.setTeam(team);
        Player player2 = new Player("Bob", 2);
        player2.setTeam(team);
        playerService.savePlayer(player); // Assuming constructor with team
        playerService.savePlayer(player2); // Assuming constructor with team

        // Find all players
        List<Player> players = playerService.getAllPlayers();

        assertEquals(2, players.size());
    }

    @Test
    void testUpdatePlayerWithTeam() {
        // Create a new team
        Team team = new Team("Team C", 3);
        teamService.createTeam(team);

        // Create a new player
        Player player = new Player("Charlie", 3);
        player.setTeam(team); // Assign team to player
        playerService.savePlayer(player);

        // Update the player's nickname
        player.setNickName("Updated Charlie");
        playerService.updatePlayer(player);

        // Retrieve the updated player
        Player updatedPlayer = playerService.getPlayerById(player.getId());

        assertEquals("Updated Charlie", updatedPlayer.getNickName());
        assertEquals("Team C", updatedPlayer.getTeam().getName()); // Ensure the team remains unchanged
    }

    @Test
    void testDeletePlayer() {
        // Create a team
        Team team = new Team("Team D", 4);
        teamService.createTeam(team);

        // Create a new player
        Player player = new Player("Dave", 4);
        player.setTeam(team); // Assign team to player
        playerService.savePlayer(player);

        playerService.deletePlayer(player.getId());

        Player deletedPlayer = playerService.getPlayerById(player.getId());

        assertEquals(null, deletedPlayer);
    }
}
