package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ReportPetsByAgeRangeDTO {
    private int totalPetsByRange;
    private byte ageMin;
    private byte ageMax;
    private List<Pet> report;
}
