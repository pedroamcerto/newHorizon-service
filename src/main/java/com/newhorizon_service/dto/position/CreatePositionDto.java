package com.newhorizon_service.dto.position;

import com.newhorizon_service.dto.RelationshipDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreatePositionDto {
    @NotBlank(message = "O campo 'name' não pode estar vazio.")
    @Size(max = 255, message = "Tamanho máximo do campo 'name' é de 255 caracteres.")
    private String name;

    @NotEmpty(message = "O campo 'competences' não pode estar vazio.")
    @Valid
    private List<RelationshipDto> competences;
}
