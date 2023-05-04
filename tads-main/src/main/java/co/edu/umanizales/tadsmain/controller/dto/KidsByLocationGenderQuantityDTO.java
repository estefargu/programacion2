package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class KidsByLocationGenderQuantityDTO {
    private String city;
    private List<KidsByGenderDTO> genders;
    private int total;

    public KidsByLocationGenderQuantityDTO(String city) {
        this.city = city;
        this.total=0;
        this.genders = new ArrayList<>();
        this.genders.add(new KidsByGenderDTO("M",0));
        this.genders.add(new KidsByGenderDTO("F",0));
    }
}
