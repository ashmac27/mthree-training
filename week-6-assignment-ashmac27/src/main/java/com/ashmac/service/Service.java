package com.ashmac.service;

import com.ashmac.data.GameDAO;
import com.ashmac.models.Game;
import com.ashmac.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class Service {

    @Autowired
    GameDAO gDao;

    public int startGame(){
        Game game = new Game();
        game.setAnswer(createAnswer());
        gDao.addGame(game);
        return game.getGameId();
    }

    public String createAnswer(){
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(l);
        Integer result = 1000*l.get(0) + 100*l.get(1) + 10*l.get(2) + l.get(3);

        return result.toString();
    }

    public List<Round> getRoundsViaGameId(int gameId){
        return gDao.getAGameForRound(gameId);
    }

    public Game getGame(int gameId){
        Game game = gDao.findGameById(gameId);
        //if(game.getIsFinished() == false){
          //  game.setAnswer("aaaa");
        //}
        return game;
    }

    //Do we need this still? Using createanswer instead
    private String generateAnswer(){
        List<Integer> numbers = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        String answer = "";
        for(int i = 0; i < 4; i++){
            answer += numbers.get(i).toString();
        }
        return answer;
    }

    public List<Game> getListGames(){
        return gDao.getAllGames();
    }

    public Round makeGuess(Round round) {
        String answer = gDao.findGameById(round.getGameId()).getAnswer();
        String result = getResult(round.getGuess(), answer);
        round.setResult(result);

        if (round.getGuess().equals(answer)) {
            Game game = getGame(round.getGameId());
            game.setIsFinished(true);
            gDao.updateGame(game);
        }

        return gDao.addRound(round);
    }

    public String getResult(String guess, String answer) {
        int exactMatch  = 0;
        int partialMatch = 0;
        int index = 0;

        char[] guessArray = guess.toCharArray();
        char[] answerArray = answer.toCharArray();

        while(index < guessArray.length){
            if (guess.indexOf(answerArray[index]) >= 0) {
                if (guessArray[index] == answerArray[index]) {
                    exactMatch++;
                } else {
                    partialMatch++;
                }
            }
            index++;
        }
        return "e:" + exactMatch + ":p:" + partialMatch;
    }


}
