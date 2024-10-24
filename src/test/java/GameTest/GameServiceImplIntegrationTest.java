package GameTest;

import com.gameDev.dao.impl.GameDaoImpl;
import com.gameDev.entity.Game;
import com.gameDev.service.GameService;
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

class GameServiceImplIntegrationTest {

    private GameService gameService;
    private static final Logger logger = LoggerFactory.getLogger(GameDaoImpl.class);

    @BeforeAll
    public static void init(){
        System.setProperty("puName", "TestGameDev");
    }

    @BeforeEach
    void setUp() throws SQLException {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        gameService = (GameService) context.getBean("testGameService");

        logger.info("Using in-memory database for testing.");
    }

    @Test
    void testCreateAndFindGame() {
        // Create a new game
        Game game = new Game();
        game.setName("New Game");
        game.setDifficulty(20.0);
        game.setMatchAvgDuration(10.0);

        gameService.createGame(game);

        // Retrieve the game by ID
        Game foundGame = gameService.findGameById(game.getId());

        assertEquals("New Game", foundGame.getName());
    }

    @Test
    void testFindAllGames() {
        // Create some games
        gameService.createGame(new Game("Game 3", 60.0, 20.0));
        gameService.createGame(new Game("Game 4", 30.0, 20.0));

        // Find all games
        List<Game> games = gameService.findAllGames();

        assertEquals(2, games.size());
    }
}
