package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Gender;
import lombok.Data;

@Data
public class KidDTO {
    private String identification;
    private String name;
    private byte age;
    private char gender;
    private String codeLocation;
}
