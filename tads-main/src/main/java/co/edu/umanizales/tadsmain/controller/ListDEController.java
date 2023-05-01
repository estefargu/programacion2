package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.*;
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
import org.springframework.web.bind.annotation.*;

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
        List<PetDTO> petDTOList = listDEService.getPetList();
        return new ResponseEntity<>(new ResponseDTO(200, petDTOList, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody PetDTO petDTO){
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        boolean id = listDEService.getPets().getConfirmKidById(petDTO.getId());
        if(!id){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        listDEService.getPets().addToStart(new Pet(petDTO.getName(), petDTO.getId(),
                petDTO.getGender(),petDTO.getType(), petDTO.getAge(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el animal al inicio de la lista",
                null), HttpStatus.OK);
    }
    @PostMapping(path ="/addinposition/{position}")
    public ResponseEntity<ResponseDTO> addInPosition(@RequestBody PetDTO petDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        boolean id = listDEService.getPets().getConfirmKidById(petDTO.getId());
        if(!id){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        int listSize = listDEService.getPets().getSize();
        if (position < 1 || position > listSize + 1) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "La posición especificada no es válida. La lista tiene "
                    + listSize + " animales",
                    null), HttpStatus.BAD_REQUEST);
        }
        Pet pet = new Pet(petDTO.getName(), petDTO.getId(),
                petDTO.getGender(),petDTO.getType(), petDTO.getAge(), location);
        if(position ==1 ){
            listDEService.getPets().addToStart(pet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado el animal en la posición " + position,
                    null), HttpStatus.OK);
        }
        listDEService.getPets().addInPosition(pet, position);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el animal en la posición " + position,
                null), HttpStatus.OK);
    }
    @PostMapping(path="/addtoend")
    public ResponseEntity<ResponseDTO> addToEnd(@RequestBody PetDTO petDTO){
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        boolean id = listDEService.getPets().getConfirmKidById(petDTO.getId());
        if(!id){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        listDEService.getPets().addToEnd(new Pet(petDTO.getName(), petDTO.getId(),
                petDTO.getGender(), petDTO.getType(), petDTO.getAge(),location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el animal al final de la lista",
                null), HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<ResponseDTO> deleteByIdentification(@PathVariable String id){
        boolean existId = listDEService.getPets().getConfirmKidById(id);
        if(existId){
            return new ResponseEntity<>(new ResponseDTO(
                    404, "No existe la identificación ingresada",
                    null), HttpStatus.NOT_FOUND);
        }
        listDEService.getPets().deleteByIdentification(id);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha eliminado el animal con identificación " + id,
                null), HttpStatus.OK);
    }

    @DeleteMapping(path="/deletepet/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age){
        listDEService.getPets().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha eliminado animal/es con edad " + age,
                null), HttpStatus.OK);
    }


    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        listDEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha invertido la lista de animales",
                null), HttpStatus.OK);

    }

    @GetMapping(path= "/ordermaletostart")
    public ResponseEntity<ResponseDTO> getOrderMaleToStart(){
        listDEService.getPets().orderMaleToStart();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han colocado los machos al inicio y las hembras al final",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/mixmaleandfemale")
    public ResponseEntity<ResponseDTO> getMixMaleAndFemale(){
        listDEService.getPets().getMixMaleAndFemale();
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se ha intercalado la lista de animales correctamente.",null),HttpStatus.OK);
    }

    @GetMapping(path = "/averageagebypet")
    public ResponseEntity<ResponseDTO> getAverageAgeByPet() {
        float average = listDEService.getPets().getAverageAgeByPet();
        return new ResponseEntity<>(new ResponseDTO(200,
                "El promedio de edad por animales es " + average, null), HttpStatus.OK);
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
        boolean code = listDEService.getPets().getConfirmKidById(id);
        if(code){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño no existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        int positionPet = listDEService.getPets().getPositionById(id);
        int newPosition = positionPet + position;
        if (newPosition > listDEService.getPets().getSize()) {
            return new ResponseEntity<>(new ResponseDTO(400, "El animal con identificación " + id
                    + " no puede perder " + position + " posiciones", null), HttpStatus.BAD_REQUEST);
        }
        listDEService.getPets().getLosePosition(id, position);
        return new ResponseEntity<>(new ResponseDTO(200, "El animal con identificación "
                + id + " ha perdido " + position + " posición/es", null), HttpStatus.OK);
    }

    @GetMapping(path = "/gainposition/{id}/{position}")
    public ResponseEntity<ResponseDTO> getGainPosition(@PathVariable String id, @PathVariable int position) {
        boolean code = listDEService.getPets().getConfirmKidById(id);
        if(code){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño no existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        int positionPet = listDEService.getPets().getPositionById(id);
        int newPosition = positionPet - position;
        if (newPosition < 1) {
            return new ResponseEntity<>(new ResponseDTO(400, "El animal con identificación " + id
                    + " no puede ganar " + position + " posiciones", null), HttpStatus.BAD_REQUEST);
        }
        listDEService.getPets().getGainPosition(id, position);
        return new ResponseEntity<>(new ResponseDTO(200, "El animal con identificación "
                + id + " ha ganado " + position + " posición/es", null), HttpStatus.OK);
    }

    @GetMapping(path = "/ordertoendpetbyletter/{letter}")
    public ResponseEntity<ResponseDTO> getOrderToEndKidByLetter(@PathVariable String letter) {
        listDEService.getPets().getOrderToEndPetByLetter(letter);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se colocaron los animales que empiezan por la letra "
                        + letter + " al final de la lista", null), HttpStatus.OK);
    }

    @GetMapping(path = "/generateagerangereport/{ageMin}/{ageMax}")
    public ResponseEntity<ResponseDTO> getGenerateAgeRangeReport(@PathVariable byte ageMin, @PathVariable byte ageMax) {
        ReportPetsByAgeRangeDTO report = listDEService.getPets().getGenerateAgeRangeReport(ageMin, ageMax);
        return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);
    }

}
