package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KidsByLocationDTO {
    private Location location;
    private int quantity;
}
