package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Gender;
import co.edu.umanizales.tadsmain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class KidsByGenderDTO {
    private Gender gender;
    private int total;
}
