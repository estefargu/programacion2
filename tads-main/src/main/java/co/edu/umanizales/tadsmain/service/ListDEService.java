package co.edu.umanizales.tadsmain.service;

import co.edu.umanizales.tadsmain.model.ListDE;
import co.edu.umanizales.tadsmain.model.NodeDE;
import co.edu.umanizales.tadsmain.model.Pet;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ListDEService {
    private ListDE pets;

    public ListDEService() {
        pets = new ListDE();
    }

    public List<Pet> getPetList() {
        List<Pet> petList = new ArrayList<>();
        NodeDE temp = pets.getHead();
        while (temp != null) {
            petList.add(temp.getData());
            temp = temp.getNext();
        }
        return petList;
    }

    public void invert(){pets.invert();
    }

}
