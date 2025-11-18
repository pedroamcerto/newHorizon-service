package com.newhorizon_service.dto.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionFitDto {
    private String positionId;
    private String positionName;
    private Double fitPercentage;
    private List<String> matchingCompetences;
    private List<String> missingCompetences;
    private Integer totalRequiredCompetences;
    private Integer userCompetences;
}
