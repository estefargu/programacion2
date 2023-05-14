package co.edu.umanizales.tadsmain.service;

import co.edu.umanizales.tadsmain.model.ListDE;
import co.edu.umanizales.tadsmain.model.ListDECircular;
import co.edu.umanizales.tadsmain.model.NodeDE;
import co.edu.umanizales.tadsmain.model.Pet;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ListDECircularService {
    private ListDECircular pets;
    public ListDECircularService() {
        pets = new ListDECircular();
    }

    public List<Pet> getPetList() {
        List<Pet> petList = new ArrayList<>();
        if(pets.getHead()!=null) {
            NodeDE temp = pets.getHead();
            petList.add(temp.getData());
            temp = temp.getNext();
            while (temp != pets.getHead()) {
                petList.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return petList;
    }



}
