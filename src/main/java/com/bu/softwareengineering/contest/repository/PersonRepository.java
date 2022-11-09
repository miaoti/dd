package com.bu.softwareengineering.contest.repository;


import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.enumeration.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value =
            "SELECT COUNT(pr.id) as count, pr.birthday as birthday, pr.type as type FROM Person pr WHERE pr.type='STUDENT' GROUP BY pr.birthday"
    )
    List<StudentAge> findAllStudentsAndGroupByAge();

    List<Person> findAllByType(PersonType type);
}
