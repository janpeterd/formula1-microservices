package fact.it.gpservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fact.it.gpservice.dto.GpRequest;
import fact.it.gpservice.dto.GpResponse;
import fact.it.gpservice.service.GpService;
import lombok.RequiredArgsConstructor;

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

    @GetMapping("/{gpCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GpResponse> getGpByCode(@PathVariable String gpCode) {
        GpResponse gp = gpService.getGp(gpCode);
        if (gp != null) {
            return new ResponseEntity<>(gp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{gpCode}")
    public ResponseEntity<GpResponse> createGp(@PathVariable String gpCode, @RequestBody GpRequest gpRequest) {
        try {

            GpResponse gp = gpService.updateGp(gpCode, gpRequest);
            return new ResponseEntity<>(gp, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createGp(@RequestBody GpRequest gpRequest) {
        gpService.createGp(gpRequest);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void deleteGp(@RequestParam String gpCode) {
        try {
            gpService.deleteGp(gpCode);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
