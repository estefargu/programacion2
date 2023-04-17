package co.edu.umanizales.tadsmain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Kid {
    private String identification;
    private String name;
    private byte age;
    private char gender;
    private Location location;
}
