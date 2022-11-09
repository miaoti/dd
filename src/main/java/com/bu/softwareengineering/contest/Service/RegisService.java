package com.bu.softwareengineering.contest.Service;

import com.bu.softwareengineering.contest.controller.ProductNotfoundException;
import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.TeamMember;
import com.bu.softwareengineering.contest.domain.enumeration.PersonType;
import com.bu.softwareengineering.contest.domain.enumeration.State;
import com.bu.softwareengineering.contest.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.bu.softwareengineering.contest.domain.ContestTeam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Service
public class RegisService {
    @Autowired
    ContestRepository contestRepository;
    @Autowired
    TeamRepository teamRepository;
    private EntityManager entityManager;
    @Autowired
    ContestTeamRepository contestteamRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ContestManagerRepository contestManagerRepository;

    public void RefreashCapacity(){
        List<Contest> contests = contestRepository.findAll();
        for(Contest i : contests){
            int capacity = 0;
            //Get capacity for all teams
            Set<ContestTeam> teams = i.getContestTeams();
            for(ContestTeam contestTeamteam : teams){
                Team team = contestTeamteam.getTeam();
                if(team.getCloneOf()!=null){
                    continue;
                }
                Set<TeamMember> members = team.getTeamMembers();
                for(TeamMember teamMember : members){
                    capacity++;
                }
            }

            //Get capacity for subContest
//            Set<Contest> contestSet = i.getContests();
//            for(Contest contest : contestSet){
//                Set<ContestTeam> subteams = contest.getContestTeams();
//                for(ContestTeam contestTeamteam : subteams){
//                    Team team = contestTeamteam.getTeam();
//                    Set<TeamMember> members = team.getTeamMembers();
//                    for(TeamMember teamMember : members){
//                        capacity++;
//                    }
//                }
//            }
            i.setCurrent_capacity(capacity);
            contestRepository.save(i);
        }
    }

    public Map Team_setEditable(JsonNode file){
        Map respose = new HashMap();
        Long contest_id = file.get("team").asLong();
        Team team = teamRepository.findById(contest_id).get();
        team.setWritable(true);
        teamRepository.save(team);
        respose.put("success", "Team " + team.getName() + " is writable now.");
        return respose;
    }

    public Map Team_setReadOnly(JsonNode file){
        Map respose = new HashMap();
        Long contest_id = file.get("team").asLong();
        Team team = teamRepository.findById(contest_id).get();
        team.setWritable(false);
        teamRepository.save(team);
        respose.put("success", "Team " + team.getName() + " is read only now.");
        return respose;
    }



    public Map Contest_setEditable(String contestid){
        Map respose = new HashMap();
        Long contest_id = Long.parseLong(contestid);
        Contest contest = contestRepository.findById(contest_id).get();
        contest.setWritable(true);

        respose.put("success",  contestRepository.save(contest));
        return respose;
    }

    public Map Contest_setReadOnly(String contestid){
        Map respose = new HashMap();
        Long contest_id = Long.parseLong(contestid);
        Contest contest = contestRepository.findById(contest_id).get();
        contest.setWritable(false);

        respose.put("success", contestRepository.save(contest));
        return respose;
    }

    public Map editTeam(Team team){
        Map respose = new HashMap();
        Long team_id = team.getId();
        Team oldteam = teamRepository.findById(team_id).get();

        Set<ContestTeam> contests = oldteam.getContestTeams();
        boolean writable = true;
        for(ContestTeam i : contests){
            if(!i.getContest().getWritable()){

                respose.put("error","This team is not editable because of its contests");
                writable = false;
                break;
            }
        }
        List<Contest> contestSet = contestRepository.findAll();
        for(Contest i : contestSet){
            Set<ContestTeam> teamin = i.getContestTeams();
            for(ContestTeam j : teamin){
                if(j.getTeam().getId() == oldteam.getId() && !i.getWritable()){
                    writable = false;
                }
            }
        }
        if(!writable){
            return respose;
        }

        List<Team> allteams = teamRepository.findAll();
        List<String> names = new ArrayList<>();
        for(Team i : allteams){
            names.add(i.getName());
        }
        if(names.contains(team.getName())){
            respose.put("error","This team name is used by other teams.");
            return respose;
        }
        if (team.getName()!=null)
        oldteam.setName(team.getName());
        if (team.getCoach()!=null)
        oldteam.setCoach(team.getCoach());
        if (team.getContestTeams()!=null)
        oldteam.setContestTeams(team.getContestTeams());
        if (team.getRank()!=null)
        oldteam.setRank(team.getRank());
        if (team.getState()!=null)
        oldteam.setState(team.getState());
        if (team.getTeamMembers()!=null)
        oldteam.setTeamMembers(team.getTeamMembers());


        respose.put("success","Your edit is successful.");
        respose.put("success",teamRepository.save(oldteam));
//        respose.put("TeamName: ",oldteam.getName());
//        respose.put("TeadId: ",oldteam.getId());
//        respose.put("TeamRank:",oldteam.getRank());
//        Set<ContestTeam> contestTeams = oldteam.getContestTeams();
//        for(ContestTeam contestTeam : contestTeams){
//        respose.put("Team's ContestId:",contestTeam.getContest().getId());
//        }
//
//        for(Contest i : contestSet){
//            Set<ContestTeam> teamin = i.getContestTeams();
//            for(ContestTeam j : teamin){
//                if(j.getTeam().getId() == oldteam.getId()){
//                    respose.put("TeamContestId:",i.getId());
//                }
//            }
//        }
//        respose.put("TeamCoachId:",oldteam.getCoach().getId());
//        RefreashCapacity();
        return respose;
    }

