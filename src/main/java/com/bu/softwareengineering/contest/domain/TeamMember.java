package com.bu.softwareengineering.contest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A TeamMember.
 */
@Entity
@Table(name = "team_member")
public class TeamMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "teamMembers", allowSetters = true)
    private Person person;

    @ManyToOne
    @JsonIgnoreProperties(value = "teamMembers", allowSetters = true)
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public TeamMember person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Team getTeam() {
        return team;
    }

    public TeamMember team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


}
