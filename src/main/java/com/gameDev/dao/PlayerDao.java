package com.gameDev.dao;

import com.gameDev.entity.Player;

import java.util.List;

public interface PlayerDao {
    void savePlayer(Player player);
    Player getPlayerById(int id);
    List<Player> getAllPlayers();
    void updatePlayer(Player player);
    void deletePlayer(int id);
}
