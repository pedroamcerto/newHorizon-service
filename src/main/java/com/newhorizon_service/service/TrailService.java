package com.newhorizon_service.service;

import com.newhorizon_service.dto.trail.CreateTrailDto;
import com.newhorizon_service.dto.trail.GetTrailDto;
import com.newhorizon_service.model.Trail;
import com.newhorizon_service.repository.TrailRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrailService {
    private final TrailRepository repository;
    private final ModelMapper modelMapper;

    public void create(CreateTrailDto dto) {
        Trail trail = modelMapper.map(dto, Trail.class);
        repository.save(trail);
    }

    public void update(String id, CreateTrailDto dto) {
        Trail existingTrail = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trilha não encontrada.")
        );

        Trail updatedTrail = modelMapper.map(dto, Trail.class);

        // Atualiza os campos da trilha existente com os valores do DTO
        existingTrail.setName(updatedTrail.getName());
        existingTrail.setCourses(updatedTrail.getCourses());
        existingTrail.setCompetences(updatedTrail.getCompetences());
        existingTrail.setPositions(updatedTrail.getPositions());

        repository.save(existingTrail);
    }

    public List<GetTrailDto> getAll() {
        // Busca todas trilhas no banco de dados
        List<Trail> trails = repository.findAll();

        // Converte o objeto trail em GetTrailDto e retorna;
        return trails.stream().map(
                trail -> modelMapper.map(trail, GetTrailDto.class)
        ).toList();
    }

    public GetTrailDto getById(String id) {
        Trail trail = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trilha não encontrada.")
        );

        return modelMapper.map(trail, GetTrailDto.class);
    }

    public void delete(String id) {
        Trail trail = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trilha não encontrada.")
        );

        this.repository.delete(trail);
    }
}