    public Map editContest(Contest contest){
        Map respose = new HashMap();
        Long contest_id = contest.getId();
        Contest oldcontest = contestRepository.findById(contest_id).get();
        if(oldcontest.getWritable()==false){
            respose.put("error","This contest is not writable.");
            return respose;
        }
        oldcontest.setContests(contest.getContests());
        List<Contest> allteams = contestRepository.findAll();
        List<String> names = new ArrayList<>();
        for(Contest i : allteams){
            names.add(i.getName());
        }
        if(names.contains(contest.getName())){
            respose.put("error","This team name is used by other teams.");
            return respose;
        }
        oldcontest.setName(contest.getName());
        oldcontest.setCurrent_capacity(contest.getCurrent_capacity());
        oldcontest.setContestManagers(contest.getContestManagers());
        oldcontest.setCapacity(contest.getCapacity());
        oldcontest.setContestTeams(contest.getContestTeams());
        if(contest.getParent() != null)
        oldcontest.setParent(contest.getParent());
        oldcontest.setDate(contest.getDate());
        oldcontest.setRegistrationAllowed(contest.getRegistrationAllowed());
        oldcontest.setRegistrationFrom(contest.getRegistrationFrom());
        oldcontest.setRegistrationTo(contest.getRegistrationTo());

        respose.put("success",contestRepository.save(oldcontest));
        RefreashCapacity();
        return respose;
    }

