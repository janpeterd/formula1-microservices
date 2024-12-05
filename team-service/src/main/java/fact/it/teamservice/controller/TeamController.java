package fact.it.teamservice.controller;

import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
  private final TeamService teamService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void createTeam(@RequestBody TeamRequest teamRequest) {
    teamService.createTeam(teamRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TeamResponse> getAllTeams() {
    return teamService.getAllTeams();
  }

  @PostMapping("/add-driver")
  public void addDriverToTeam(@RequestBody String driverCode, @RequestBody String teamCode) {
    teamService.addDriverToTeam(driverCode, teamCode);
  }

  @GetMapping("/by-id")
  @ResponseStatus(HttpStatus.OK)
  public List<TeamResponse> getAllTeamsByTeamCode(@RequestParam List<String> teamCode) {
    return teamService.getAllTeamsByTeamCode(teamCode);
  }
}
