package com.bu.softwareengineering.contest.controller;

import com.bu.softwareengineering.contest.Service.RegisService;
import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.domain.ContestManager;
import com.bu.softwareengineering.contest.domain.ContestTeam;
import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.repository.ContestManagerRepository;
import com.bu.softwareengineering.contest.repository.ContestRepository;
import com.bu.softwareengineering.contest.repository.ContestTeamRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@Transactional
public class ContestController {

    private final Logger log = LoggerFactory.getLogger(ContestController.class);

    @Autowired
    ContestRepository contestRepository;
    @Autowired
    ContestTeamRepository teamRepository;
    @Autowired
    ContestManagerRepository contestManagerRepository;

    @Autowired
    RegisService  regisService;

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Contest> getCoursesBySize(){
        return new ResponseEntity(contestRepository.findAll(),HttpStatus.OK);
    }


    @PostMapping("/contests")
    public ResponseEntity<Person> createContest(@RequestBody Contest contest) {

        Contest result = contestRepository.save(contest);
        for (ContestTeam ct :contest.getContestTeams()){
            ContestTeam team = new ContestTeam();
            team.setContest(result);
            team.setTeam(ct.getTeam());
            teamRepository.save(team);
        }
        ContestManager contestManager = new ContestManager();
        contestManager.setContest(contest);
        for (ContestManager cm : contest.getContestManagers()){
            contestManager.setPerson(cm.getPerson());
        }
        contestManagerRepository.save(contestManager);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }


    @PostMapping("/contestsRegistration")
        public ResponseEntity<?> regisForContest(@RequestBody JsonNode file,@RequestParam(value = "contestId") String contestId) throws ParseException {
            //regisService.RefreashCapacity();
            return new ResponseEntity(regisService.Regis(file,contestId), HttpStatus.OK);
        }

    @PostMapping("/setEditable")
    public ResponseEntity<Person> setEditable(@RequestParam(value = "contestId") String contestId){
        //regisService.RefreashCapacity();
        return new ResponseEntity(regisService.Contest_setEditable(contestId), HttpStatus.OK);
    }
    @PostMapping("/setReadOnly")
    public ResponseEntity<Person> setReadOnly(@RequestParam(value = "contestId") String contestId){
        //regisService.RefreashCapacity();
        return new ResponseEntity(regisService.Contest_setReadOnly(contestId), HttpStatus.OK);
    }

    @PostMapping("/contest")
    public ResponseEntity<Person> editContest(@RequestBody Contest contest){
        //regisService.RefreashCapacity();
        return new ResponseEntity(regisService.editContest(contest), HttpStatus.OK);
    }



    @GetMapping("/refresh")
    public void refresh(){
        regisService.RefreashCapacity();
    }
    @GetMapping("/contest")
    public ResponseEntity<?> getAllContests() {
        log.debug("REST request to get all Contests");
        return new ResponseEntity(contestRepository.findAll(), HttpStatus.OK);
        //return contestRepository.findAll();
    }


}
