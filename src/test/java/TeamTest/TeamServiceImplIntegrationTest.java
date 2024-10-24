package TeamTest;


import com.gameDev.dao.impl.TeamDaoImpl;
import com.gameDev.entity.Team;
import com.gameDev.service.TeamService;
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

class TeamServiceImplIntegrationTest {

    private TeamService teamService;
    private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);

    @BeforeAll
    public static void init() {
        System.setProperty("puName", "TestGameDev");
    }

    @BeforeEach
    void setUp() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        teamService = (TeamService) context.getBean("testTeamService");

        logger.info("Using in-memory database for testing.");
    }

    @Test
    void testCreateAndFindTeam() {
        // Create a new team
        Team team = new Team();
        team.setName("Team A");
        team.setRank(1);

        teamService.createTeam(team);

        // Retrieve the team by ID
        Team foundTeam = teamService.getTeamById(team.getId());

        assertEquals("Team A", foundTeam.getName());
        assertEquals(1, foundTeam.getRank());
    }

    @Test
    void testFindAllTeams() {
        // Create some teams
        teamService.createTeam(new Team("Team B", 2));
        teamService.createTeam(new Team("Team C", 3));

        // Find all teams
        List<Team> teams = teamService.getAllTeams();

        assertEquals(2, teams.size());
    }

    @Test
    void testUpdateTeam() {
        // Create a new team
        Team team = new Team("Team D", 4);
        teamService.createTeam(team);

        // Update the team's name
        team.setName("Updated Team D");
        teamService.updateTeam(team);

        // Retrieve the updated team
        Team updatedTeam = teamService.getTeamById(team.getId());

        assertEquals("Updated Team D", updatedTeam.getName());
    }

    @Test
    void testDeleteTeam() {

        Team team = new Team("Team E", 5);
        teamService.createTeam(team);

        teamService.deleteTeam(team.getId());

        Team deletedTeam = teamService.getTeamById(team.getId());

        assertEquals(null, deletedTeam);
    }

}

