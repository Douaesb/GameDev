package com.gameDev.entity;

import com.gameDev.entity.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Tournament title cannot be null")
    @Size(min = 3, max = 100, message = "Tournament title must be between 3 and 100 characters")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "Start date cannot be null")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Min(value = 0, message = "Number of spectators must be positive")
    @Column(name = "num_spectators")
    private int numSpectators;

    @Min(value = 0, message = "Duration must be positive")
    @Column(name = "duration", nullable = false)
    private int duration;

    @Min(value = 0, message = "Break time must be positive")
    @Column(name = "break_time", nullable = false)
    private int breakTime;

    @Min(value = 0, message = "Ceremony time must be positive")
    @Column(name = "ceremony_time", nullable = false)
    private int ceremonyTime;

    @NotNull(message = "Tournament status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;


    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;


    @ManyToMany
    @JoinTable(
            name = "tournament_team",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;


    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // Constructors
    public Tournament() {
    }

    public Tournament(String title, LocalDate startDate, LocalDate endDate, int numSpectators, int duration, int breakTime, int ceremonyTime, Status status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numSpectators = numSpectators;
        this.duration = duration;
        this.breakTime = breakTime;
        this.ceremonyTime = ceremonyTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumSpectators() {
        return numSpectators;
    }

    public void setNumSpectators(int numSpectators) {
        this.numSpectators = numSpectators;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }

    public int getCeremonyTime() {
        return ceremonyTime;
    }

    public void setCeremonyTime(int ceremonyTime) {
        this.ceremonyTime = ceremonyTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
