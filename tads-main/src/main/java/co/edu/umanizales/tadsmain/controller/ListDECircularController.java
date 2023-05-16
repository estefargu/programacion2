package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.PetDTO;
import co.edu.umanizales.tadsmain.controller.dto.ResponseDTO;
import co.edu.umanizales.tadsmain.exception.ListSEException;
import co.edu.umanizales.tadsmain.model.Location;
import co.edu.umanizales.tadsmain.model.Pet;
import co.edu.umanizales.tadsmain.service.ListDECircularService;
import co.edu.umanizales.tadsmain.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/listdecircular")
public class ListDECircularController {
    @Autowired
    private ListDECircularService listDECircularService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        List<Pet> petList = listDECircularService.getPetList();
        return new ResponseEntity<>(new ResponseDTO(200, petList, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addToStart(@Valid @RequestBody PetDTO petDTO){
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listDECircularService.getPets().addToStart(new Pet(petDTO.getName(), petDTO.getId(),
                    petDTO.getGender(),petDTO.getTypeOfAnimal(), petDTO.getAge(), location, petDTO.getBath(), petDTO.getPulgas()));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado la mascota al inicio de la lista",
                null), HttpStatus.OK);
    }
    @PostMapping(path = "/addinposition/{position}")
    public ResponseEntity<ResponseDTO> addInPosition(@Valid @RequestBody PetDTO petDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        Pet pet = new Pet(petDTO.getName(), petDTO.getId(),
                petDTO.getGender(),petDTO.getTypeOfAnimal(), petDTO.getAge(), location, petDTO.getBath(),petDTO.getPulgas());
        try {
            listDECircularService.getPets().addInPosition(pet, position);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    200, e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado la mascota en la posición " + position,
                null), HttpStatus.OK);
    }

    @PostMapping(path = "/addtoend")
    public ResponseEntity<ResponseDTO> addToEnd(@Valid @RequestBody PetDTO petDTO){
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listDECircularService.getPets().addToEnd(new Pet(petDTO.getName(), petDTO.getId(),
                    petDTO.getGender(),petDTO.getTypeOfAnimal(), petDTO.getAge(), location, petDTO.getBath(), petDTO.getPulgas()));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado la mascota al final de la lista",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/bathpet/{direction}")
    public ResponseEntity<ResponseDTO> getBathPet(@PathVariable String direction){
        try {
            listDECircularService.getPets().getBathPet(direction);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha bañado la mascota",
                null), HttpStatus.OK);
    }

        @GetMapping(path = "/petwithmorefleas")
        public ResponseEntity<ResponseDTO> getPerWithMoreFleas(){
            int maxFleas = listDECircularService.getPets().getPetWithMoreFleas();
            return new ResponseEntity<>(new ResponseDTO(200,"La mascota con mas pulgas tiene "
                    + maxFleas + " pulgas", null), HttpStatus.OK);

        }
}
