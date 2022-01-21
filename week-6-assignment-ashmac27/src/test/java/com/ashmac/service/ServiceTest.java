package com.ashmac.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ashmac.models.Game;
import com.ashmac.data.GameDAO;
import com.ashmac.TestApp;

@SpringBootTest(classes = TestApp.class)
public class ServiceTest {
    @Autowired
    Service service;

    @Autowired
    GameDAO gameDao;


    public ServiceTest() {

    }

    @Test
    public void testCreateAnswer() {

        String result = service.createAnswer();

        assertEquals(4, result.length());

        assertEquals(true, uniqueCharacters(result));

    }

    boolean uniqueCharacters(String str){
        for (int i = 0; i < str.length(); i++)
            for (int j = i + 1; j < str.length(); j++)
                if (str.charAt(i) == str.charAt(j))
                    return false;

        return true;
    }


    @Test
    public void testGetGameFalse() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        game = gameDao.addGame(game);

        Game newGame = service.getGame(game.getGameId());
        assertEquals(game.getAnswer(), newGame.getAnswer());

    }

    @Test
    public void testGetGameTrue() {

        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setIsFinished(true);
        game1 = gameDao.addGame(game1);

        Game newGame = service.getGame(game1.getGameId());
        assertEquals(game1.getAnswer(), newGame.getAnswer());

    }


    @Test
    public void testGame1() {
        String guess = "1234";
        String answer = "4320";
        String result = service.getResult(guess, answer);

        assertEquals("e:0:p:3", result);
    }

    @Test
    public void testGame2() {
        String guess = "1234";
        String answer = "1234";
        String result = service.getResult(guess, answer);

        assertEquals("e:4:p:0", result);
    }

    @Test
    public void testGame3() {
        String guess = "1234";
        String answer = "4321";
        String result = service.getResult(guess, answer);

        assertEquals("e:0:p:4", result);
    }
}
