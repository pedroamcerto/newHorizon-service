package com.newhorizon_service.service;

import com.newhorizon_service.dto.course.CreateCourseDto;
import com.newhorizon_service.dto.course.GetCourseDto;
import com.newhorizon_service.model.Course;
import com.newhorizon_service.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private CreateCourseDto createCourseDto;
    private GetCourseDto getCourseDto;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId("1");
        course.setName("Java Básico");

        createCourseDto = new CreateCourseDto();
        createCourseDto.setName("Java Básico");

        getCourseDto = new GetCourseDto();
        getCourseDto.setId("1");
        getCourseDto.setName("Java Básico");
    }

    @Test
    void create_DeveCriarCursoComSucesso() {
        // Arrange
        when(modelMapper.map(createCourseDto, Course.class)).thenReturn(course);
        when(repository.save(any(Course.class))).thenReturn(course);

        // Act
        courseService.create(createCourseDto);

        // Assert
        verify(modelMapper).map(createCourseDto, Course.class);
        verify(repository).save(course);
    }

    @Test
    void update_DeveAtualizarCursoComSucesso() {
        // Arrange
        String courseId = "1";
        when(repository.findById(courseId)).thenReturn(Optional.of(course));
        when(modelMapper.map(createCourseDto, Course.class)).thenReturn(course);
        when(repository.save(any(Course.class))).thenReturn(course);

        // Act
        courseService.update(courseId, createCourseDto);

        // Assert
        verify(repository).findById(courseId);
        verify(modelMapper).map(createCourseDto, Course.class);
        verify(repository).save(course);
    }

    @Test
    void update_DeveLancarExcecaoQuandoCursoNaoEncontrado() {
        // Arrange
        String courseId = "999";
        when(repository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> courseService.update(courseId, createCourseDto));
        verify(repository).findById(courseId);
        verify(repository, never()).save(any(Course.class));
    }

    @Test
    void getAll_DeveRetornarListaDeCursos() {
        // Arrange
        List<Course> courses = Arrays.asList(course);
        when(repository.findAll()).thenReturn(courses);
        when(modelMapper.map(course, GetCourseDto.class)).thenReturn(getCourseDto);

        // Act
        List<GetCourseDto> result = courseService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getCourseDto.getId(), result.get(0).getId());
        assertEquals(getCourseDto.getName(), result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void getById_DeveRetornarCursoPorId() {
        // Arrange
        String courseId = "1";
        when(repository.findById(courseId)).thenReturn(Optional.of(course));
        when(modelMapper.map(course, GetCourseDto.class)).thenReturn(getCourseDto);

        // Act
        GetCourseDto result = courseService.getById(courseId);

        // Assert
        assertNotNull(result);
        assertEquals(getCourseDto.getId(), result.getId());
        assertEquals(getCourseDto.getName(), result.getName());
        verify(repository).findById(courseId);
    }

    @Test
    void getById_DeveLancarExcecaoQuandoCursoNaoEncontrado() {
        // Arrange
        String courseId = "999";
        when(repository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> courseService.getById(courseId));
        verify(repository).findById(courseId);
    }

    @Test
    void delete_DeveDeletarCursoComSucesso() {
        // Arrange
        String courseId = "1";
        when(repository.findById(courseId)).thenReturn(Optional.of(course));
        doNothing().when(repository).delete(course);

        // Act
        courseService.delete(courseId);

        // Assert
        verify(repository).findById(courseId);
        verify(repository).delete(course);
    }

    @Test
    void delete_DeveLancarExcecaoQuandoCursoNaoEncontrado() {
        // Arrange
        String courseId = "999";
        when(repository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> courseService.delete(courseId));
        verify(repository).findById(courseId);
        verify(repository, never()).delete(any(Course.class));
    }
}
