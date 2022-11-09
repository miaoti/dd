package com.bu.softwareengineering.contest.repository;


import com.bu.softwareengineering.contest.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Contest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    public Contest findByName(String name);
//
//    @Query("SELECT u FROM Contest u) " +
//            "public List<Contest> getAll());

    public List<Contest> findAllByParent(Contest contest);
}
