package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Kid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReportKidsByAgeRangeDTO {
    private int totalKidsByRange;
    private byte ageMin;
    private byte ageMax;
    private List<Kid> report;

}
