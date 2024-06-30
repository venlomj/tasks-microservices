package fact.it.teamservice.controller;

import fact.it.teamservice.dto.*;
import fact.it.teamservice.exception.EntityNotFoundException;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.service.TeamService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private ModelMapper mapper;

    @PostConstruct
    private void initializeMapper() {
        mapper = new ModelMapper();
    }


    @PostMapping("/add")
    public ResponseEntity<String> createTeam(@RequestBody TeamRequest teamRequest) {
        try {
            Team createdTeam = teamService.createTeam(teamRequest);

            // Map the createdTeam entity to TeamResponse DTO using ModelMapper
            TeamResponse teamResponse = mapper.map(createdTeam, TeamResponse.class);

            return ResponseEntity.ok(teamResponse.toString());
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ResponseEntity.status(500).body("Error creating team: " + e.getMessage());
        }
    }

    @GetMapping("/get/all")
    public List<TeamOnlyResponse> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("/assign-members/{teamId}")
    public ResponseEntity<String> assignMembersToTeam(@PathVariable Long teamId, @RequestBody List<MemberRequest> memberRequests) {
        try {
            List<MemberToTeamRequest> memberToTeamRequests = convertToMemberToTeamRequests(memberRequests);
            teamService.addMembersToTeam(teamId, memberToTeamRequests);
            return ResponseEntity.ok("Members assigned to the team successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    private List<MemberToTeamRequest> convertToMemberToTeamRequests(List<MemberRequest> memberRequests) {
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<MemberToTeamRequest>>() {}.getType();
        return modelMapper.map(memberRequests, listType);
    }


    @GetMapping("/get/{teamNumber}")
    public TeamResponse findTeamByNumber(@PathVariable String teamNumber) {
        return teamService.findTeamByNumber(teamNumber);
    }

    @PutMapping("/update/{teamNumber}")
    public void updateTeam(@PathVariable String teamNumber, @RequestBody UpdateTeamRequest  teamRequest) {
        teamService.updateTeam(teamNumber, teamRequest);
    }

    @DeleteMapping("/deleteBy/{teamId}")
    public ResponseEntity<String> deleteTeamById(@PathVariable Long teamId) {
        try {
            teamService.deleteTeam(teamId);
            return new ResponseEntity<>("Team deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{teamNumber}")
    public void deleteTeamByNumber(@PathVariable String teamNumber) {
        teamService.deleteTeamByNumber(teamNumber);
    }
}
