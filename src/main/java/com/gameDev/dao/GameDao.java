package com.gameDev.dao;

import com.gameDev.entity.Game;

import java.util.List;

public interface GameDao {
    void saveGame(Game game);
    Game getGameById(int id);
    List<Game> getAllGames();
    void updateGame(Game game);
    void deleteGame(int id);
}
