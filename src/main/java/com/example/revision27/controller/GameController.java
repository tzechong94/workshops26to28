package com.example.revision27.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.revision27.models.Game;
import com.example.revision27.service.GameService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
public class GameController {
    
    @Autowired
    private GameService gameSvc;

    @GetMapping("/games")
    public ResponseEntity<JsonObject> findAllGames(@RequestParam(defaultValue = "25") Integer limit, 
    @RequestParam(defaultValue = "0") Integer offset) {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        List<Game> gameList = gameSvc.findAllGames(limit, offset);

        System.out.println("gameList" + gameList);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<JsonObject> listOfGames = gameList
                    .stream()
                    .map(g -> g.toJsonObject())
                    .toList();
        
        for (JsonObject x : listOfGames) {
            arrBuilder.add(x);
        }

        System.out.println("arr" + arrBuilder);


        JsonObject result = Json.createObjectBuilder()
                        .add("games", arrBuilder)
                        .add("offset", offset)
                        .add("limit", limit)
                        .add("total", gameSvc.count())
                        .add("timestamp", timestamp.toString())
                        .build();
        
        System.out.println("arr" + result);

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/games/rank")
    public ResponseEntity<JsonObject> findGamesByRank(@RequestParam(defaultValue = "25") Integer limit, 
    @RequestParam(defaultValue = "0") Integer offset) {

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        List<Game> gameList = gameSvc.findGamesByRank(limit, offset);

        System.out.println("gameList" + gameList);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<JsonObject> listOfGames = gameList
                    .stream()
                    .map(g -> g.toJsonObject())
                    .toList();
        
        for (JsonObject x : listOfGames) {
            arrBuilder.add(x);
        }

        System.out.println("arr" + arrBuilder);


        JsonObject result = Json.createObjectBuilder()
                        .add("games", arrBuilder)
                        .add("offset", offset)
                        .add("limit", limit)
                        .add("total", gameSvc.count())
                        .add("timestamp", timestamp.toString())
                        .build();
        
        System.out.println("arr" + result);

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> findGameBy_Id(@PathVariable String gameId) {
        try {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        List<Game> games = gameSvc.findGameBy_Id(gameId);
        Game game = games.get(0);

        JsonObject result = Json.createObjectBuilder()
                                .add("game_id", game.get_id().toString())
                                .add("name", game.getName())
                                .add("year", game.getYear())
                                .add("ranking", game.getRanking())
                                .add("users_rated", game.getUsersRated())
                                .add("url", game.getUrl())
                                .add("timestamp", timestamp.toString())
                                .build();

        System.out.println(result + "result");

        
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(e);

        }
    }


}
