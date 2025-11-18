package com.newhorizon_service.dto.analysis;

import com.newhorizon_service.dto.competence.GetSimpleCompetence;
import com.newhorizon_service.dto.position.GetSimplePosition;
import com.newhorizon_service.dto.trail.GetSimpleTrail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnalysisDto {
    private String userId;
    private String userName;
    private String email;
    private List<GetSimpleCompetence> competences;
    private List<GetSimpleTrail> enrolledTrails;
    private Integer totalCompetences;
    private Integer hardSkills;
    private Integer softSkills;
}
