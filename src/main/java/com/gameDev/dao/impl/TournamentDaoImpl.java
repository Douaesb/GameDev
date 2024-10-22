package com.gameDev.dao.impl;

import com.gameDev.dao.TournamentDao;
import com.gameDev.entity.Tournament;
import com.gameDev.util.JPAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TournamentDaoImpl implements TournamentDao {

    private static final Logger logger = LoggerFactory.getLogger(TournamentDaoImpl.class);

    @Override
    public void saveTournament(Tournament tournament) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(tournament);
            transaction.commit();
            logger.info("Tournament saved: {}", tournament.getTitle());
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for saving tournament: {}", tournament.getTitle(), e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Tournament getTournamentById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            Tournament tournament = entityManager.find(Tournament.class, id);
            logger.info("Retrieved tournament with ID: {}", id);
            return tournament;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Tournament> getAllTournaments() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            List<Tournament> tournaments = entityManager.createQuery("FROM Tournament", Tournament.class).getResultList();
            logger.info("Retrieved {} tournaments from the database", tournaments.size());
            return tournaments;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateTournament(Tournament tournament) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(tournament);
            transaction.commit();
            logger.info("Tournament updated: {}", tournament.getTitle());
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for updating tournament: {}", tournament.getTitle(), e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteTournament(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Tournament tournament = entityManager.find(Tournament.class, id);
            if (tournament != null) {
                entityManager.remove(tournament);
                logger.info("Tournament deleted with ID: {}", id);
            } else {
                logger.warn("No tournament found with ID: {}", id);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for deleting tournament with ID: {}", id, e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
