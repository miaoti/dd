package com.bu.softwareengineering.contest.repository;

import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TeamMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    public List<TeamMember> getTeamMemberByTeam(Team team);
    public Optional<TeamMember> getByPerson(Person person);
}
