package co.edu.umanizales.tadsmain.controller.dto;

import lombok.Data;

@Data
public class PetDTO {
    private String name;
    private String id;
    private String typeOfAnimal;
    private byte age;
    private double weight;
}
