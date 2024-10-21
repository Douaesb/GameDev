package com.gameDev.service;

import com.gameDev.entity.Game;

import java.util.List;

public interface GameService {
    void createGame(Game game);
    Game findGameById(int id);
    List<Game> findAllGames();
    void updateGame(Game game);
    void deleteGameById(int id);
}
