package com.gameDev.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Nickname cannot be null")
    @Size(min = 3, max = 30, message = "Nickname must be between 3 and 30 characters")
    @Column(name = "nickName", nullable = false, unique = true)
    private String nickName;

    @Min(value = 16, message = "Player must be at least 16 years old")
    @Max(value = 60, message = "Player age must be less than or equal to 60")
    @Column(name = "age", nullable = false)
    private int age;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    // Constructors
    public Player() {
    }

    public Player(String nickName, int age) {
        this.nickName = nickName;
        this.age = age;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + nickName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
