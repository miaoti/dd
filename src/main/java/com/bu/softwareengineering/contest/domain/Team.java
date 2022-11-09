package com.bu.softwareengineering.contest.domain;


import com.bu.softwareengineering.contest.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_rank")
    private Integer rank;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "writable")
    private boolean writable = true;

    @Column(name = "clone_of")
    private Long cloneOf;


    @OneToMany(mappedBy = "team",cascade = {CascadeType.ALL})
    private Set<TeamMember> teamMembers = new HashSet<>();

    @OneToMany(mappedBy = "team",cascade = {CascadeType.ALL})
    private Set<ContestTeam> contestTeams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "teams", allowSetters = true)
    private Person coach;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Person getCoach() {
        return coach;
    }

    public void setCoach(Person coach) {
        this.coach = coach;
    }

    public Set<ContestTeam> getContestTeams() {
        return contestTeams;
    }

    public void setWritable(boolean writable){
        this.writable = writable;
    }

    public boolean getWritable(){
        return writable;
    }
    public void setContestTeams(Set<ContestTeam> contestTeams) {
        this.contestTeams = contestTeams;
    }

    public boolean isWritable() {
        return writable;
    }

    public Long getCloneOf() {
        return cloneOf;
    }

    public void setCloneOf(Long cloneOf) {
        this.cloneOf = cloneOf;
    }
}
