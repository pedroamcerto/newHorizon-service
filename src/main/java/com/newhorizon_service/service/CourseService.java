package com.newhorizon_service.service;

import com.newhorizon_service.dto.course.CreateCourseDto;
import com.newhorizon_service.dto.course.GetCourseDto;
import com.newhorizon_service.model.Course;
import com.newhorizon_service.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;
    private final ModelMapper modelMapper;

    public void create(CreateCourseDto dto) {
        Course course = modelMapper.map(dto, Course.class);
        repository.save(course);
    }

    public void update(String id, CreateCourseDto dto) {
        Course existingCourse = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.")
        );

        Course updatedCourse = modelMapper.map(dto, Course.class);

        // Atualiza os campos do curso existente com os valores do DTO
        existingCourse.setName(updatedCourse.getName());

        repository.save(existingCourse);
    }

    public List<GetCourseDto> getAll() {
        // Busca todos cursos no banco de dados
        List<Course> courses = repository.findAll();

        // Converte o objeto course em GetCourseDto e retorna;
        return courses.stream().map(
                course -> modelMapper.map(course, GetCourseDto.class)
        ).toList();
    }

    public GetCourseDto getById(String id) {
        Course course = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.")
        );

        return modelMapper.map(course, GetCourseDto.class);
    }

    public void delete(String id) {
        Course course = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.")
        );

        this.repository.delete(course);
    }
}
