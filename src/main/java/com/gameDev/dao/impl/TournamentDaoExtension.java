package com.gameDev.dao.impl;

import com.gameDev.dao.TournamentDao;
import com.gameDev.entity.Game;
import com.gameDev.entity.Tournament;
import com.gameDev.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class TournamentDaoExtension implements TournamentDao {
    private final TournamentDaoImpl tournamentDaoImpl;

    public TournamentDaoExtension(TournamentDaoImpl tournamentDaoImpl) {
        this.tournamentDaoImpl = tournamentDaoImpl;  // Composition: Use the base implementation
    }

    // Delegate the core methods to TournamentDaoImpl
    @Override
    public int saveTournament(Tournament tournament) {
        return tournamentDaoImpl.saveTournament(tournament);  // Reuse the implementation
    }

    @Override
    public Tournament getTournamentById(int id) {
        return tournamentDaoImpl.getTournamentById(id);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentDaoImpl.getAllTournaments();
    }

    @Override
    public void updateTournament(Tournament tournament) {
        tournamentDaoImpl.updateTournament(tournament);
    }

    @Override
    public void deleteTournament(int id) {
        tournamentDaoImpl.deleteTournament(id);
    }

    @Override
    public void assignTeamToTournament(int tournamentId, int teamId) {
        tournamentDaoImpl.assignTeamToTournament(tournamentId, teamId);
    }

    @Override
    public void removeTeamFromTournament(int tournamentId, int teamId) {
        tournamentDaoImpl.assignTeamToTournament(tournamentId, teamId);
    }

    @Override
    public double calculateEstimatedDurationTournament(int tournamentId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Tournament tournament = em.find(Tournament.class, tournamentId);
            if (tournament == null) {
                throw new IllegalArgumentException("Tournament not found with ID: " + tournamentId);
            }
            Game game = tournament.getGame();
            int numberOfTeams = tournament.getTeams().size();
            double difficulty = game.getDifficulty();
            double matchDuration = game.getMatchAvgDuration();
            double breakTime = tournament.getBreakTime();
            double ceremonyTime = tournament.getCeremonyTime();

            double estimatedDuration = (numberOfTeams * matchDuration * difficulty) + breakTime + ceremonyTime;

            return estimatedDuration;
        } finally {
            em.close();
        }
    }

}
