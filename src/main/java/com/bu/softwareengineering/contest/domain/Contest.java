package com.bu.softwareengineering.contest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contest.
 */
@Entity
@Table(name = "contest")
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "current_capacity")
    private Integer current_capacity;

    @Column(name = "date")
    private String date;

    @Column(name = "name")
    private String name;

    @Column(name = "registration_allowed")
    private Boolean registrationAllowed;

    @Column(name = "registration_from")
    private Date registrationFrom;

    @Column(name = "registration_to")
    private Date registrationTo;


    @Column(name = "writable")
    private boolean writable = true;

    @OneToMany(mappedBy = "parent",cascade = {CascadeType.ALL})
    private Set<Contest> contests = new HashSet<>();

    @OneToMany(mappedBy = "contest")
    private Set<ContestTeam> contestTeams = new HashSet<>();

    @OneToMany(mappedBy = "contest",cascade = {CascadeType.ALL})
    private Set<ContestManager> contestManagers  = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "contests", allowSetters = true)
    private Contest parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurrent_capacity(Integer integer) {
        this.current_capacity = integer;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCurrent_capacity() {
        return current_capacity;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRegistrationAllowed() {
        return registrationAllowed;
    }

    public void setRegistrationAllowed(Boolean registrationAllowed) {
        this.registrationAllowed = registrationAllowed;
    }

    public Date getRegistrationFrom() {
        return registrationFrom;
    }

    public void setRegistrationFrom(Date registrationFrom) {
        this.registrationFrom = registrationFrom;
    }

    public Date getRegistrationTo() {
        return registrationTo;
    }

    public void setRegistrationTo(Date registrationTo) {
        this.registrationTo = registrationTo;
    }

    public Set<Contest> getContests() {
        return contests;
    }

    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }

    public Set<ContestTeam> getContestTeams() {
        return contestTeams;
    }

    public void setContestTeams(Set<ContestTeam> contestTeams) {
        this.contestTeams = contestTeams;
    }

    public Set<ContestManager> getContestManagers() {
        return contestManagers;
    }

    public void setContestManagers(Set<ContestManager> contestManagers) {
        this.contestManagers = contestManagers;
    }

    public Contest getParent() {
        return parent;
    }

    public void setWritable(boolean writable){
        this.writable = writable;
    }

    public boolean getWritable(){
        return writable;
    }

    public void setParent(Contest parent) {
        this.parent = parent;
    }
}
