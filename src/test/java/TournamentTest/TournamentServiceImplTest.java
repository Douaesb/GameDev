package TournamentTest;

import com.gameDev.dao.TournamentDao;
import com.gameDev.dao.impl.TournamentDaoExtension;
import com.gameDev.entity.Tournament;
import com.gameDev.entity.enums.Status;
import com.gameDev.service.impl.TournamentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TournamentServiceImplTest {

    @Mock
    private TournamentDao tournamentDao;

    @InjectMocks
    private TournamentDaoExtension tournamentDaoExtension;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Tournament createSampleTournament() {
        return new Tournament(
                "Tournament A",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 15),
                5000,
                10,
                2,
                1,
                Status.ONGOING
        );
    }

    @Test
    void createTournamentTest() {
        Tournament tournament = createSampleTournament();

        // Call the service method
        tournamentService.createTournament(tournament);

        // Verify that the DAO's saveTournament method was called once
        verify(tournamentDao, times(1)).saveTournament(tournament);
    }

    @Test
    void getTournamentByIdTest() {
        Tournament tournament = createSampleTournament();
        tournament.setId(1);

        // Mock the DAO behavior
        when(tournamentDao.getTournamentById(1)).thenReturn(tournament);

        // Call the service method
        Tournament result = tournamentService.getTournamentById(1);

        // Assert and verify the result
        assertEquals(tournament.getId(), result.getId());
        assertEquals(tournament.getTitle(), result.getTitle());
        verify(tournamentDao, times(1)).getTournamentById(1);
    }

    @Test
    void getAllTournamentsTest() {
        List<Tournament> tournaments = Arrays.asList(
                new Tournament("Tournament A", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 10), 4000, 8, 2, 1, Status.ONGOING),
                new Tournament("Tournament B", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 10), 4000, 8, 2, 1, Status.ONGOING)
        );

        // Mock the DAO behavior
        when(tournamentDao.getAllTournaments()).thenReturn(tournaments);

        // Call the service method
        List<Tournament> result = tournamentService.getAllTournaments();

        // Assert and verify the result
        assertEquals(2, result.size());
        verify(tournamentDao, times(1)).getAllTournaments();
    }

    @Test
    void updateTournamentTest() {
        Tournament tournament = createSampleTournament();

        // Call the service method
        tournamentService.updateTournament(tournament);

        // Verify that the DAO's updateTournament method was called once
        verify(tournamentDao, times(1)).updateTournament(tournament);
    }

    @Test
    void deleteTournamentTest() {
        int tournamentId = 1;

        // Call the service method
        tournamentService.deleteTournament(tournamentId);

        // Verify that the DAO's deleteTournament method was called once
        verify(tournamentDao, times(1)).deleteTournament(tournamentId);
    }

    @Test
    void assignTeamToTournamentTest() {
        int tournamentId = 1;
        int teamId = 2;

        // Call the service method

        tournamentService.assignTeamToTournament(tournamentId, teamId);
        // Verify that the DAO's assignTeamToTournament method was called once
        verify(tournamentDao, times(1)).assignTeamToTournament(tournamentId, teamId);
    }

    @Test
    void removeTeamFromTournamentTest() {
        int tournamentId = 1;
        int teamId = 2;

        // Call the service method
        tournamentService.removeTeamFromTournament(tournamentId, teamId);
        // Verify that the DAO's removeTeamFromTournament method was called once
        verify(tournamentDao, times(1)).removeTeamFromTournament(tournamentId, teamId);
    }

}
