package co.edu.umanizales.tadsmain.controller.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class KidDTO {
    @NotBlank(message = "La identificacion es un campo obligatorio")
    @Positive(message= "No se aceptan numeros negativos")
    private String identification;
    @Size(min = 1, max = 30, message = "El nombre debe tener maximo 30 caracteres")
    private String name;
    @Min(value = 1, message = "La edad ingresada debe ser mayor o igual a 1")
    @Max(value = 14, message = "La edad ingresada debe ser menor o igual a 14")
    private byte age;
    @Pattern(regexp = "[FM]", message = "El género debe ser 'F' o 'M'")
    private String gender;
    @NotEmpty(message = "La locacion es un campo obligatorio")
    private String codeLocation;
}
