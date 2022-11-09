package com.bu.softwareengineering.contest.controller;

import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.domain.TeamMember;
import com.bu.softwareengineering.contest.repository.TeamMemberRepository;
import com.bu.softwareengineering.contest.repository.TeamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.bu.softwareengineering.contest.Service.RegisService;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.*;

@RestController
@Transactional
public class TeamController {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    RegisService  regisService;

    @PostMapping("/teams")
    public ResponseEntity<?> createTeam(@RequestBody Team team) throws  JsonProcessingException {
        Map teamResponse = new HashMap();
        ObjectMapper objectMapper = new ObjectMapper();
        if(team.getTeamMembers().size()>3){
            teamResponse.put("hasError",true);
            teamResponse.put("errorMessage","Can't assign more than three member.");
            return new ResponseEntity(objectMapper.writeValueAsString(teamResponse),HttpStatus.OK);
        }

        Team result = teamRepository.save(team);
        Set<TeamMember> teamMembers = new HashSet<>();
        Iterator<TeamMember> iterator = team.getTeamMembers().iterator();
        while (iterator.hasNext()){
            Optional<TeamMember> existingPerson = teamMemberRepository.getByPerson(iterator.next().getPerson());
            if(existingPerson.isPresent()){
                iterator.remove();
            }else {
                TeamMember teamMember = new TeamMember();
                teamMember.setTeam(result);
                teamMember.setPerson(iterator.next().getPerson());
                teamMembers.add(teamMember);
            }
        }
        teamMemberRepository.saveAll(teamMembers);
        teamResponse.put("hasError",false);
        teamResponse.put("response",result);
        return new ResponseEntity(objectMapper.writeValueAsString(teamResponse), HttpStatus.OK);
    }

    @PostMapping("/team/editable")
    public ResponseEntity<?> setEditable(@RequestBody JsonNode file){
        //regisService.RefreashCapacity();
        return new ResponseEntity(regisService.Team_setEditable(file), HttpStatus.OK);
    }
    @PostMapping("/team/readonly")
    public ResponseEntity<?> setReadOnly(@RequestBody JsonNode file){
        //regisService.RefreashCapacity();
        return new ResponseEntity(regisService.Team_setReadOnly(file), HttpStatus.OK);
    }

    @PostMapping("/team")
    public ResponseEntity<?> editTeam(@RequestBody Team contest){
        //regisService.RefreashCapacity();
        return new ResponseEntity(regisService.editTeam(contest), HttpStatus.OK);
    }
    @PostMapping("/promote")
    public ResponseEntity<?> Promote(@RequestParam(value = "superContestId") String superContestId,@RequestParam(value = "teamId") String teamId){
        //regisService.RefreashCapacity();
        return new ResponseEntity(regisService.promote(superContestId,teamId), HttpStatus.OK);
    }

}
