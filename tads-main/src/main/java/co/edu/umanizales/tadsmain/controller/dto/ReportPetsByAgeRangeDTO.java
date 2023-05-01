package co.edu.umanizales.tadsmain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ReportPetsByAgeRangeDTO {
    private int numPetsByRange;
    private byte ageMin;
    private byte ageMax;
    private List<PetDTO> report;
}
