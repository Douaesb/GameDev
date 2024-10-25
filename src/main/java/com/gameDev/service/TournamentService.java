package com.gameDev.service;

import com.gameDev.entity.Team;
import com.gameDev.entity.Tournament;

import java.util.List;

public interface TournamentService {
    int createTournament(Tournament tournament);
    Tournament getTournamentById(int id);
    List<Tournament> getAllTournaments();
    void updateTournament(Tournament tournament);
    void deleteTournament(int id);
    void assignTeamToTournament(int tournamentId, int team);

    void removeTeamFromTournament(int tournamentId, int team);

    double getEstimatedDurationTournament(int tournamentId);

}
