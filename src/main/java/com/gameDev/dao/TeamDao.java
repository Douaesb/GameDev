package com.gameDev.dao;

import com.gameDev.entity.Team;

import java.util.List;

public interface TeamDao {
    void saveTeam(Team team);
    Team getTeamById(int id);
    List<Team> getAllTeams();
    void updateTeam(Team team);
    void deleteTeam(int id);

    void changePlayerTeam(int playerId, int newTeamId);
}
