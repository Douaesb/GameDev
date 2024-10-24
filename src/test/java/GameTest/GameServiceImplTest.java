package GameTest;

import com.gameDev.dao.GameDao;
import com.gameDev.entity.Game;
import com.gameDev.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGameTest() {
        Game game = new Game();
        game.setName("Game 1");

        gameService.createGame(game);

        verify(gameDao, times(1)).saveGame(game);
    }

    @Test
    void findGameByIdTest() {
        Game game = new Game();
        game.setId(1);
        when(gameDao.getGameById(1)).thenReturn(game);

        Game result = gameService.findGameById(1);

        assertEquals(1, result.getId());
        verify(gameDao, times(1)).getGameById(1);
    }

    @Test
    void findAllGamesTest() {
        List<Game> games = Arrays.asList(new Game(), new Game());
        when(gameDao.getAllGames()).thenReturn(games);

        List<Game> result = gameService.findAllGames();

        assertEquals(2, result.size());
        verify(gameDao, times(1)).getAllGames();
    }

    @Test
    void updateGameTest() {
        Game game = new Game();
        game.setName("Updated Game");

        gameService.updateGame(game);

        verify(gameDao, times(1)).updateGame(game);
    }

    @Test
    void deleteGameByIdTest() {

        gameService.deleteGameById(1);
        verify(gameDao, times(1)).deleteGame(1);
    }
}