package com.newhorizon_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RelationshipDto {
    @NotEmpty(message = "O campo 'id' não pode estar vazio.")
    private String id;
}
