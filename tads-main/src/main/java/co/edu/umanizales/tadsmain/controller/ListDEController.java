package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.*;
import co.edu.umanizales.tadsmain.exception.ListSEException;
import co.edu.umanizales.tadsmain.model.Kid;
import co.edu.umanizales.tadsmain.model.Location;
import co.edu.umanizales.tadsmain.model.NodeDE;
import co.edu.umanizales.tadsmain.model.Pet;
import co.edu.umanizales.tadsmain.service.ListDEService;
import co.edu.umanizales.tadsmain.service.ListSEService;
import co.edu.umanizales.tadsmain.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        List<Pet> petList = listDEService.getPetList();
        return new ResponseEntity<>(new ResponseDTO(200, petList, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addToStart(@Valid @RequestBody PetDTO petDTO){
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listDEService.getPets().addToStart(new Pet(petDTO.getName(), petDTO.getId(),
                    petDTO.getGender(),petDTO.getTypeOfAnimal(), petDTO.getAge(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado la mascota al inicio de la lista",
                null), HttpStatus.OK);
    }
    @PostMapping(path ="/addinposition/{position}")
    public ResponseEntity<ResponseDTO> addInPosition(@Valid @RequestBody PetDTO petDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        Pet pet = new Pet(petDTO.getName(), petDTO.getId(),
                petDTO.getGender(),petDTO.getTypeOfAnimal(), petDTO.getAge(), location);
        try {
            listDEService.getPets().addInPosition(pet, position);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado la mascota en la posición " + position,
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
    }
    @PostMapping(path="/addtoend")
    public ResponseEntity<ResponseDTO> addToEnd(@Valid @RequestBody PetDTO petDTO){
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listDEService.getPets().addToEnd(new Pet(petDTO.getName(), petDTO.getId(),
                    petDTO.getGender(), petDTO.getTypeOfAnimal(), petDTO.getAge(),location));
            return new ResponseEntity<>(new ResponseDTO(
                    200,"Se ha adicionado la mascota al final de la lista",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ResponseDTO> deleteByIdentification(@PathVariable String id){
        try {
            listDEService.getPets().deleteByIdentification(id);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha eliminado la mascota con identificación " + id,
                null), HttpStatus.OK);
    }

    @DeleteMapping(path="/deletepet/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age){
        try {
            listDEService.getPets().deleteByAge(age);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha eliminado la mascota con edad " + age,
                null), HttpStatus.OK);
    }


    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        try {
            listDEService.invert();
            return new ResponseEntity<>(new ResponseDTO(
                    200,"Se ha invertido la lista de mascotas",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
    }

    @GetMapping(path= "/ordermaletostart")
    public ResponseEntity<ResponseDTO> getOrderMaleToStart(){
        try {
            listDEService.getPets().orderMaleToStart();
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han colocado los machos al inicio y las hembras al final",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/mixmaleandfemale")
    public ResponseEntity<ResponseDTO> getMixMaleAndFemale(){
        try {
            listDEService.getPets().getMixMaleAndFemale();
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se ha intercalado la lista de mascotas correctamente.",null),HttpStatus.OK);
    }

    @GetMapping(path = "/averageagebypet")
    public ResponseEntity<ResponseDTO> getAverageAgeByPet() {
        float average = listDEService.getPets().getAverageAgeByPet();
        return new ResponseEntity<>(new ResponseDTO(200,
                "El promedio de edad por mascotas es " + average, null), HttpStatus.OK);
    }

    @GetMapping(path = "/petsbylocations")
    public ResponseEntity<ResponseDTO> getPetsByLocation(){
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()) {
            int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
            if (count > 0) {
                petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,petsByLocationDTOList,
                null), HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/petsbydepartments")
    public ResponseEntity<ResponseDTO> getPetsByDepartments(){
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(5)) {
            int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
            if (count > 0) {
                petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
            }
        }
        if(petsByLocationDTOList != null){
            return new ResponseEntity<>(new ResponseDTO(200,petsByLocationDTOList,
                    null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(
                    309, "No se han registrado departamentos",
                    null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/petsbycities")
    public ResponseEntity<ResponseDTO> getPetsByCities(){
        List<PetsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(8)) {
            int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
            if (count > 0) {
                petsByLocationDTOList.add(new PetsByLocationDTO(loc, count));
            }
        }
        if(petsByLocationDTOList != null){
            return new ResponseEntity<>(new ResponseDTO(200,petsByLocationDTOList,
                    null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(
                    309, "No se han registrado ciudades",
                    null), HttpStatus.OK);
        }
    }
    @GetMapping(path = "/loseposition/{id}/{position}")
    public ResponseEntity<ResponseDTO> getLosePosition(@PathVariable String id, @PathVariable int position) {
        try {
            listDEService.getPets().getLosePosition(id, position);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "La mascota con identificación "
                + id + " ha perdido " + position + " posición/es", null), HttpStatus.OK);
    }

    @GetMapping(path = "/gainposition/{id}/{position}")
    public ResponseEntity<ResponseDTO> getGainPosition(@PathVariable String id, @PathVariable int position) {
        try {
            listDEService.getPets().getGainPosition(id, position);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "La mascota con identificación "
                + id + " ha ganado " + position + " posición/es", null), HttpStatus.OK);
    }

    @GetMapping(path = "/ordertoendpetbyletter/{letter}")
    public ResponseEntity<ResponseDTO> getOrderToEndKidByLetter(@PathVariable String letter) {
        try {
            listDEService.getPets().getOrderToEndPetByLetter(letter);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se colocaron las mascotas que empiezan por la letra "
                        + letter + " al final de la lista", null), HttpStatus.OK);
    }

    @GetMapping(path = "/generateagerangereport/{ageMin}/{ageMax}")
    public ResponseEntity<ResponseDTO> getGenerateAgeRangeReport(@PathVariable byte ageMin, @PathVariable byte ageMax) {
        ReportPetsByAgeRangeDTO report = null;
        try {
            report = listDEService.getPets().getGenerateAgeRangeReport(ageMin, ageMax);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteinposition/{id}")
    public ResponseEntity<ResponseDTO> getDeleteInPosition(@PathVariable String id){
        listDEService.getPets().getDeleteInPosition(id);
        return new ResponseEntity<>(new ResponseDTO(200,
                "El nino con identificacion " + id + " ha sido eliminado", null), HttpStatus.OK);
    }
}
