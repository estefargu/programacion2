package co.edu.umanizales.tadsmain.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PetDTO {
    @Size(min = 1, max = 30, message = "El nombre debe tener maximo 30 caracteres")
    private String name;
    @NotBlank(message = "La identificacion es un campo obligatorio")
    private String id;
    @Pattern(regexp = "[FM]", message = "El género de la mascota debe ser 'F' o 'M'")
    private String gender;
    @NotBlank(message = "La especie es un campo obligatorio")
    private String typeOfAnimal;
    @Min(value = 1, message = "La edad ingresada debe ser mayor a 1")
    @Max(value = 14, message = "La edad ingresada debe ser menor que 14")
    private byte age;
    @NotBlank(message = "La locacion es un campo obligatorio")
    private String codeLocation;
    @Pattern(regexp = "(dirty|clean)", message = "El valor del campo 'bath' debe ser 'dirty' o 'clean'")
    private String bath;

    private int pulgas;

}
