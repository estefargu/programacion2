package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.*;
import co.edu.umanizales.tadsmain.exception.ListSEException;
import co.edu.umanizales.tadsmain.model.Kid;
import co.edu.umanizales.tadsmain.model.Location;
import co.edu.umanizales.tadsmain.service.ListSEService;
import co.edu.umanizales.tadsmain.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseDTO> invert() {
        try {
            listSEService.invert();
            return new ResponseEntity<>(new ResponseDTO(
                    200,"Se ha invertido la lista de niños",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        try {
            listSEService.getKids().changeExtremes();
            return new ResponseEntity<>(new ResponseDTO(
                    200,"Se han intercambiado los extremos de la lista",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400,e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> addKidToStart(@RequestBody @Valid KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().addToStart(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getName(), kidDTO.getAge(),
                            kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el nino al inicio de la lista",
                null), HttpStatus.OK);
    }

    @PostMapping(path ="/addinposition/{position}")
    public ResponseEntity<ResponseDTO> addInPosition(@Valid @RequestBody KidDTO kidDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        Kid kid = new Kid(kidDTO.getIdentification(), kidDTO.getName(),
                kidDTO.getAge(), kidDTO.getGender(), location);
        try {
            listSEService.getKids().addInPosition(kid, position);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado el niño en la posición " + position,
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path="/addtoend")
    public ResponseEntity<ResponseDTO> addKid(@Valid @RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getName(), kidDTO.getAge(),
                            kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el nino al final de la lista",
                null), HttpStatus.OK);
    }

    @DeleteMapping(path="/{identification}")
    public ResponseEntity<ResponseDTO> deleteByIdentification(@PathVariable String identification) {
        try {
            listSEService.getKids().deleteByIdentification(identification);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha eliminado el niño con identificación " + identification,
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
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

    @GetMapping(path= "/orderboystostart")
    public ResponseEntity<ResponseDTO> getOrderBoysToStart(){
        try {
            listSEService.getKids().orderBoysToStart();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se han colocado los ninos al inicio y las ninas al final",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path="/deletekid/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age){
        try {
            listSEService.getKids().deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha eliminado niño/s con edad " + age,
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/mixboyandgirl")
    public ResponseEntity<ResponseDTO> getMixBoyAndGirl() {
        try {
            listSEService.getKids().getMixBoyAndGirl();
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se ha intercalado la lista de niños y niñas correctamente.", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400,
                    e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/loseposition/{identification}/{position}")
    public ResponseEntity<ResponseDTO> getLosePosition(@PathVariable String identification, @PathVariable int position) {
        try {
            listSEService.getKids().getLosePosition(identification, position);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño con identificación "
                    + identification + " ha perdido " + position + " posición/es", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400,
                    e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/gainposition/{identification}/{position}")
    public ResponseEntity<ResponseDTO> getGainPosition(@PathVariable String identification, @PathVariable int position) {
        try {
            listSEService.getKids().getGainPosition(identification, position);
            return new ResponseEntity<>(new ResponseDTO(200, "El niño con identificación "
                    + identification + " ha ganado " + position + " posición/es", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400,
                    e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

   @GetMapping(path = "/ordertoendkidbyletter/{letter}")
    public ResponseEntity<ResponseDTO> getOrderToEndKidByLetter(@PathVariable String letter) {
       try {
           listSEService.getKids().getOrderToEndKidByLetter(letter);
           return new ResponseEntity<>(new ResponseDTO(200, "Se colocaron los ninos que empiezan por la letra "
                           + letter + " al final de la lista", null), HttpStatus.OK);
       } catch (ListSEException e) {
           return new ResponseEntity<>(new ResponseDTO(400,
                   e.getMessage(), null), HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping(path = "/generateagerangereport/{ageMin}/{ageMax}")
    public ResponseEntity<ResponseDTO> getGenerateAgeRangeReport(@PathVariable byte ageMin, @PathVariable byte ageMax) {
        ReportKidsByAgeRangeDTO report;
        try {
            report = listSEService.getKids().getGenerateAgeRangeReport(ageMin, ageMax);
            return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400,
                    e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
