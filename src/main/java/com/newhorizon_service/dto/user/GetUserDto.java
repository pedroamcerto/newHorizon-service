package com.newhorizon_service.dto.user;

import com.newhorizon_service.dto.competence.GetSimpleCompetence;
import com.newhorizon_service.dto.trail.user.GetUserTrail;
import lombok.Data;

import java.util.List;

@Data
public class GetUserDto {
    private String id;
    private String name;
    private String email;
    private List<GetSimpleCompetence> competences;
    private List<GetUserTrail> trails;
}
