package com.newhorizon_service.service;

import com.newhorizon_service.dto.competence.CreateCompetence;
import com.newhorizon_service.dto.competence.GetCompetenceDto;
import com.newhorizon_service.model.Competence;
import com.newhorizon_service.repository.CompetenceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetenceService {
    private final CompetenceRepository repository;
    private final ModelMapper modelMapper;

    public void create(CreateCompetence dto) {
        Competence competence = modelMapper.map(dto, Competence.class);
        repository.save(competence);
    }

    public void update(String id, CreateCompetence dto) {
        Competence existingCompetence = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competência não encontrada.")
        );

        Competence updatedCompetence = modelMapper.map(dto, Competence.class);

        // Atualiza os campos da competência existente com os valores do DTO
        existingCompetence.setName(updatedCompetence.getName());
        existingCompetence.setType(updatedCompetence.getType());

        repository.save(existingCompetence);
    }

    public List<GetCompetenceDto> getAll() {
        // Busca todas competências no banco de dados
        List<Competence> competences = repository.findAll();

        // Converte o objeto competence em GetCompetenceDto e retorna;
        return competences.stream().map(
                competence -> modelMapper.map(competence, GetCompetenceDto.class)
        ).toList();
    }

    public GetCompetenceDto getById(String id) {
        Competence competence = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competência não encontrada.")
        );

        return modelMapper.map(competence, GetCompetenceDto.class);
    }

    public void delete(String id) {
        Competence competence = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competência não encontrada.")
        );

        this.repository.delete(competence);
    }
}
