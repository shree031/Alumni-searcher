package com.shree031.alumniSearcher.controller;

import com.shree031.alumniSearcher.dto.AlumniResponseDto;
import com.shree031.alumniSearcher.dto.AlumniSearchRequest;
import com.shree031.alumniSearcher.service.AlumniService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumni")
@RequiredArgsConstructor
public class AlumniController {

    private final AlumniService alumniService;

    @PostMapping("/search")
    public ResponseEntity<?> searchAlumni(@RequestBody @Valid AlumniSearchRequest request) {
        List<AlumniResponseDto> data = alumniService.searchAndSaveAlumni(request);
        return ResponseEntity.ok(Map.of("status", "success", "data", data));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAlumni() {
        List<AlumniResponseDto> data = alumniService.getAllAlumni();
        return ResponseEntity.ok(Map.of("status", "success", "data", data));
    }
}
