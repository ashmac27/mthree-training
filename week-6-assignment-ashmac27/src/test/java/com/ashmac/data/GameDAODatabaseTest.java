package com.ashmac.data;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ashmac.TestApp;
import com.ashmac.models.Game;
import com.ashmac.models.Round;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

@SpringBootTest(classes = TestApp.class)
public class GameDAODatabaseTest {

    @Autowired
    GameDAO gameDao;

    public GameDAODatabaseTest() {

    }


    @AfterEach
    public void setUp() {
        List<Round> rounds = gameDao.getAllRounds();
        for (Round r : rounds) {
            gameDao.deleteRoundById(r.getRoundId());
        }

        List<Game> games = gameDao.getAllGames();
        for (Game g : games) {
            gameDao.deleteGameById(g.getGameId());
        }
    }


    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        game = gameDao.addGame(game);

        Game newGame = gameDao.findGameById(game.getGameId());
       // System.out.println(game);

        assertEquals(game.getGameId(), newGame.getGameId());
    }

    @Test
    public void testGetAllGames() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        game = gameDao.addGame(game);

        List<Game> games = gameDao.getAllGames();

        assertEquals(1, games.size());

        //System.out.println(games);


    }

    @Test
    public void testUpdateGame() {

        Game game = new Game();
        game.setAnswer("1235");
        game.setIsFinished(false);
        game = gameDao.addGame(game);

        Game newGame = gameDao.findGameById(game.getGameId());

        assertEquals(game.getGameId(), newGame.getGameId());

        game.setIsFinished(true);

        gameDao.updateGame(game);
        assertNotEquals(game, newGame);

    }

    @Test
    public void testDeleteGame() {


        Game game = new Game();
        game.setAnswer("1236");
        game.setIsFinished(false);
        game = gameDao.addGame(game);

        gameDao.deleteGameById(game.getGameId());
        System.out.println(gameDao.toString());

        Game fromDao; //= null;

        try{
            fromDao = gameDao.findGameById(game.getGameId());
        }catch (EmptyResultDataAccessException ex){
            fromDao = null;
        }
       // Game fromDao = gameDao.findGameById(game.getGameId());
        //assertNull();
       // System.out.println(fromDao.toString());
        assertNull(fromDao);


    }
    LocalDateTime now = LocalDateTime.now();

    @Test
    public void testAddGetRound() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        game = gameDao.addGame(game);

        Round round1 = new Round();

        round1.setGuess("1234");
        round1.setResult("e:4:p:0");
        round1.setGameId(game.getGameId());
        gameDao.addRound(round1);

        Round fromDao = gameDao.getRoundByID(round1.getRoundId());

        round1.setGuessTime(fromDao.getGuessTime());
        assertEquals(round1.getRoundId(), fromDao.getRoundId());

    }

    @Test
    public void testGetAllRounds() {

        Game game = new Game();
        game.setAnswer("3456");
        game.setIsFinished(false);
        game = gameDao.addGame(game);

        Round round1 = new Round();

        round1.setGuess("3456");
        round1.setResult("e:0:p:3");
        round1.setGameId(game.getGameId());


        gameDao.addRound(round1);

        Game game2 = new Game();
        game2.setAnswer("9876");
        game2.setIsFinished(true);
        game2 = gameDao.addGame(game2);


        Round round2 = new Round();

        round2.setGuess("9876");
        round2.setResult("e:0:p:4");
        round2.setGameId(game2.getGameId());
        gameDao.addRound(round2);

        List<Round> rounds = gameDao.getAllRounds();

        assertEquals(2, rounds.size());


    }
}
