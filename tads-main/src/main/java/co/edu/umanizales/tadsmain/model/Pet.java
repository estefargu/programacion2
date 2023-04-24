package co.edu.umanizales.tadsmain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String name;
    private String id;
    private String typeOfAnimal;
    private byte age;
    private double weight;
}
