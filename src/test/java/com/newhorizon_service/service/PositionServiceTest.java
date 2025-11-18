package com.newhorizon_service.service;

import com.newhorizon_service.dto.position.CreatePositionDto;
import com.newhorizon_service.dto.position.GetPositionDto;
import com.newhorizon_service.model.Position;
import com.newhorizon_service.repository.PositionRepository;
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
class PositionServiceTest {

    @Mock
    private PositionRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PositionService positionService;

    private Position position;
    private CreatePositionDto createPositionDto;
    private GetPositionDto getPositionDto;

    @BeforeEach
    void setUp() {
        position = new Position();
        position.setId("1");
        position.setName("Desenvolvedor Java Júnior");

        createPositionDto = new CreatePositionDto();
        createPositionDto.setName("Desenvolvedor Java Júnior");

        getPositionDto = new GetPositionDto();
        getPositionDto.setId("1");
        getPositionDto.setName("Desenvolvedor Java Júnior");
    }

    @Test
    void create_DeveCriarPosicaoComSucesso() {
        // Arrange
        when(modelMapper.map(createPositionDto, Position.class)).thenReturn(position);
        when(repository.save(any(Position.class))).thenReturn(position);

        // Act
        positionService.create(createPositionDto);

        // Assert
        verify(modelMapper).map(createPositionDto, Position.class);
        verify(repository).save(position);
    }

    @Test
    void update_DeveAtualizarPosicaoComSucesso() {
        // Arrange
        String positionId = "1";
        when(repository.findById(positionId)).thenReturn(Optional.of(position));
        when(modelMapper.map(createPositionDto, Position.class)).thenReturn(position);
        when(repository.save(any(Position.class))).thenReturn(position);

        // Act
        positionService.update(positionId, createPositionDto);

        // Assert
        verify(repository).findById(positionId);
        verify(modelMapper).map(createPositionDto, Position.class);
        verify(repository).save(position);
    }

    @Test
    void update_DeveLancarExcecaoQuandoPosicaoNaoEncontrada() {
        // Arrange
        String positionId = "999";
        when(repository.findById(positionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> positionService.update(positionId, createPositionDto));
        verify(repository).findById(positionId);
        verify(repository, never()).save(any(Position.class));
    }

    @Test
    void getAll_DeveRetornarListaDePosicoes() {
        // Arrange
        List<Position> positions = Arrays.asList(position);
        when(repository.findAll()).thenReturn(positions);
        when(modelMapper.map(position, GetPositionDto.class)).thenReturn(getPositionDto);

        // Act
        List<GetPositionDto> result = positionService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getPositionDto.getId(), result.get(0).getId());
        assertEquals(getPositionDto.getName(), result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void getById_DeveRetornarPosicaoPorId() {
        // Arrange
        String positionId = "1";
        when(repository.findById(positionId)).thenReturn(Optional.of(position));
        when(modelMapper.map(position, GetPositionDto.class)).thenReturn(getPositionDto);

        // Act
        GetPositionDto result = positionService.getById(positionId);

        // Assert
        assertNotNull(result);
        assertEquals(getPositionDto.getId(), result.getId());
        assertEquals(getPositionDto.getName(), result.getName());
        verify(repository).findById(positionId);
    }

    @Test
    void getById_DeveLancarExcecaoQuandoPosicaoNaoEncontrada() {
        // Arrange
        String positionId = "999";
        when(repository.findById(positionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> positionService.getById(positionId));
        verify(repository).findById(positionId);
    }

    @Test
    void delete_DeveDeletarPosicaoComSucesso() {
        // Arrange
        String positionId = "1";
        when(repository.findById(positionId)).thenReturn(Optional.of(position));
        doNothing().when(repository).delete(position);

        // Act
        positionService.delete(positionId);

        // Assert
        verify(repository).findById(positionId);
        verify(repository).delete(position);
    }

    @Test
    void delete_DeveLancarExcecaoQuandoPosicaoNaoEncontrada() {
        // Arrange
        String positionId = "999";
        when(repository.findById(positionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> positionService.delete(positionId));
        verify(repository).findById(positionId);
        verify(repository, never()).delete(any(Position.class));
    }
}
