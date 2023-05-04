package co.edu.umanizales.tadsmain.controller.dto;

import co.edu.umanizales.tadsmain.model.Location;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ListKidsByLocationGenderDTO {
    private List<KidsByLocationGenderQuantityDTO> kidsByLocationGenderQuantityDTOS;

    public ListKidsByLocationGenderDTO(List<Location> cities) {
        kidsByLocationGenderQuantityDTOS = new ArrayList<>();
        for(Location location: cities){
            kidsByLocationGenderQuantityDTOS.add(new
                    KidsByLocationGenderQuantityDTO(location.getName()));
        }
    }

    // método actualizar
    public void updateQuantity(String city, String gender){
        for(KidsByLocationGenderQuantityDTO loc:kidsByLocationGenderQuantityDTOS){
            if(loc.getCity().equals(city)){
                for(KidsByGenderDTO genderDTO: loc.getGenders()){
                    if(genderDTO.getGender()==gender){
                        genderDTO.setQuantity(genderDTO.getQuantity()+1);
                        loc.setTotal(loc.getTotal()+1);
                        return;
                    }
                }
            }
        }
    }
}
