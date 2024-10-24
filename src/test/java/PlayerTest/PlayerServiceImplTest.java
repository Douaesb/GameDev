package PlayerTest;

import com.gameDev.dao.PlayerDao;
import com.gameDev.entity.Player;
import com.gameDev.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerServiceImplTest {

    @Mock
    private PlayerDao playerDao;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePlayerTest() {
        Player player = new Player("John", 1);

        // Call the service method
        playerService.savePlayer(player);

        // Verify that the DAO's savePlayer method was called once
        verify(playerDao, times(1)).savePlayer(player);
    }

    @Test
    void getPlayerByIdTest() {
        Player player = new Player("John", 1);
        player.setId(1);

        // Mock the DAO behavior
        when(playerDao.getPlayerById(1)).thenReturn(player);

        // Call the service method
        Player result = playerService.getPlayerById(1);

        // Assert and verify the result
        assertEquals(player.getId(), result.getId());
        assertEquals(player.getNickName(), result.getNickName());
        verify(playerDao, times(1)).getPlayerById(1);
    }

    @Test
    void getAllPlayersTest() {
        List<Player> players = Arrays.asList(new Player("John", 1), new Player("Jane", 2));

        // Mock the DAO behavior
        when(playerDao.getAllPlayers()).thenReturn(players);

        // Call the service method
        List<Player> result = playerService.getAllPlayers();

        // Assert and verify the result
        assertEquals(2, result.size());
        verify(playerDao, times(1)).getAllPlayers();
    }

    @Test
    void updatePlayerTest() {
        Player player = new Player("Updated Player", 1);

        // Call the service method
        playerService.updatePlayer(player);

        // Verify that the DAO's updatePlayer method was called once
        verify(playerDao, times(1)).updatePlayer(player);
    }

    @Test
    void deletePlayerTest() {
        int playerId = 1;

        // Call the service method
        playerService.deletePlayer(playerId);

        // Verify that the DAO's deletePlayer method was called once
        verify(playerDao, times(1)).deletePlayer(playerId);
    }
}
