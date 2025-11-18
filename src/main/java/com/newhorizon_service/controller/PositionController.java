package com.newhorizon_service.controller;

import com.newhorizon_service.dto.position.CreatePositionDto;
import com.newhorizon_service.dto.position.GetPositionDto;
import com.newhorizon_service.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<Void> createPosition(@Valid @RequestBody CreatePositionDto dto) {
        positionService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetPositionDto>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPositionDto> getPositionById(@PathVariable String id) {
        return ResponseEntity.ok(positionService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePosition(@PathVariable String id, @Valid @RequestBody CreatePositionDto dto) {
        positionService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable String id) {
        positionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
