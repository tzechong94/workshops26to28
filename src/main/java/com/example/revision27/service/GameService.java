package com.example.revision27.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.revision27.models.Game;
import com.example.revision27.repo.GameRepo;


@Service
public class GameService {

    @Autowired
    private GameRepo gameRepo;
    public List<Game> findAllGames(Integer limit, Integer offset) {
        return gameRepo.findAllGames(limit,offset)
                    .stream()
                    .map(g -> Game.create(g))
                    .toList();
    }
    
    public Integer count() {
        return gameRepo.count();
    }

    public List<Game> findGamesByRank(Integer limit, Integer offset) {
        return gameRepo.findAllGames(limit,offset)
                    .stream()
                    .map(g -> Game.create(g))
                    .toList();
    }

    public List<Game> findGameBy_Id(String gameId) {
        return gameRepo.findGameBy_Id(gameId)
                        .stream()
                        .map(g -> Game.create(g))
                        .toList();
    }

    public List<Game> findGameByGameId(String gameId) {
        return gameRepo.findGameBy_Id(gameId)
                        .stream()
                        .map(g -> Game.create(g))
                        .toList();
    }

    public String getGameNameFromId(Integer gameId) {
        List<Game> gameList =  gameRepo.getGameNameFromId(gameId)
                        .stream()
                        .map(g -> Game.create(g))
                        .toList();
        Game game = gameList.get(0);
        String gameName = game.getName();
        return gameName;
    }


    
}
