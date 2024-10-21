package com.gameDev.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Team name cannot be null")
    @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Min(value = 1, message = "Rank must be a positive integer")
    @Column(name = "rank", nullable = false)
    private int rank;

    // Constructors
    public Team() {
    }

    public Team(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    // Getters and Setters
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
