package com.ashmac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.ashmac.service.Service;
import com.ashmac.models.*;

@RestController
@RequestMapping("api/gtn")
public class GTNController {

    @Autowired
    Service service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int create() {
        return service.startGame();
    }

    @PostMapping("/guess")
    public Round guess(@RequestBody Round round) { //@RequestBody tells Spring MVC to expect the data fully serialized in the HTTP request body
        return service.makeGuess(round);
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getListGames();
    }

    @GetMapping("/game/{gameId}")
    public Game getGame(@PathVariable("gameId") int id) {
        return service.getGame(id);
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> getRounds(@PathVariable("gameId") int id) {
        return service.getRoundsViaGameId(id);
    }

}
