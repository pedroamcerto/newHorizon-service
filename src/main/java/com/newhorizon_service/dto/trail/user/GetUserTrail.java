package com.newhorizon_service.dto.trail.user;

import com.newhorizon_service.dto.competence.GetSimpleCompetence;
import com.newhorizon_service.dto.course.GetSimpleCourse;
import com.newhorizon_service.dto.position.GetSimplePosition;
import lombok.Data;

import java.util.List;

@Data
public class GetUserTrail {
    private String id;
    private String name;
    private List<GetSimpleCourse> courses;
    private List<GetSimpleCompetence> competences;
    private List<GetSimplePosition> positions;
}
