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
    
}
