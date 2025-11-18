package com.newhorizon_service.dto.competence;

import com.newhorizon_service.model.enums.CompetenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCompetence {
    @NotBlank(message = "O campo 'name' não pode estar vazio.")
    @Size(max = 255, message = "Tamanho máximo do campo 'name' é de 255 caracteres.")
    private String name;

    @NotNull(message = "O campo 'type' não pode estar vazio.")
    private CompetenceType type;
}
