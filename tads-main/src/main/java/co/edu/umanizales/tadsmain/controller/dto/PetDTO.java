package co.edu.umanizales.tadsmain.controller.dto;

import lombok.Data;

@Data
public class PetDTO {
    private String name;
    private String id;
    private char gender;
    private String type;
    private byte age;
    private String codeLocation;
}
