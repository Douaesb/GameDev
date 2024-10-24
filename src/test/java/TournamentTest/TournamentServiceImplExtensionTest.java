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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TournamentServiceImplExtensionTest {

    @Mock
    private TournamentDao tournamentDao;

    @Mock
    private TournamentDaoExtension tournamentDaoExtension;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getEstimatedDurationTournamentTest() {
        int tournamentId = 7;

        Tournament tournament =  new Tournament("Tournament A", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 10), 4000, 8, 2, 1, Status.ONGOING);
        tournament.setId(tournamentId);

        // Mock the DAO behavior
        when(tournamentDao.getTournamentById(tournamentId)).thenReturn(tournament);
        when(tournamentDaoExtension.calculateEstimatedDurationTournament(tournamentId)).thenReturn(10.5);

        // Call the service method
        double result = tournamentService.getEstimatedDurationTournament(tournamentId);

        // Assert the result
        assertEquals(10.5, result);
        verify(tournamentDaoExtension, times(1)).calculateEstimatedDurationTournament(tournamentId);
    }
}
