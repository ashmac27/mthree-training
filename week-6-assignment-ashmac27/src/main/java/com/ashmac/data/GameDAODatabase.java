package com.ashmac.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.dao.DataAccessException;


import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ashmac.models.Game;
import com.ashmac.models.Round;

@Repository
public class GameDAODatabase implements GameDAO{

    @Autowired
    JdbcTemplate jdbc;


    @Autowired
    public GameDAODatabase(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer, isFinished) VALUES(?,?)";

        jdbc.update(INSERT_GAME,
                //Might have to get changed
                game.getAnswer(),
                game.getIsFinished());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game findGameById(int id) {

        final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE gameId = ?";
        return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), id);

    }


    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET isFinished = ?, answer = ? WHERE gameId = ?";
        jdbc.update(UPDATE_GAME,
                game.getIsFinished(),
                game.getAnswer(),
                game.getGameId());
    }

    @Override
    public void deleteGameById(int id) {

        final String DELETE_GAME_BY_ID = "DELETE FROM game WHERE gameId = ?";
        jdbc.update(DELETE_GAME_BY_ID, id);
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setIsFinished(rs.getBoolean("isFinished"));
            return game;
        }
    }


    //Round part of Controller

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(gameId, guess, result) VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND,
                round.getGameId(),
                round.getGuess(),
                round.getResult());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newId);
        return getRoundByID(newId);
    }

    @Override
    public List<Round> getAllRounds() {
        final String SELECT_ALL_ROOMS = "SELECT * FROM round";
        return jdbc.query(SELECT_ALL_ROOMS, new RoundMapper());
    }

    @Override
    public Round getRoundByID(int id) {
        try {
            final String SELECT_ROOM_BY_ID = "SELECT * FROM round WHERE roundId = ?";
            return jdbc.queryForObject(SELECT_ROOM_BY_ID, new RoundMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateRound(Round round) {
        final String UPDATE_ROOM = "UPDATE round SET guess = ?, result = ? WHERE roundId = ?";
        jdbc.update(UPDATE_ROOM,
                round.getGuess(),
                round.getResult(),
                round.getRoundId());
    }

    /**
     * Look closely at this
     * @param id
     * @return
     */
    public List<Round> getAGameForRound(int id) {
        final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round "
                + "WHERE gameId = ? ORDER BY guessTime";
        List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), id);
        return rounds;
    }

    @Override
    public void deleteRoundById(int id) {
        final String DELETE_ROUND_BY_ID = "DELETE FROM round WHERE roundId = ?";
        jdbc.update(DELETE_ROUND_BY_ID, id);

    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));
            Timestamp timestamp = rs.getTimestamp("guessTime");
            round.setGuessTime(timestamp.toLocalDateTime());
            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("result"));
            return round;
        }
    }

}
