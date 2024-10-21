package com.gameDev.dao.impl;

import com.gameDev.dao.GameDao;
import com.gameDev.entity.Game;
import com.gameDev.util.JPAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GameDaoImpl implements GameDao {

    private static final Logger logger = LoggerFactory.getLogger(GameDaoImpl.class);

    @Override
    public void saveGame(Game game) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            try {
                entityManager.persist(game);
            } catch (Exception e) {
                e.printStackTrace();
            }            transaction.commit();
            logger.info("Game saved: {}", game.getName());
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for saving game: {}", game.getName(), e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Game getGameById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            Game game = entityManager.find(Game.class, id);
            logger.info("Retrieved game with ID: {}", id);
            return game;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Game> getAllGames() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            List<Game> games = entityManager.createQuery("FROM Game", Game.class).getResultList();
            logger.info("Retrieved {} games from the database", games.size());
            return games;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateGame(Game game) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(game);
            transaction.commit();
            logger.info("Game updated: {}", game.getName());
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for updating game: {}", game.getName(), e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteGame(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Game game = entityManager.find(Game.class, id);
            if (game != null) {
                entityManager.remove(game);
                logger.info("Game deleted with ID: {}", id);
            } else {
                logger.warn("No game found with ID: {}", id);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for deleting game with ID: {}", id, e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
