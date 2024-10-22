package com.gameDev.dao;

import com.gameDev.entity.Team;
import com.gameDev.entity.Tournament;

import java.util.List;

public interface TournamentDao {
    void saveTournament(Tournament tournament);
    Tournament getTournamentById(int id);
    List<Tournament> getAllTournaments();
    void updateTournament(Tournament tournament);
    void deleteTournament(int id);

    void assignTeamToTournament(int tournamentId, int team);

    void removeTeamFromTournament(int tournamentId, int team);
}
