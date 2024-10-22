package com.gameDev.service;

import com.gameDev.entity.Player;

import java.util.List;

public interface PlayerService {
    void savePlayer(Player player);
    Player getPlayerById(int id);
    List<Player> getAllPlayers();
    void updatePlayer(Player player);
    void deletePlayer(int id);
}
