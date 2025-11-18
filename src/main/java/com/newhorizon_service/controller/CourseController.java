package com.newhorizon_service.controller;

import com.newhorizon_service.dto.course.CreateCourseDto;
import com.newhorizon_service.dto.course.GetCourseDto;
import com.newhorizon_service.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Void> createCourse(@Valid @RequestBody CreateCourseDto dto) {
        courseService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetCourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCourseDto> getCourseById(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable String id, @Valid @RequestBody CreateCourseDto dto) {
        courseService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.delete(id);
        return ResponseEntity.ok().build();
    }
}
