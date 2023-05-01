package co.edu.umanizales.tadsmain.model;

import co.edu.umanizales.tadsmain.controller.dto.PetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String name;
    private String id;
    private char gender;
    private String type;
    private byte age;
    private Location location;
    public PetDTO petDTO() {
        PetDTO dto = new PetDTO();
        dto.setName(name);
        dto.setId(id);
        dto.setGender(gender);
        dto.setType(type);
        dto.setAge(age);
        dto.setCodeLocation(location.getCode());
        return dto;
    }

}
