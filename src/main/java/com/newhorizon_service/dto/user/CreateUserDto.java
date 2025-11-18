package com.newhorizon_service.dto.user;

import com.newhorizon_service.dto.RelationshipDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserDto {
    @NotBlank(message = "O campo 'name' não pode estar vazio.")
    @Size(max = 255, message = "Tamanho máximo do campo 'name' é de 255 caracteres.")
    private String name;

    @NotBlank(message = "O campo 'email' não pode estar vazio.")
    @Email(message = "Email inválido.")
    @Size(max = 320, message = "Tamanho máximo do campo 'email' é de 320 caracteres.")
    private String email;

    @NotEmpty(message = "O campo 'competences' não pode estar vazio.")
    @Valid
    private List<RelationshipDto> competences;

    @Valid
    private List<RelationshipDto> trails;
}
