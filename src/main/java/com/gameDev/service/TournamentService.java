package com.gameDev.service;

import com.gameDev.entity.Tournament;

import java.util.List;

public interface TournamentService {
    void createTournament(Tournament tournament);
    Tournament getTournamentById(int id);
    List<Tournament> getAllTournaments();
    void updateTournament(Tournament tournament);
    void deleteTournament(int id);

}
