package com.gameDev.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "game cannot be null")
    @Size(min = 2, max = 100, message = "Game name must be between 2 and 100 characters." )
    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @NotNull(message = "difficulty of game cannot be null")
    @Min(value = 0, message = "difficulty of game must be positive")
    @Column(name = "difficulty", nullable = false)
    private Double difficulty;

    @NotNull(message = "Match average duration cannot be null")
    @Min(value = 0, message = "Match average duration must be positive")
    @Column(name = "match_avg_duration", nullable = false)
    private Double matchAvgDuration;


    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Tournament> tournaments;

    // Constructors
    public Game() {
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public Game(String name, Double difficulty, Double matchAvgDuration) {
        this.name = name;
        this.difficulty = difficulty;
        this.matchAvgDuration = matchAvgDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public Double getMatchAvgDuration() {
        return matchAvgDuration;
    }

    public void setMatchAvgDuration(Double matchAvgDuration) {
        this.matchAvgDuration = matchAvgDuration;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", matchAvgDuration=" + matchAvgDuration +
                '}';
    }
}
