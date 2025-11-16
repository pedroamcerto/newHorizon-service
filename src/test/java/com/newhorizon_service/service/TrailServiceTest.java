package com.newhorizon_service.service;

import com.newhorizon_service.dto.trail.CreateTrailDto;
import com.newhorizon_service.dto.trail.GetTrailDto;
import com.newhorizon_service.model.Trail;
import com.newhorizon_service.repository.TrailRepository;
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
class TrailServiceTest {

    @Mock
    private TrailRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TrailService trailService;

    private Trail trail;
    private CreateTrailDto createTrailDto;
    private GetTrailDto getTrailDto;

    @BeforeEach
    void setUp() {
        trail = new Trail();
        trail.setId("1");
        trail.setName("Trilha Iniciante Java");

        createTrailDto = new CreateTrailDto();
        createTrailDto.setName("Trilha Iniciante Java");

        getTrailDto = new GetTrailDto();
        getTrailDto.setId("1");
        getTrailDto.setName("Trilha Iniciante Java");
    }

    @Test
    void create_DeveCriarTrilhaComSucesso() {
        // Arrange
        when(modelMapper.map(createTrailDto, Trail.class)).thenReturn(trail);
        when(repository.save(any(Trail.class))).thenReturn(trail);

        // Act
        trailService.create(createTrailDto);

        // Assert
        verify(modelMapper).map(createTrailDto, Trail.class);
        verify(repository).save(trail);
    }

    @Test
    void update_DeveAtualizarTrilhaComSucesso() {
        // Arrange
        String trailId = "1";
        when(repository.findById(trailId)).thenReturn(Optional.of(trail));
        when(modelMapper.map(createTrailDto, Trail.class)).thenReturn(trail);
        when(repository.save(any(Trail.class))).thenReturn(trail);

        // Act
        trailService.update(trailId, createTrailDto);

        // Assert
        verify(repository).findById(trailId);
        verify(modelMapper).map(createTrailDto, Trail.class);
        verify(repository).save(trail);
    }

    @Test
    void update_DeveLancarExcecaoQuandoTrilhaNaoEncontrada() {
        // Arrange
        String trailId = "999";
        when(repository.findById(trailId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> trailService.update(trailId, createTrailDto));
        verify(repository).findById(trailId);
        verify(repository, never()).save(any(Trail.class));
    }

    @Test
    void getAll_DeveRetornarListaDeTrilhas() {
        // Arrange
        List<Trail> trails = Arrays.asList(trail);
        when(repository.findAll()).thenReturn(trails);
        when(modelMapper.map(trail, GetTrailDto.class)).thenReturn(getTrailDto);

        // Act
        List<GetTrailDto> result = trailService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getTrailDto.getId(), result.get(0).getId());
        assertEquals(getTrailDto.getName(), result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void getById_DeveRetornarTrilhaPorId() {
        // Arrange
        String trailId = "1";
        when(repository.findById(trailId)).thenReturn(Optional.of(trail));
        when(modelMapper.map(trail, GetTrailDto.class)).thenReturn(getTrailDto);

        // Act
        GetTrailDto result = trailService.getById(trailId);

        // Assert
        assertNotNull(result);
        assertEquals(getTrailDto.getId(), result.getId());
        assertEquals(getTrailDto.getName(), result.getName());
        verify(repository).findById(trailId);
    }

    @Test
    void getById_DeveLancarExcecaoQuandoTrilhaNaoEncontrada() {
        // Arrange
        String trailId = "999";
        when(repository.findById(trailId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> trailService.getById(trailId));
        verify(repository).findById(trailId);
    }

    @Test
    void delete_DeveDeletarTrilhaComSucesso() {
        // Arrange
        String trailId = "1";
        when(repository.findById(trailId)).thenReturn(Optional.of(trail));
        doNothing().when(repository).delete(trail);

        // Act
        trailService.delete(trailId);

        // Assert
        verify(repository).findById(trailId);
        verify(repository).delete(trail);
    }

    @Test
    void delete_DeveLancarExcecaoQuandoTrilhaNaoEncontrada() {
        // Arrange
        String trailId = "999";
        when(repository.findById(trailId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> trailService.delete(trailId));
        verify(repository).findById(trailId);
        verify(repository, never()).delete(any(Trail.class));
    }
}
