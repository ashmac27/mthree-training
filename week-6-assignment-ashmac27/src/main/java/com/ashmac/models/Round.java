package com.ashmac.models;
import java.time.LocalDateTime;

public class Round {

    private int roundId;
    private int gameId;
    private String guess;
    private LocalDateTime guessTime;
    private String result;

    public Round() {
    }

    public Round(int roundId, int gameId, LocalDateTime guessTime,String guess, String result ){

        this.roundId = roundId;
        this.gameId = gameId;
        this.guessTime = guessTime;
        this.guess = guess;
        this.result = result;

    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
