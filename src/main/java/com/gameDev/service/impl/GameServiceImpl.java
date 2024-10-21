package com.gameDev.service.impl;

import com.gameDev.dao.GameDao;
import com.gameDev.entity.Game;
import com.gameDev.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private GameDao gameDao;

    // Constructor for dependency injection
    public GameServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public void createGame(Game game) {
        logger.info("Creating game: {}", game.getName());
        gameDao.saveGame(game);
    }

    @Override
    public Game findGameById(int id) {
        logger.info("Finding game with ID: {}", id);
        return gameDao.getGameById(id);
    }

    @Override
    public List<Game> findAllGames() {
        logger.info("Finding all games");
        return gameDao.getAllGames();
    }

    @Override
    public void updateGame(Game game) {
        logger.info("Updating game: {}", game.getName());
        gameDao.updateGame(game);
    }

    @Override
    public void deleteGameById(int id) {
        logger.info("Deleting game with ID: {}", id);
        gameDao.deleteGame(id);
    }
}
