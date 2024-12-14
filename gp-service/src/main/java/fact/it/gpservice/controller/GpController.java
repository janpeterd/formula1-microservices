package fact.it.gpservice.controller;

import fact.it.gpservice.dto.GpRequest;
import fact.it.gpservice.dto.GpResponse;
import fact.it.gpservice.service.GpService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gp")
@RequiredArgsConstructor
public class GpController {
    private final GpService gpService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<GpResponse> getAllGps() {
        return gpService.getAllGps();
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createGp(@RequestBody GpRequest gpRequest) {
        gpService.createGp(gpRequest);
    }
}
