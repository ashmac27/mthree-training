package com.ashmac.data;

import com.ashmac.models.*;
import java.util.List;

public interface GameDAO {

    Game addGame(Game game);
    List<Game> getAllGames();
    Game findGameById(int gameId);
    void updateGame(Game game);
    void deleteGameById(int gameId);


    Round addRound(Round round);
    List<Round> getAllRounds();
    Round getRoundByID(int roundId);
    void updateRound(Round round);
    void deleteRoundById(int roundId);

    List<Round> getAGameForRound(int id);

}
