package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KidsByLocationAndGenderDTO {
    private List<KidsByGenderDTO> kidsByGenderDTO;
    private Location location;
    private int totalKids;
}
