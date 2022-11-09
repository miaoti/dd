package com.bu.softwareengineering.contest;

import com.bu.softwareengineering.contest.domain.*;
import com.bu.softwareengineering.contest.domain.enumeration.PersonType;
import com.bu.softwareengineering.contest.repository.ContestRepository;
import com.bu.softwareengineering.contest.repository.PersonRepository;
import com.bu.softwareengineering.contest.repository.StudentAge;
import com.bu.softwareengineering.contest.repository.TeamMemberRepository;
import org.hibernate.collection.internal.PersistentList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@SpringBootTest
class ApplicationTests {
	final int TOTALMEMBERINCONTEST = 0;
	@Autowired
	private ContestRepository contestRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	TeamMemberRepository teamMemberRepository;

//	@Test
//	@Transactional
//	void getAllTeamsInContest() {
//		Contest parentContest = contestRepository.findByName("CONTEST-001");
//		List<Contest> subContest = contestRepository.findAllByParent(parentContest);
//		System.out.println("Contest: "+parentContest.getName());
//		Iterator<Contest> contestIterator =subContest.listIterator();
//		while (contestIterator.hasNext())
//			contestIterator.next();
//		for (Contest cn : subContest){
//			System.out.println("-Sub-contest: "+cn.getName());
//			for (ContestTeam tm : cn.getContestTeams()){
//				System.out.println("--Team Name: "+tm.getTeam().getName());
//				System.out.println("--Team Rank: "+tm.getTeam().getRank());
//				System.out.println("--Team Sate: "+tm.getTeam().getState());
//			}
//		}
//	}
//	@Test
//	@Transactional
//	void getAllStudentByAgeGroup(){
//		List<Person> students = personRepository.findAllByType(PersonType.STUDENT);
//		LocalDate currentDate = LocalDate.now();
//		List<Long> ages = new ArrayList<>();
//		for (Person p : students){
//			long years = ChronoUnit.YEARS.between(p.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),currentDate);
//			ages.add(years);
//		}
//		Map map = ages.stream().collect(Collectors.groupingBy(c ->c , Collectors.counting()));
//		System.out.println("Student "+ "Age");
//		map.forEach((k , v ) -> System.out.println( v + "       "+ k ));
//
//	}

//	@Test
//	@Transactional
//	void getContestVacancy(){
//		Contest parentContest = contestRepository.findByName("CONTEST-001");
//		int totalCapacity = 0;
//		int totalOccupancy = 0;
//		List<Contest> subContest = contestRepository.findAllByParent(parentContest);
//		for (Contest cn : subContest){
//			totalCapacity+=cn.getCapacity();
//			for (ContestTeam tm : cn.getContestTeams()){
//				List<TeamMember>  teamMembers = teamMemberRepository.getTeamMemberByTeam(tm.getTeam());
//				totalOccupancy+=teamMembers.size();
//			}
//		}
//		System.out.println("Contest Capacity: "+totalCapacity+"  Total Occupancy: "+totalOccupancy);
//
//	}



}
