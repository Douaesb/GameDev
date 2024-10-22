package com.gameDev.service.impl;

import com.gameDev.dao.TeamDao;
import com.gameDev.dao.impl.TeamDaoImpl;
import com.gameDev.entity.Team;
import com.gameDev.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TeamServiceImpl implements TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public void createTeam(Team team) {
        logger.info("Creating team: {}", team.getName());
        teamDao.saveTeam(team);
    }

    @Override
    public Team getTeamById(int id) {
        logger.info("Fetching team with ID: {}", id);
        return teamDao.getTeamById(id);
    }

    @Override
    public List<Team> getAllTeams() {
        logger.info("Fetching all teams");
        return teamDao.getAllTeams();
    }

    @Override
    public void updateTeam(Team team) {
        logger.info("Updating team: {}", team.getName());
        teamDao.updateTeam(team);
    }

    @Override
    public void deleteTeam(int id) {
        logger.info("Deleting team with ID: {}", id);
        teamDao.deleteTeam(id);
    }

    @Override
    public void changePlayerTeam(int playerId, int newTeamId) {
        logger.info("Assign new team with ID: {} for the player with ID: {}", newTeamId, playerId);
        teamDao.changePlayerTeam(playerId, newTeamId);
    }
}
