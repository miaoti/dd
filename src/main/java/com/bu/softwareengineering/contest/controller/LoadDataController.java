package com.bu.softwareengineering.contest.controller;

import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.domain.TeamMember;
import com.bu.softwareengineering.contest.domain.enumeration.PersonType;
import com.bu.softwareengineering.contest.domain.enumeration.State;
import com.bu.softwareengineering.contest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Transactional
public class LoadDataController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping("/load-data")
    public void loadData(){

        List<Person> savedPersonList = new ArrayList<>();

        for(int i=0; i<10; i++){
            Person person = new Person();
            person.setUniversity("Baylor University");
            if(i%2 == 0){
                person.setName("test_std"+i);
                person.setEmail("test_std"+i+"@gmail.com");
                person.setBirthday(new Date());
                person.setType(PersonType.STUDENT);
            } else if (i%3 == 0) {
                person.setName("test_mng"+i);
                person.setEmail("test_mng"+i+"@gmail.com");
                person.setBirthday(new Date());
                person.setType(PersonType.MANAGER);
            }else {
                person.setName("test_coa"+i);
                person.setEmail("test_coa"+i+"@gmail.com");
                person.setBirthday(new Date());
                person.setType(PersonType.COACH);
            }
            personRepository.save(person);
            savedPersonList.add(person);
        }

        Team team1 = new Team();
        team1.setName("Baylor Bears");
        team1.setRank(1);
        team1.setState(State.ACCEPTED);
        team1.setCoach(savedPersonList.stream().filter(svp->"test_mng3".equals(svp.getName())).findAny().get());
        Set<TeamMember> teamMembers1 = new HashSet<>();
        ListIterator<Person> iterator = savedPersonList.listIterator();
        int i=0;
        while (iterator.hasNext()){
            i++;
            if (iterator.next().getType().equals(PersonType.STUDENT)){
                TeamMember teamMember = new TeamMember();
                teamMember.setPerson(iterator.next());
                iterator.remove();
            }
            if(i==3) break;
        }
        i=0;
        while (iterator.hasNext()){
            i++;
            if (iterator.next().getType().equals(PersonType.STUDENT)){
                TeamMember teamMember = new TeamMember();
                teamMember.setPerson(iterator.next());
                teamMembers1.add(teamMember);
                iterator.remove();
            }
            if(i==3) break;
        }

    }
}
