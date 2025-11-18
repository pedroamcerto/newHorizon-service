package com.newhorizon_service.service;

import com.newhorizon_service.dto.competence.CreateCompetence;
import com.newhorizon_service.dto.competence.GetCompetenceDto;
import com.newhorizon_service.exception.NotFoundException;
import com.newhorizon_service.model.Competence;
import com.newhorizon_service.model.enums.CompetenceType;
import com.newhorizon_service.repository.CompetenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompetenceServiceTest {

    @Mock
    private CompetenceRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CompetenceService competenceService;

    private Competence competence;
    private CreateCompetence createCompetence;
    private GetCompetenceDto getCompetenceDto;

    @BeforeEach
    void setUp() {
        competence = new Competence();
        competence.setId("1");
        competence.setName("Java Programming");
        competence.setType(CompetenceType.HARD);

        createCompetence = new CreateCompetence();
        createCompetence.setName("Java Programming");
        createCompetence.setType(CompetenceType.HARD);

        getCompetenceDto = new GetCompetenceDto();
        getCompetenceDto.setId("1");
        getCompetenceDto.setName("Java Programming");
        getCompetenceDto.setType(CompetenceType.HARD);
    }

    @Test
    void create_DeveCriarCompetenciaComSucesso() {
        // Arrange
        when(modelMapper.map(createCompetence, Competence.class)).thenReturn(competence);
        when(repository.save(any(Competence.class))).thenReturn(competence);

        // Act
        competenceService.create(createCompetence);

        // Assert
        verify(modelMapper).map(createCompetence, Competence.class);
        verify(repository).save(competence);
    }

    @Test
    void update_DeveAtualizarCompetenciaComSucesso() {
        // Arrange
        String competenceId = "1";
        when(repository.findById(competenceId)).thenReturn(Optional.of(competence));
        when(modelMapper.map(createCompetence, Competence.class)).thenReturn(competence);
        when(repository.save(any(Competence.class))).thenReturn(competence);

        // Act
        competenceService.update(competenceId, createCompetence);

        // Assert
        verify(repository).findById(competenceId);
        verify(modelMapper).map(createCompetence, Competence.class);
        verify(repository).save(competence);
    }

    @Test
    void update_DeveLancarExcecaoQuandoCompetenciaNaoEncontrada() {
        // Arrange
        String competenceId = "999";
        when(repository.findById(competenceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> competenceService.update(competenceId, createCompetence));
        verify(repository).findById(competenceId);
        verify(repository, never()).save(any(Competence.class));
    }

    @Test
    void getAll_DeveRetornarListaDeCompetencias() {
        // Arrange
        List<Competence> competences = Arrays.asList(competence);
        when(repository.findAll()).thenReturn(competences);
        when(modelMapper.map(competence, GetCompetenceDto.class)).thenReturn(getCompetenceDto);

        // Act
        List<GetCompetenceDto> result = competenceService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getCompetenceDto.getId(), result.get(0).getId());
        assertEquals(getCompetenceDto.getName(), result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void getById_DeveRetornarCompetenciaPorId() {
        // Arrange
        String competenceId = "1";
        when(repository.findById(competenceId)).thenReturn(Optional.of(competence));
        when(modelMapper.map(competence, GetCompetenceDto.class)).thenReturn(getCompetenceDto);

        // Act
        GetCompetenceDto result = competenceService.getById(competenceId);

        // Assert
        assertNotNull(result);
        assertEquals(getCompetenceDto.getId(), result.getId());
        assertEquals(getCompetenceDto.getName(), result.getName());
        verify(repository).findById(competenceId);
    }

    @Test
    void getById_DeveLancarExcecaoQuandoCompetenciaNaoEncontrada() {
        // Arrange
        String competenceId = "999";
        when(repository.findById(competenceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> competenceService.getById(competenceId));
        verify(repository).findById(competenceId);
    }

    @Test
    void delete_DeveDeletarCompetenciaComSucesso() {
        // Arrange
        String competenceId = "1";
        when(repository.findById(competenceId)).thenReturn(Optional.of(competence));
        doNothing().when(repository).delete(competence);

        // Act
        competenceService.delete(competenceId);

        // Assert
        verify(repository).findById(competenceId);
        verify(repository).delete(competence);
    }

    @Test
    void delete_DeveLancarExcecaoQuandoCompetenciaNaoEncontrada() {
        // Arrange
        String competenceId = "999";
        when(repository.findById(competenceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> competenceService.delete(competenceId));
        verify(repository).findById(competenceId);
        verify(repository, never()).delete(any(Competence.class));
    }
}
