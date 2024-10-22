package com.gameDev.dao.impl;

import com.gameDev.dao.TeamDao;
import com.gameDev.entity.Player;
import com.gameDev.entity.Team;
import com.gameDev.util.JPAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class TeamDaoImpl implements TeamDao {

    private static final Logger logger = LoggerFactory.getLogger(TeamDaoImpl.class);

    @Override
    public void saveTeam(Team team) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(team);
            transaction.commit();
            logger.info("Team saved: {}", team.getName());
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for saving team: {}", team.getName(), e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Team getTeamById(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            Team team = entityManager.find(Team.class, id);
            logger.info("Retrieved team with ID: {}", id);
            return team;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Team> getAllTeams() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            List<Team> teams = entityManager.createQuery("FROM Team", Team.class).getResultList();
            logger.info("Retrieved {} teams from the database", teams.size());
            return teams;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateTeam(Team team) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(team);
            transaction.commit();
            logger.info("Team updated: {}", team.getName());
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for updating team: {}", team.getName(), e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteTeam(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Team team = entityManager.find(Team.class, id);
            if (team != null) {
                entityManager.remove(team);
                logger.info("Team deleted with ID: {}", id);
            } else {
                logger.warn("No team found with ID: {}", id);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for deleting team with ID: {}", id, e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void changePlayerTeam(int playerId, int newTeamId) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Player player = entityManager.find(Player.class, playerId);
            Team newTeam = entityManager.find(Team.class, newTeamId);
            Team currentTeam = player.getTeam();
            if (currentTeam != null) {
                currentTeam.getPlayers().remove(player);
            }
            player.setTeam(newTeam);
            newTeam.getPlayers().add(player);
            entityManager.merge(player);
            entityManager.merge(newTeam);
            if (currentTeam != null) {
                entityManager.merge(currentTeam);
            }
            transaction.commit();
            logger.info("Player with ID: {} reassigned to team: {}", playerId, newTeamId);
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                logger.error("Transaction rolled back for changing player team with ID: {}", playerId, e);
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }


}
