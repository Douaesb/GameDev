package com.gameDev.service;

import com.gameDev.entity.Team;

import java.util.List;

public interface TeamService {
    void createTeam(Team team);
    Team getTeamById(int id);
    List<Team> getAllTeams();
    void updateTeam(Team team);
    void deleteTeam(int id);
}