    public Map Regis(JsonNode file,String contestId) throws ParseException {
        Map respose = new HashMap();
        List<String> result = new ArrayList<>();

        ArrayNode arrayNode = (ArrayNode) file.get("members");
        Contest contest = contestRepository.findById(Long.parseLong(contestId)).get();
        Set<ContestTeam> contestTeam = contest.getContestTeams();
        List<Long> ids = new ArrayList<>();
        List<TeamMember> teamMembersList = new ArrayList<>();
        Team newTeam = new Team();
        teamRepository.save(newTeam);
//        contest.setName("CONTEST-001-SUB2\t");
//        contestRepository.save(contest);
        int team_size = 0;

        //To check if the contest is able to edit
        if(!contest.getWritable()){
            teamRepository.delete(newTeam);
            respose.put("error","Contest " + contest.getName() + " is read only!");
            return respose;
            //throw new ProductNotfoundException();
        }
//        System.out.println(arrayNode.get(0).get("id"));

        //Find if duplicate team name
        String teamname = file.get("name").asText();
        List<Team> teams = teamRepository.findAll();
        List<Long> allMember = new ArrayList<>();
        List<String> teamnames = new ArrayList<>();
        List<Person> PersonList = new ArrayList<>();
        for (Team i : teams){
            teamnames.add(i.getName());
        }
        if(teamnames.contains(teamname)){
            System.out.println("Duplicated team name");
            teamRepository.delete(newTeam);
            respose.put("error","Duplicated team name");
            return respose;
            //throw new ProductNotfoundException();
        }
        newTeam.setName(file.get("name").asText());
        Set<ContestTeam> contestTeamsteams = contest.getContestTeams();
        for(ContestTeam i : contestTeamsteams){
            Set<TeamMember> member = i.getTeam().getTeamMembers();
            for(TeamMember j : member){
                allMember.add(j.getPerson().getId());
            }
        }

        //For each team member
        for(JsonNode i : arrayNode){
            //Create a new person
            if(i.get("id")==null){
                Person teamMem = new Person();
                teamMem.setName(i.get("name").asText());
                teamMem.setType(PersonType.STUDENT);
                teamMem.setEmail(i.get("email").asText());
                String bir = i.get("birthdate").asText();
                bir = method(bir);
                System.out.println(bir);
                Date birthday = new SimpleDateFormat("yyyy–MM–dd").parse(bir);
                LocalDate date = convertToLocalDateViaMilisecond(birthday);
                //Age is more than 24
                if(calculateAge(date) > 24){
                    System.out.println("Age>24");
                    teamRepository.delete(newTeam);
                    respose.put("error",i.get("name").asText()+" Age>24");
                    return respose;
                    //throw new ProductNotfoundException();

                }
                teamMem.setBirthday(birthday);
                teamMem.setUniversity(i.get("university").asText());
                // Save this person
                //personRepository.save(teamMem);
                PersonList.add(teamMem);
                TeamMember teamMember = new TeamMember();
                teamMember.setPerson(teamMem);
                teamMember.setTeam(newTeam);
                // Add to this person to team
                //teamMemberRepository.save(teamMember);
                teamMembersList.add(teamMember);
                team_size++;
            }
            else{
                //Duplicated member in other team
                if(ids.contains(i.get("id").asLong())){
                    System.out.println("Duplicated member");
                    teamRepository.delete(newTeam);
                    respose.put("error","Duplicated member");
                    return respose;
                    //throw new ProductNotfoundException();
                }

                if(allMember.contains(i.get("id").asLong())){
                    System.out.println(i.get("id").asLong()+" in another team");
                    teamRepository.delete(newTeam);
                    respose.put("error",i.get("id").asLong()+" in another team");
                    return respose;
                    //throw new ProductNotfoundException();
                }
                ids.add(i.get("id").asLong());
                Person person = personRepository.findById(i.get("id").asLong()).get();
                Date birthday = person.getBirthday();
                LocalDate date = convertToLocalDateViaMilisecond(birthday);
                //Age is more than 24
                if(calculateAge(date)>24) {
                    teamRepository.delete(newTeam);
                    respose.put("error","Age > 24");
                    return respose;
                    //throw new ProductNotfoundException();
                }
                TeamMember teamMember = new TeamMember();
                teamMember.setPerson(person);
                teamMember.setTeam(newTeam);
                //teamMemberRepository.save(teamMember);
                teamMembersList.add(teamMember);
                team_size++;
            }
        }

        JsonNode coachInfo = file.get("coach");
        if(coachInfo.get("id") == null){
        Person coach = new Person();
        coach.setType(PersonType.COACH);
        coach.setUniversity(coachInfo.get("university").asText());
        coach.setEmail(coachInfo.get("email").asText());
        Set<Team> co = new HashSet<>();
        co.add(newTeam);
        coach.setTeams(co);
        coach.setName(coachInfo.get("name").asText());
        String bir = coachInfo.get("birthdate").asText();

            Date birthday = new SimpleDateFormat("yyyy–MM–dd").parse(bir);
            coach.setBirthday(birthday);
        //personRepository.save(coach);
            PersonList.add(coach);
        newTeam.setCoach(coach);
        }
        else {
            if (ids.contains(coachInfo.get("id").asLong())) {
                teamRepository.delete(newTeam);
                respose.put("error","Not distinct");
                return respose;
                //throw new ProductNotfoundException();
            }
            Person coach = personRepository.findById(coachInfo.get("id").asLong()).get();
            Set<Team> co = coach.getTeams();
            co.add(newTeam);
            coach.setTeams(co);
            //personRepository.save(coach);
            PersonList.add(coach);
            newTeam.setCoach(coach);
        }

        int current = contest.getCurrent_capacity()+team_size;
        int max = contest.getCapacity();

        if(current>max){
            teamRepository.delete(newTeam);
            respose.put("errpr","Overflow capacity");
            return respose;
            //throw new ProductNotfoundException();
        }
        ContestTeam contestTeam1 = new ContestTeam();
        contestTeam1.setTeam(newTeam);
        contestTeam1.setContest(contest);
        newTeam.setState(State.PENDING);
        if(file.get("rank")!=null)
        newTeam.setRank(file.get("rank").asInt());
        else newTeam.setRank(null);
        for(Person i : PersonList){
            personRepository.save(i);
        }
        for(TeamMember i : teamMembersList){
            teamMemberRepository.save(i);
        }
        respose.put("Team",teamRepository.save(newTeam));


        respose.put("success", "Team are registed successfully into contest");
        contestteamRepository.save(contestTeam1);
        List<ContestTeam> helper = contestteamRepository.findAll();

        for(ContestTeam i : helper){
            String str = "Contest id: " + i.getContest().getId().toString() + "    Team id: " + i.getTeam().getId() + "    Team name: " + i.getTeam().getName();
            result.add(str);
        }
        respose.put("Contest and team",result);

        return respose;
    }


