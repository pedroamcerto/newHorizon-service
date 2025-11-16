package com.newhorizon_service.dto.course;

import com.newhorizon_service.dto.trail.GetSimpleTrail;
import lombok.Data;

import java.util.List;

@Data
public class GetCourseDto {
    private String id;
    private String name;
    private List<GetSimpleTrail> trails;
}
