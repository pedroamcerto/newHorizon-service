package com.newhorizon_service.dto.position;

import com.newhorizon_service.dto.competence.GetSimpleCompetence;
import com.newhorizon_service.dto.trail.GetSimpleTrail;
import lombok.Data;

import java.util.List;

@Data
public class GetPositionDto {
    private String id;
    private String name;
    private List<GetSimpleTrail> trails;
    private List<GetSimpleCompetence> competences;
}
