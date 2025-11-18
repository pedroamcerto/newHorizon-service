package com.newhorizon_service.controller;

import com.newhorizon_service.dto.analysis.*;
import com.newhorizon_service.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    /**
     * Analisa o fit do usuário com todas as posições disponíveis
     * GET /api/v1/analysis/users/{userId}/position-fit
     */
    @GetMapping("/users/{userId}/position-fit")
    public ResponseEntity<List<PositionFitDto>> analyzeUserPositionFit(@PathVariable String userId) {
        List<PositionFitDto> fits = analysisService.analyzeUserPositionFit(userId);
        return ResponseEntity.ok(fits);
    }

    /**
     * Obtém análise detalhada do perfil do usuário
     * GET /api/v1/analysis/users/{userId}/profile
     */
    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<UserAnalysisDto> getUserAnalysis(@PathVariable String userId) {
        UserAnalysisDto analysis = analysisService.getUserAnalysis(userId);
        return ResponseEntity.ok(analysis);
    }

    /**
     * Recomenda trilhas para o usuário baseado em gaps de competências
     * GET /api/v1/analysis/users/{userId}/trail-recommendations
     */
    @GetMapping("/users/{userId}/trail-recommendations")
    public ResponseEntity<List<TrailRecommendationDto>> recommendTrails(@PathVariable String userId) {
        List<TrailRecommendationDto> recommendations = analysisService.recommendTrails(userId);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * Lista posições compatíveis com uma trilha específica
     * GET /api/v1/analysis/trails/{trailId}/positions
     */
    @GetMapping("/trails/{trailId}/positions")
    public ResponseEntity<PositionsByTrailDto> getPositionsByTrail(@PathVariable String trailId) {
        PositionsByTrailDto positions = analysisService.getPositionsByTrail(trailId);
        return ResponseEntity.ok(positions);
    }

    /**
     * Busca usuários que têm fit com uma posição específica
     * GET /api/v1/analysis/positions/{positionId}/candidates?minFit=70
     */
    @GetMapping("/positions/{positionId}/candidates")
    public ResponseEntity<List<UserAnalysisDto>> getUsersByPositionFit(
            @PathVariable String positionId,
            @RequestParam(defaultValue = "70.0") Double minFit) {
        List<UserAnalysisDto> candidates = analysisService.getUsersByPositionFit(positionId, minFit);
        return ResponseEntity.ok(candidates);
    }
}
