package com.gameDev.service.impl;

import com.gameDev.dao.TournamentDao;
import com.gameDev.entity.Team;
import com.gameDev.entity.Tournament;
import com.gameDev.service.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TournamentServiceImpl implements TournamentService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);
    private final TournamentDao tournamentDao;

    // Constructor for dependency injection
    public TournamentServiceImpl(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    @Override
    public void createTournament(Tournament tournament) {
        tournamentDao.saveTournament(tournament);
        logger.info("Tournament created: {}", tournament.getTitle());
    }

    @Override
    public Tournament getTournamentById(int id) {
        Tournament tournament = tournamentDao.getTournamentById(id);
        logger.info("Fetched tournament with ID: {}", id);
        return tournament;
    }

    @Override
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = tournamentDao.getAllTournaments();
        logger.info("Fetched all tournaments. Total count: {}", tournaments.size());
        return tournaments;
    }

    @Override
    public void updateTournament(Tournament tournament) {
        tournamentDao.updateTournament(tournament);
        logger.info("Tournament updated: {}", tournament.getTitle());
    }

    @Override
    public void deleteTournament(int id) {
        tournamentDao.deleteTournament(id);
        logger.info("Tournament deleted with ID: {}", id);
    }

    @Override
    public void assignTeamToTournament(int tournamentId, int team) {
        tournamentDao.assignTeamToTournament(tournamentId, team);
    }

    @Override
    public void removeTeamFromTournament(int tournamentId, int team) {
        tournamentDao.removeTeamFromTournament(tournamentId, team);
    }
}
