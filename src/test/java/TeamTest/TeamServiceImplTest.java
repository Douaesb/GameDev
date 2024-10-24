package TeamTest;

import com.gameDev.dao.TeamDao;
import com.gameDev.entity.Team;
import com.gameDev.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeamServiceImplTest {

    @Mock
    private TeamDao teamDao;

    @InjectMocks
    private TeamServiceImpl teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTeamTest() {
        Team team = new Team("Team A", 1);


        teamService.createTeam(team);


        verify(teamDao, times(1)).saveTeam(team);
    }

    @Test
    void getTeamByIdTest() {
        Team team = new Team("Team A", 1);
        team.setId(1);

        // Mock the DAO behavior
        when(teamDao.getTeamById(1)).thenReturn(team);


        Team result = teamService.getTeamById(1);


        assertEquals(team.getId(), result.getId());
        assertEquals(team.getName(), result.getName());
        verify(teamDao, times(1)).getTeamById(1);
    }

    @Test
    void getAllTeamsTest() {
        List<Team> teams = Arrays.asList(new Team("Team A", 1), new Team("Team B", 2));

        // Mock the DAO behavior
        when(teamDao.getAllTeams()).thenReturn(teams);


        List<Team> result = teamService.getAllTeams();


        assertEquals(2, result.size());
        verify(teamDao, times(1)).getAllTeams();
    }

    @Test
    void updateTeamTest() {
        Team team = new Team("Updated Team", 1);


        teamService.updateTeam(team);

        verify(teamDao, times(1)).updateTeam(team);
    }

    @Test
    void deleteTeamTest() {
        int teamId = 1;


        teamService.deleteTeam(teamId);

        verify(teamDao, times(1)).deleteTeam(teamId);
    }

    @Test
    void changePlayerTeamTest() {
        int playerId = 1;
        int newTeamId = 2;


        teamService.changePlayerTeam(playerId, newTeamId);

        verify(teamDao, times(1)).changePlayerTeam(playerId, newTeamId);
    }
}
