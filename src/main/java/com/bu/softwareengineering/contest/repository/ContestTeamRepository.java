package com.bu.softwareengineering.contest.repository;

import com.bu.softwareengineering.contest.domain.ContestTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestTeamRepository extends JpaRepository<ContestTeam,Long> {
}
