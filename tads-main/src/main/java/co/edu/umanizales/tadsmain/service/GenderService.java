package co.edu.umanizales.tadsmain.service;

import co.edu.umanizales.tadsmain.model.Gender;
import co.edu.umanizales.tadsmain.model.Location;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class GenderService {
    private List<Gender> genders;

    public GenderService(){
        genders = new ArrayList<>();
        genders.add(new Gender('M',true));
        genders.add(new Gender('F',false));
    }

    public Gender getGenderById(boolean id){

        for(Gender gender: genders){
            if(gender.isId() == id) {
                return gender;
            }
        }
        return null;
    }

    public Gender getGenderByGender(char gen){

        for(Gender gender: genders){
            if(gender.getGender() == gen) {
                return gender;
            }
        }
        return null;
    }

}
