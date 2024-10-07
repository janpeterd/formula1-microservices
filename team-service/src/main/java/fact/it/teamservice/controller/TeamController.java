package fact.it.teamservice.controller;

import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import fact.it.teamservice.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    // TODO: somehow add drivers to a team! probably with an array of driver codes
    public void createTeam(@RequestBody TeamRequest teamRequest) {
        teamService.createTeam(teamRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TeamResponse> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping
    @Repository(HttpStatus.OK)
    public List<TeamResponse> getAllTeamsByTeamCode(@RequestParam List<String> teamCode) {
        return teamService.getAllTeamsByTeamCode(teamCode);
    }
}
