package com.newhorizon_service.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCourseDto {
    @NotBlank(message = "O campo 'name' não pode estar vazio.")
    @Size(max = 255, message = "Tamanho máximo do campo 'name' é de 255 caracteres.")
    private String name;
}
