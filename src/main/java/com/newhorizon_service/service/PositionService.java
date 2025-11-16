package com.newhorizon_service.service;

import com.newhorizon_service.dto.position.CreatePositionDto;
import com.newhorizon_service.dto.position.GetPositionDto;
import com.newhorizon_service.model.Position;
import com.newhorizon_service.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository repository;
    private final ModelMapper modelMapper;

    public void create(CreatePositionDto dto) {
        Position position = modelMapper.map(dto, Position.class);
        repository.save(position);
    }

    public void update(String id, CreatePositionDto dto) {
        Position existingPosition = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado.")
        );

        Position updatedPosition = modelMapper.map(dto, Position.class);

        // Atualiza os campos do cargo existente com os valores do DTO
        existingPosition.setName(updatedPosition.getName());
        existingPosition.setCompetences(updatedPosition.getCompetences());

        repository.save(existingPosition);
    }

    public List<GetPositionDto> getAll() {
        // Busca todos cargos no banco de dados
        List<Position> positions = repository.findAll();

        // Converte o objeto position em GetPositionDto e retorna;
        return positions.stream().map(
                position -> modelMapper.map(position, GetPositionDto.class)
        ).toList();
    }

    public GetPositionDto getById(String id) {
        Position position = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado.")
        );

        return modelMapper.map(position, GetPositionDto.class);
    }

    public void delete(String id) {
        Position position = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado.")
        );

        this.repository.delete(position);
    }
}
