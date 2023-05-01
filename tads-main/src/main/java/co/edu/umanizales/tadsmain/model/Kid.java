package co.edu.umanizales.tadsmain.model;

import co.edu.umanizales.tadsmain.controller.dto.KidDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Kid {
    private String identification;
    private String name;
    private byte age;
    private char gender;
    private Location location;

    public KidDTO kidDTO() {
        KidDTO dto = new KidDTO();
        dto.setName(this.name);
        dto.setIdentification(this.identification);
        dto.setAge(this.age);
        dto.setGender(this.gender);
        dto.setCodeLocation(location.getCode());

        return dto;
    }
}
