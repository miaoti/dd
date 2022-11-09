package com.bu.softwareengineering.contest.repository;

import com.bu.softwareengineering.contest.domain.ContestManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestManagerRepository extends JpaRepository<ContestManager,Long> {
}
