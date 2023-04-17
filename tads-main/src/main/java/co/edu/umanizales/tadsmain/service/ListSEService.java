package co.edu.umanizales.tadsmain.service;

import co.edu.umanizales.tadsmain.model.ListSE;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ListSEService {
    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();

    }

    public void invert(){
        kids.invert();
    }
}
