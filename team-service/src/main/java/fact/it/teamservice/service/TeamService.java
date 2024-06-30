package fact.it.teamservice.service;

import fact.it.teamservice.dto.*;
import fact.it.teamservice.model.Member;
import fact.it.teamservice.model.Team;

import java.util.List;

public interface TeamService {

    Team createTeam(TeamRequest teamRequest);
    List<TeamOnlyResponse> getAllTeams();
    TeamResponse findTeamByNumber(String teamNumber);
    void updateTeam(String teamNumber, UpdateTeamRequest teamRequest);
    void deleteTeamByNumber(String teamNumber);
    void deleteTeam(Long teamId);
    void addMembersToTeam(Long teamId, List<MemberToTeamRequest> memberToTeamRequest);


}
