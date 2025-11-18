package com.newhorizon_service.dto.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionMatchDto {
    private String positionId;
    private String positionName;
    private Integer matchingCompetences;
    private List<String> competenceNames;
}
