package com.newhorizon_service.dto.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionsByTrailDto {
    private String trailId;
    private String trailName;
    private List<PositionMatchDto> matchingPositions;
    private Integer totalPositions;
}
