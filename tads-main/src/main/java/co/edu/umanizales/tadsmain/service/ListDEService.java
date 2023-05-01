package co.edu.umanizales.tadsmain.service;

import co.edu.umanizales.tadsmain.controller.dto.PetDTO;
import co.edu.umanizales.tadsmain.model.ListDE;
import co.edu.umanizales.tadsmain.model.ListSE;
import co.edu.umanizales.tadsmain.model.NodeDE;
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

    public List<PetDTO> getPetList() {
        List<PetDTO> petDTOList = new ArrayList<>();
        NodeDE temp = pets.getHead();
        while (temp != null) {
            petDTOList.add(temp.getData().petDTO());
            temp = temp.getNext();
        }
        return petDTOList;
    }

    public void invert(){pets.invert();
    }

}
