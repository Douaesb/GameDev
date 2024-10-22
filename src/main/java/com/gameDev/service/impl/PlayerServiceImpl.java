package com.gameDev.service.impl;

import com.gameDev.dao.PlayerDao;
import com.gameDev.entity.Player;
import com.gameDev.service.PlayerService;

import java.util.List;

public class PlayerServiceImpl implements PlayerService {

    private final PlayerDao playerDao;

    public PlayerServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public void savePlayer(Player player) {
        playerDao.savePlayer(player);
    }

    @Override
    public Player getPlayerById(int id) {
        return playerDao.getPlayerById(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    @Override
    public void updatePlayer(Player player) {
        playerDao.updatePlayer(player);
    }

    @Override
    public void deletePlayer(int id) {
        playerDao.deletePlayer(id);
    }
}