    public Map promote(String superContestId, String teamId){
        Map respose = new HashMap();
        Long contest_id = Long.parseLong(superContestId);
        Long team_id = Long.parseLong(teamId);
        Contest contest = contestRepository.findById(contest_id).get();
        Set<ContestTeam> contestTeam = contest.getContestTeams();
        List<Long> ids = new ArrayList<>();
        List<Long> people = new ArrayList<>();

//        contest.setName("CONTEST-001-SUB2\t");
//        contestRepository.save(contest);
        int team_size = 0;

            Team a = teamRepository.findById(team_id).get();
            people.add(a.getCoach().getId());
            for (ContestTeam contestTeam1 : contestTeam){
                ids.add(contestTeam1.getTeam().getId());
            }
            if(ids.contains(team_id)){
                respose.put("error","This team is duplicated.");
                return respose;
            }
            List<Long> in_inContest = new ArrayList<>();
            Set<ContestTeam> teams = contest.getContestTeams();
            for(ContestTeam contestTeamteam : teams){
                Team team = contestTeamteam.getTeam();
                Set<TeamMember> members = team.getTeamMembers();
                for(TeamMember teamMember : members){
                    long id = teamMember.getPerson().getId();
                    in_inContest.add(id);
                }
            }

            for(ContestTeam i : contestTeam){
                Team team = i.getTeam();
                if(team.getId() == a.getId()){
                    String teamname = a.getName();
                    String contestname = contest.getName();
                    respose.put("erroMesage ", teamname + " is already in" + contestname);
                    return respose;
                }
            }

            Set<TeamMember> a_member = a.getTeamMembers();
            //Less than 24
            for(TeamMember i : a_member){
                if(people.contains(i.getPerson().getId())){
                    respose.put("erroMesage ", i.getPerson().getName() + " is duplicated");
                    return respose;
                }
                if(in_inContest.contains(i.getPerson().getId())){
                    respose.put("erroMesage ", i.getPerson().getName() + " is in another team in this contest");
                    return respose;
                }
                team_size++;
                people.add(i.getPerson().getId());
                Date birthday = i.getPerson().getBirthday();
                LocalDate date = convertToLocalDateViaMilisecond(birthday);
                if(calculateAge(date)>24){
                    respose.put("erroMesage", "TeamMember " + i.getPerson().getName() + " is more than 24");
                    return respose;
                }
            }

            int capacity = contest.getCapacity();
            int newcapa = contest.getCurrent_capacity()+team_size;
            if(newcapa>capacity){
                respose.put("erroMesage", "Capacity of the contest in overflowed");
                return respose;
            }


            ContestTeam newer = new ContestTeam();
            /*Team newteam = new Team();
            newteam.setRank(a.getRank());
            newteam.setWritable(a.getWritable());
            newteam.setCoach(a.getCoach());
            newteam.setState(a.getState());
            newteam.setId(null);
            newteam.setTeamMembers(a.getTeamMembers());
            newteam.setContestTeams(a.getContestTeams());
            newteam.setName(a.getName());
            newer.setTeam(newteam);
            newer.setContest(contest);*/
            if(a.getRank()>5){
                respose.put("error","This team's rank is lower than 5.");
                return respose;
            }
            newer.setContest(contest);
            newer.setTeam(a);
            contestteamRepository.save(newer);
            Team promotedTeam = new Team();
            BeanUtils.copyProperties(a,promotedTeam);
            promotedTeam.setCloneOf(a.getId());
            promotedTeam.setId(null);
            promotedTeam.setTeamMembers(null);
            promotedTeam.setContestTeams(null);


            //RefreashCapacity();
            team_size=0;
            people = new ArrayList<>();














//         if ///error
//        respose.put("erroMesage", "Registernnjkfnv");
//
//         if/// success
//
        respose.put("success", teamRepository.save(promotedTeam));



        return respose;
    }



    public int calculateAge(LocalDate dob){

        LocalDate curDate = LocalDate.now();
        if ((dob != null) && (curDate != null))
        {
            return Period.between(dob, curDate).getYears();
        }
        else
        {
            return 0;
        }
    }
    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
