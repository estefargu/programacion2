package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.*;
import co.edu.umanizales.tadsmain.model.Kid;
import co.edu.umanizales.tadsmain.model.Location;
import co.edu.umanizales.tadsmain.service.ListSEService;
import co.edu.umanizales.tadsmain.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids().getHead(),null), HttpStatus.OK);
    }

    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha invertido la lista de ninos",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han intercambiado los extremos de la lista",
                null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKidToStart(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        boolean identification = listSEService.getKids().getConfirmKidById(kidDTO.getIdentification());
        if(!identification){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        listSEService.getKids().addToStart(
                new Kid(kidDTO.getIdentification(),
                        kidDTO.getName(), kidDTO.getAge(),
                        kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el nino al inicio de la lista",
                null), HttpStatus.OK);
    }

    @PostMapping(path ="/addinposition/{position}")
    public ResponseEntity<ResponseDTO> addInPosition(@RequestBody KidDTO kidDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        boolean identification = listSEService.getKids().getConfirmKidById(kidDTO.getIdentification());
        if(!identification){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        int listSize = listSEService.getKids().getSize();
        if (position < 1 || position > listSize + 1) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "La posición especificada no es válida. La lista tiene "
                    + listSize + " nino/s",
                    null), HttpStatus.BAD_REQUEST);
        }
        Kid kid = new Kid(kidDTO.getIdentification(), kidDTO.getName(),
                kidDTO.getAge(), kidDTO.getGender(), location);
        if(position ==1 ){
            listSEService.getKids().addToStart(kid);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado el niño en la posición " + position,
                    null), HttpStatus.OK);
        }
        listSEService.getKids().addInPosition(kid, position);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el niño en la posición " + position,
                null), HttpStatus.OK);
    }

    @PostMapping(path="/addtoend")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        boolean identification = listSEService.getKids().getConfirmKidById(kidDTO.getIdentification());
        if(!identification){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        listSEService.getKids().add(
                new Kid(kidDTO.getIdentification(),
                        kidDTO.getName(), kidDTO.getAge(),
                        kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el nino al final de la lista",
                null), HttpStatus.OK);
    }

    @DeleteMapping(path="/{identification}")
    public ResponseEntity<ResponseDTO> deleteByIdentification(@PathVariable String identification){
        boolean existIdentification = listSEService.getKids().getConfirmKidById(identification);
        if(existIdentification){
            return new ResponseEntity<>(new ResponseDTO(
                    404, "No existe la identificación ingresada",
                    null), HttpStatus.NOT_FOUND);
        }
        listSEService.getKids().deleteByIdentification(identification);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha eliminado el niño con identificación " + identification,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "/kidsbydepartments")
    public ResponseEntity<ResponseDTO> getKidsByDepartments(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(5)) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        if(kidsByLocationDTOList != null) {
            return new ResponseEntity<>(new ResponseDTO(
                    200, kidsByLocationDTOList,
                    null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(
                    309, "No se han registrado departamentos",
                    null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/kidsbycities")
    public ResponseEntity<ResponseDTO> getKidsByCities(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(8)) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        if(kidsByLocationDTOList != null){
            return new ResponseEntity<>(new ResponseDTO(
                    200,kidsByLocationDTOList,
                    null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(
                    309, "No se han registrado ciudades",
                    null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/kidsbycitiesgenders/{age}")
    public ResponseEntity<ResponseDTO> getListKisLocationGenders(@PathVariable byte age) {
        ListKidsByLocationGenderDTO report = new ListKidsByLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getKids().getListKidsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(
                200,report,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/averageagebykid")
    public ResponseEntity<ResponseDTO> getAverageAgeByKid() {
        float average = listSEService.getKids().getAverageAgeByKid();
        return new ResponseEntity<>(new ResponseDTO(200,
               "El promedio de edad por ninos es " + average, null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations/{code}")
    public ResponseEntity<ResponseDTO> getKidsByLocation(@PathVariable String code){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        Location loc = locationService.getLocationByCode(code); // Obtener la ubicación por el código
        int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode()); // Obtener la cantidad de niños por ubicación

        kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count)); // Agregar la ubicación y la cantidad de niños a la lista
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path= "/orderboystostart")
    public ResponseEntity<ResponseDTO> getOrderBoysToStart(){
        listSEService.getKids().orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se han colocado los ninos al inicio y las ninas al final",
                null), HttpStatus.OK);
    }

    @DeleteMapping(path="/deletekid/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age){
        listSEService.getKids().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha eliminado niño/s con edad " + age,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/mixboyandgirl")
    public ResponseEntity<ResponseDTO> getMixBoyAndGirl(){
        listSEService.getKids().getMixBoyAndGirl();
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se ha intercalado la lista de niños y niñas correctamente.",null),HttpStatus.OK);
    }

    @GetMapping(path = "/loseposition/{identification}/{position}")
    public ResponseEntity<ResponseDTO> getLosePosition(@PathVariable String identification, @PathVariable int position) {
        boolean code = listSEService.getKids().getConfirmKidById(identification);
        if(code){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño no existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        int positionKid = listSEService.getKids().getPositionById(identification);
        int newPosition = positionKid + position;
        if (newPosition > listSEService.getKids().getSize()) {
            return new ResponseEntity<>(new ResponseDTO(400, "El niño con identificación " + identification
                    + " no puede perder " + position + " posiciones", null), HttpStatus.BAD_REQUEST);
        }
        listSEService.getKids().getLosePosition(identification, position);
        return new ResponseEntity<>(new ResponseDTO(200, "El niño con identificación "
                + identification + " ha perdido " + position + " posición/es", null), HttpStatus.OK);
    }

    @GetMapping(path = "/gainposition/{identification}/{position}")
    public ResponseEntity<ResponseDTO> getGainPosition(@PathVariable String identification, @PathVariable int position) {
        boolean code = listSEService.getKids().getConfirmKidById(identification);
        if(code){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño no existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        int positionKid = listSEService.getKids().getPositionById(identification);
        int newPosition = positionKid - position;
        if (newPosition < 1) {
            return new ResponseEntity<>(new ResponseDTO(400, "El niño con identificación " + identification
                    + " no puede ganar " + position + " posiciones", null), HttpStatus.BAD_REQUEST);
        }
        listSEService.getKids().getGainPosition(identification, position);
        return new ResponseEntity<>(new ResponseDTO(200, "El niño con identificación "
                + identification + " ha ganado " + position + " posición/es", null), HttpStatus.OK);
    }

    @GetMapping(path = "/ordertoendkidbyletter/{letter}")
    public ResponseEntity<ResponseDTO> getOrderToEndKidByLetter(@PathVariable String letter) {
        listSEService.getKids().getOrderToEndKidByLetter(letter);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Se colocaron los ninos que empiezan por la letra "
                        + letter + " al final de la lista", null), HttpStatus.OK);
    }

    @GetMapping(path = "/generateagerangereport/{ageMin}/{ageMax}")
    public ResponseEntity<ResponseDTO> getGenerateAgeRangeReport(@PathVariable byte ageMin, @PathVariable byte ageMax) {
        ReportKidsByAgeRangeDTO report = listSEService.getKids().getGenerateAgeRangeReport(ageMin, ageMax);
        return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);
    }




}
