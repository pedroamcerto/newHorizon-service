package com.newhorizon_service.dto.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrailRecommendationDto {
    private String trailId;
    private String trailName;
    private Double relevanceScore;
    private List<String> competencesGained;
    private List<String> relatedPositions;
    private String recommendation;
}
