package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.PetDTO;
import co.edu.umanizales.tadsmain.controller.dto.ResponseDTO;
import co.edu.umanizales.tadsmain.model.Kid;
import co.edu.umanizales.tadsmain.model.Pet;
import co.edu.umanizales.tadsmain.service.ListDEService;
import co.edu.umanizales.tadsmain.service.ListSEService;
import co.edu.umanizales.tadsmain.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listDEService.getPets().getHead(),null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody PetDTO petDTO){
        listDEService.getPets().addToStart(
                new Pet(petDTO.getName(),
                        petDTO.getId(), petDTO.getTypeOfAnimal(),
                        petDTO.getAge(),petDTO.getWeight()));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el animal al inicio de la lista",
                null), HttpStatus.OK);
    }

    @PostMapping(path="addtoend")
    public ResponseEntity<ResponseDTO> addToEnd(@RequestBody PetDTO petDTO){
        listDEService.getPets().addToStart(
                new Pet(petDTO.getName(),
                        petDTO.getId(), petDTO.getTypeOfAnimal(),
                        petDTO.getAge(),petDTO.getWeight()));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el animal al final de la lista",
                null), HttpStatus.OK);
    }

}
