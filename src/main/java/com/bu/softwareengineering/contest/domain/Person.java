package com.bu.softwareengineering.contest.domain;


import com.bu.softwareengineering.contest.domain.enumeration.PersonType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "university")
    private String university;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PersonType type;

    @OneToMany(mappedBy = "person",cascade = {CascadeType.ALL})
    private Set<TeamMember> teamMembers = new HashSet<>();


    @OneToMany(mappedBy = "coach",cascade = {CascadeType.ALL})
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "person",cascade = {CascadeType.ALL})
    private Set<ContestManager> contestManagers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public Set<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<ContestManager> getContestManagers() {
        return contestManagers;
    }

    public void setContestManagers(Set<ContestManager> contestManagers) {
        this.contestManagers = contestManagers;
    }
}
