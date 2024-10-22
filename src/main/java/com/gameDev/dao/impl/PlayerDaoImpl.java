package com.gameDev.dao.impl;

import com.gameDev.dao.PlayerDao;
import com.gameDev.entity.Player;
import com.gameDev.util.JPAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao {

    private static final Logger logger = LoggerFactory.getLogger(PlayerDaoImpl.class);

    @Override
    public void savePlayer(Player player){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            em.persist(player);
            transaction.commit();
            logger.info("Player saved: {}", player.getNickName());
        }catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for saving player: {}", player.getNickName(), e);
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Player getPlayerById(int id){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            Player player = em.find(Player.class, id);
            logger.info("Retrieved player with ID: {}", id);
            return player;
        }finally {
            em.close();
        }
    }

    @Override
    public List<Player> getAllPlayers(){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            List<Player> players = em.createQuery("FROM Player ", Player.class).getResultList();
            logger.info("Retrieved {} players from the database", players.size());
            return players;
        }finally {
            em.close();
        }
    }

    @Override
    public void updatePlayer(Player player){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            em.merge(player);
            transaction.commit();
            logger.info("Player updated: {}", player.getNickName());
        }catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for updating player: {}", player.getNickName(), e);
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deletePlayer(int id){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Player player = em.find(Player.class, id);

            if(player != null){
                em.remove(player);
                logger.info("Player deleted with ID: {}", id);
            }else {
                logger.warn("No player found with ID: {}", id);
            }
            transaction.commit();
        }catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for deleting player with ID: {}", id, e);
            }
            throw e;
        } finally {
            em.close();
        }
    }

}
