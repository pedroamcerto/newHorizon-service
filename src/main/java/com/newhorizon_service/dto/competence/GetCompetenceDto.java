package com.newhorizon_service.dto.competence;

import com.newhorizon_service.dto.position.GetSimplePosition;
import com.newhorizon_service.dto.trail.GetSimpleTrail;
import com.newhorizon_service.dto.user.GetSimpleUser;
import com.newhorizon_service.model.enums.CompetenceType;
import lombok.Data;

import java.util.List;

@Data
public class GetCompetenceDto {
    private String id;
    private String name;
    private CompetenceType type;
    private List<GetSimpleUser> users;
    private List<GetSimpleTrail> trails;
    private List<GetSimplePosition> positions;
}
