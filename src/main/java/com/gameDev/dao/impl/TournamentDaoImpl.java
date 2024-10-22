package com.gameDev.dao.impl;

import com.gameDev.dao.TournamentDao;
import com.gameDev.entity.Game;
import com.gameDev.entity.Team;
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

    @Override
    public void assignTeamToTournament(int tournamentId, int teamId) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Tournament tournament = entityManager.find(Tournament.class, tournamentId);
            Team team = entityManager.find(Team.class, teamId);

            if (tournament == null) {
                logger.warn("Tournament with ID {} not found.", tournamentId);
                return;
            }

            if (team == null) {
                logger.warn("Team with ID {} not found.", teamId);
                return;
            }

            tournament.addTeam(team);
            entityManager.merge(tournament);
            transaction.commit();
            logger.info("Team with ID {} assigned to tournament with ID {}", teamId, tournamentId);

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back while assigning team with ID {} to tournament with ID {}: {}", teamId, tournamentId, e.getMessage());
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeTeamFromTournament(int tournamentId, int teamId) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Tournament tournament = entityManager.find(Tournament.class, tournamentId);
            Team team = entityManager.find(Team.class, teamId);

            if (tournament == null) {
                logger.warn("Tournament with ID {} not found.", tournamentId);
                return;
            }

            tournament.removeTeam(team);
            entityManager.merge(tournament);
            transaction.commit();
            logger.info("Team removed from tournament with ID {}", tournamentId);

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back while removing team from tournament with ID {}: {}", tournamentId, e.getMessage());
            }
            throw e; // Rethrow to handle it at a higher level
        } finally {
            entityManager.close();
        }
    }

    @Override
    public double calculateEstimatedDurationTournament(int tournamentId){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            Tournament tournament = em.find(Tournament.class, tournamentId);
            if (tournament == null) {
                throw new IllegalArgumentException("Tournament not found with ID: " + tournamentId);
            }
            Game game = tournament.getGame();
            int numberOfTeams = tournament.getTeams().size();
            double matchDuration = game.getMatchAvgDuration();
            double breakTime = tournament.getBreakTime();

            double estimatedDuration = (numberOfTeams * matchDuration) + breakTime;

            return estimatedDuration;
        }finally {
            em.close();
        }
    }


}
