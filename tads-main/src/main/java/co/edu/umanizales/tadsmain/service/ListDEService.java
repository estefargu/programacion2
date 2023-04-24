package co.edu.umanizales.tadsmain.service;

import co.edu.umanizales.tadsmain.model.ListDE;
import co.edu.umanizales.tadsmain.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ListDEService {
    private ListDE pets;

    public ListDEService() {
        pets = new ListDE();
    }
}
