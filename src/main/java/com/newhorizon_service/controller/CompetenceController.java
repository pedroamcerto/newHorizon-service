package com.newhorizon_service.controller;

import com.newhorizon_service.dto.competence.CreateCompetence;
import com.newhorizon_service.dto.competence.GetCompetenceDto;
import com.newhorizon_service.service.CompetenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/competences")
@RequiredArgsConstructor
public class CompetenceController {

    private final CompetenceService competenceService;

    @PostMapping
    public ResponseEntity<Void> createCompetence(@Valid @RequestBody CreateCompetence dto) {
        competenceService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetCompetenceDto>> getAllCompetences() {
        return ResponseEntity.ok(competenceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCompetenceDto> getCompetenceById(@PathVariable String id) {
        return ResponseEntity.ok(competenceService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCompetence(@PathVariable String id, @Valid @RequestBody CreateCompetence dto) {
        competenceService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable String id) {
        competenceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
