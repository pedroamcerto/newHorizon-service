package com.newhorizon_service.service;

import com.newhorizon_service.dto.analysis.*;
import com.newhorizon_service.model.*;
import com.newhorizon_service.model.enums.CompetenceType;
import com.newhorizon_service.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalysisServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private TrailRepository trailRepository;

    @Mock
    private CompetenceRepository competenceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AnalysisService analysisService;

    private User user;
    private Position position;
    private Trail trail;
    private Competence competence1;
    private Competence competence2;
    private Course course;

    @BeforeEach
    void setUp() {
        // Setup Competences
        competence1 = new Competence();
        competence1.setId("1");
        competence1.setName("Java");
        competence1.setType(CompetenceType.HARD);

        competence2 = new Competence();
        competence2.setId("2");
        competence2.setName("Spring Boot");
        competence2.setType(CompetenceType.HARD);

        // Setup Position
        position = new Position();
        position.setId("1");
        position.setName("Desenvolvedor Java Júnior");
        position.setCompetences(Set.of(competence1, competence2));

        // Setup Course
        course = new Course();
        course.setId("1");
        course.setName("Java Básico");
        course.setCompetences(Set.of(competence1, competence2));

        // Setup Trail
        trail = new Trail();
        trail.setId("1");
        trail.setName("Trilha Iniciante Java");
        trail.setCourses(List.of(course));

        // Setup User
        user = new User();
        user.setId("1");
        user.setName("João Silva");
        user.setEmail("joao.silva@example.com");
        user.setPositions(List.of(position));
        user.setTrails(List.of(trail));
    }

    @Test
    void analyzeUserPositionFit_DeveRetornarListaDeFit() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(positionRepository.findAll()).thenReturn(List.of(position));

        // Act
        List<PositionFitDto> result = analysisService.analyzeUserPositionFit("1");

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository).findById("1");
        verify(positionRepository).findAll();
    }

    @Test
    void analyzeUserPositionFit_DeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, 
            () -> analysisService.analyzeUserPositionFit("999"));
        verify(userRepository).findById("999");
    }

    @Test
    void getUserAnalysis_DeveRetornarAnaliseDoUsuario() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        UserAnalysisDto result = analysisService.getUserAnalysis("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getUserId());
        assertEquals("João Silva", result.getUserName());
        assertEquals("joao.silva@example.com", result.getEmail());
        verify(userRepository).findById("1");
    }

    @Test
    void getUserAnalysis_DeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, 
            () -> analysisService.getUserAnalysis("999"));
        verify(userRepository).findById("999");
    }

    @Test
    void recommendTrails_DeveRetornarRecomendacoes() {
        // Arrange
        Trail newTrail = new Trail();
        newTrail.setId("2");
        newTrail.setName("Trilha Avançada");
        newTrail.setCourses(List.of(course));

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(trailRepository.findAll()).thenReturn(List.of(trail, newTrail));
        when(positionRepository.findAll()).thenReturn(List.of(position));

        // Act
        List<TrailRecommendationDto> result = analysisService.recommendTrails("1");

        // Assert
        assertNotNull(result);
        verify(userRepository).findById("1");
        verify(trailRepository).findAll();
    }

    @Test
    void recommendTrails_DeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, 
            () -> analysisService.recommendTrails("999"));
        verify(userRepository).findById("999");
    }

    @Test
    void getPositionsByTrail_DeveRetornarPosicoesCompativeis() {
        // Arrange
        when(trailRepository.findById("1")).thenReturn(Optional.of(trail));
        when(positionRepository.findAll()).thenReturn(List.of(position));

        // Act
        PositionsByTrailDto result = analysisService.getPositionsByTrail("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getTrailId());
        assertEquals("Trilha Iniciante Java", result.getTrailName());
        assertNotNull(result.getMatchingPositions());
        verify(trailRepository).findById("1");
        verify(positionRepository).findAll();
    }

    @Test
    void getPositionsByTrail_DeveLancarExcecaoQuandoTrilhaNaoEncontrada() {
        // Arrange
        when(trailRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, 
            () -> analysisService.getPositionsByTrail("999"));
        verify(trailRepository).findById("999");
    }

    @Test
    void getUsersByPositionFit_DeveRetornarCandidatosQualificados() {
        // Arrange
        when(positionRepository.findById("1")).thenReturn(Optional.of(position));
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Act
        List<UserAnalysisDto> result = analysisService.getUsersByPositionFit("1", 50.0);

        // Assert
        assertNotNull(result);
        verify(positionRepository).findById("1");
        verify(userRepository).findAll();
    }

    @Test
    void getUsersByPositionFit_DeveLancarExcecaoQuandoPosicaoNaoEncontrada() {
        // Arrange
        when(positionRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, 
            () -> analysisService.getUsersByPositionFit("999", 50.0));
        verify(positionRepository).findById("999");
    }
}
