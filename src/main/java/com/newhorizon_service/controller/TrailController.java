package com.newhorizon_service.controller;

import com.newhorizon_service.dto.trail.CreateTrailDto;
import com.newhorizon_service.dto.trail.GetTrailDto;
import com.newhorizon_service.service.TrailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trails")
@RequiredArgsConstructor
public class TrailController {

    private final TrailService trailService;

    @PostMapping
    public ResponseEntity<Void> createTrail(@Valid @RequestBody CreateTrailDto dto) {
        trailService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetTrailDto>> getAllTrails() {
        return ResponseEntity.ok(trailService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTrailDto> getTrailById(@PathVariable String id) {
        return ResponseEntity.ok(trailService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTrail(@PathVariable String id, @Valid @RequestBody CreateTrailDto dto) {
        trailService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrail(@PathVariable String id) {
        trailService.delete(id);
        return ResponseEntity.ok().build();
    }
}
