package TournamentTest;

import com.gameDev.dao.TournamentDao;
import com.gameDev.dao.impl.TournamentDaoExtension;
import com.gameDev.entity.Game; // Assuming you have a Game class
import com.gameDev.entity.Tournament;
import com.gameDev.entity.enums.Status;
import com.gameDev.service.impl.TournamentServiceImpl;
import com.gameDev.service.impl.GameServiceImpl; // Assuming you have a GameService for managing games
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TournamentServiceImplIntegrationTest {

    private TournamentServiceImpl tournamentService;
    private GameServiceImpl gameService; // Service to manage games

    @BeforeAll
    public static void init() {
        System.setProperty("puName", "TestGameDev");
    }


    @BeforeEach
    void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        tournamentService = (TournamentServiceImpl) context.getBean("testTournamentService");
        gameService = (GameServiceImpl) context.getBean("testGameService"); // Ensure this is set up
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
    void createAndAssignGameToTournamentTest() {
        // Create and save a new game
        Game game = new Game("Game A", 20.0, 30.0); // Assuming a constructor
        gameService.createGame(game); // Assuming a method to create a game

        // Create a new tournament
        Tournament tournament = createSampleTournament();
        tournament.setGame(game); // Assuming a setGame method exists

        tournamentService.createTournament(tournament); // Save tournament

        // Assign the game to the tournament after instantiation
        tournament.setGame(game); // Assuming a setGame method exists
        tournamentService.updateTournament(tournament); // Update tournament with the game

        // Retrieve the tournament by ID
        Tournament foundTournament = tournamentService.getTournamentById(tournament.getId());

        // Assertions
        assertEquals(tournament.getId(), foundTournament.getId());
        assertEquals("Tournament A", foundTournament.getTitle());
        assertEquals(game.getId(), foundTournament.getGame().getId()); // Ensure the game is correctly associated
    }

    @Test
    void getAllTournamentsTest() {
        // Create and save a game
        Game game = new Game("Game B",50.0, 40.0);
        gameService.createGame(game);

        // Create and save tournaments
        Tournament tournament1 = createSampleTournament();
        Tournament tournament2 = createSampleTournament();
        tournament1.setGame(game);
        tournament2.setGame(game);
        tournamentService.createTournament(tournament1);
        tournamentService.createTournament(tournament2);

        // Retrieve all tournaments
        List<Tournament> tournaments = tournamentService.getAllTournaments();

        // Assertions
        assertEquals(2, tournaments.size());
        assertEquals(game.getId(), tournaments.get(0).getGame().getId()); // Ensure the game is associated with the tournaments
        assertEquals(game.getId(), tournaments.get(1).getGame().getId());
    }

    @Test
    void updateTournamentWithGameTest() {
        // Create and save a game
        Game game = new Game("Game C", 20.0,10.0);
        gameService.createGame(game);

        // Create and save a tournament
        Tournament tournament = createSampleTournament();
        tournament.setGame(game);
        tournamentService.createTournament(tournament);

        // Update tournament details
        tournament.setTitle("Updated Tournament A");
        tournamentService.updateTournament(tournament);

        // Retrieve the updated tournament
        Tournament updatedTournament = tournamentService.getTournamentById(tournament.getId());

        // Assertions
        assertEquals("Updated Tournament A", updatedTournament.getTitle());
        assertEquals(game.getId(), updatedTournament.getGame().getId()); // Check the game association remains
    }

    @Test
    void deleteTournamentTest() {
        // Create and save a game
        Game game = new Game("Game D", 10.0, 50.0);
        gameService.createGame(game);

        // Create and save a tournament
        Tournament tournament = createSampleTournament();
        tournament.setGame(game);
        tournamentService.createTournament(tournament);

        // Delete the tournament
        tournamentService.deleteTournament(tournament.getId());

        // Attempt to retrieve the deleted tournament
        Tournament deletedTournament = tournamentService.getTournamentById(tournament.getId());

        // Assertions
        assertEquals(null, deletedTournament); // Ensure it is deleted
    }
}
