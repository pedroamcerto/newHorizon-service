package com.newhorizon_service.dto.competence;

import com.newhorizon_service.model.enums.CompetenceType;
import lombok.Data;

@Data
public class GetSimpleCompetence {
    private String id;
    private String name;
    private CompetenceType type;
}
