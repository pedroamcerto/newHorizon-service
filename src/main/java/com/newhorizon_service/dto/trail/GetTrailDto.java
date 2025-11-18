package com.newhorizon_service.dto.trail;

import com.newhorizon_service.dto.competence.GetSimpleCompetence;
import com.newhorizon_service.dto.course.GetSimpleCourse;
import com.newhorizon_service.dto.position.GetSimplePosition;
import com.newhorizon_service.dto.user.GetSimpleUser;
import lombok.Data;

import java.util.List;

@Data
public class GetTrailDto {
    private String id;
    private String name;
    private List<GetSimpleCourse> courses;
    private List<GetSimpleUser> users;
    private List<GetSimpleCompetence> competences;
    private List<GetSimplePosition> positions;
}
