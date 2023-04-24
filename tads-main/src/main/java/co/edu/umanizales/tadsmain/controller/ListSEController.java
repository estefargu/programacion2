package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.*;
import co.edu.umanizales.tadsmain.model.Gender;
import co.edu.umanizales.tadsmain.model.Kid;
import co.edu.umanizales.tadsmain.model.Location;
import co.edu.umanizales.tadsmain.service.GenderService;
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
    @Autowired
    private GenderService genderService;

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
        boolean identification = listSEService.getKids().confirmKidById(kidDTO.getIdentification());
        if(identification){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        Gender gender = genderService.getGenderByGender(kidDTO.getGender());
        if(gender == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "El genero ingresado no existe",
                    null), HttpStatus.OK);
        }
        listSEService.getKids().addToStart(
                new Kid(kidDTO.getIdentification(),
                        kidDTO.getName(), kidDTO.getAge(),
                        gender, location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el nino al inicio de la lista",
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
        boolean identification = listSEService.getKids().confirmKidById(kidDTO.getIdentification());
        if(identification){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }
        Gender gender = genderService.getGenderByGender(kidDTO.getGender());
        if(gender == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404, "El genero ingresado no existe",
                    null), HttpStatus.OK);
        }
        listSEService.getKids().add(
                new Kid(kidDTO.getIdentification(),
                        kidDTO.getName(), kidDTO.getAge(),
                        gender, location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el nino al final de la lista",
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
                return new ResponseEntity<>(new ResponseDTO(
                        200,kidsByLocationDTOList,
                        null), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                309,"No se han registrado departamentos",
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/kidsbycities")
    public ResponseEntity<ResponseDTO> getKidsByCities(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(8)) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                return new ResponseEntity<>(new ResponseDTO(
                        200,kidsByLocationDTOList,
                        null), HttpStatus.OK);
            }

        }
      return new ResponseEntity<>(new ResponseDTO(
                309,"No se han registrado ciudades",
                null), HttpStatus.OK);
    }

   @GetMapping(path = "/kidsbycitiesandgenderandgreaterthanage/{age}")
   public ResponseEntity<ResponseDTO> getKidsByCitiesAndGenderAndGreaterThanAge(@PathVariable byte age) {
       List<KidsByLocationAndGenderDTO> kidsByLocationAndGenderDTOList = new ArrayList<>();
       for (Location loc : locationService.getLocationsByCodeSize(8)) {
           int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
           if (count > 0) {
               List<KidsByGenderDTO> kidsByGenderDTOList = new ArrayList<>();
               int countF = listSEService.getKids().getCountKidByLocationAndGenderAndGreaterThanAge
                       (loc.getCode(), false, age);

               int countM = listSEService.getKids().getCountKidByLocationAndGenderAndGreaterThanAge
                       (loc.getCode(), true, age);

               Gender genderF = genderService.getGenderById(false);
               Gender genderM = genderService.getGenderById(true);
               if (countF > 0 || countM > 0) { // para verificar que los ninos si cumplan con el criterio de la edad dada por parametro
                   if (countF > 0) {
                       kidsByGenderDTOList.add(new KidsByGenderDTO(genderF, countF));
                   }
                   if (countM > 0) {
                       kidsByGenderDTOList.add(new KidsByGenderDTO(genderM, countM));
                   }
                   kidsByLocationAndGenderDTOList.add(new KidsByLocationAndGenderDTO
                           (new ArrayList<>(kidsByGenderDTOList), loc, count));
               }
           }
       }

       if (kidsByLocationAndGenderDTOList.isEmpty()) {
           return new ResponseEntity<>(new ResponseDTO(
                   404, "No se encontraron niños que cumplan con el criterio de edad mayor a " + age,
                   null), HttpStatus.NOT_FOUND);
       } else {
           return new ResponseEntity<>(new ResponseDTO(
                   200, kidsByLocationAndGenderDTOList,
                   null), HttpStatus.OK);
       }
   }

    @GetMapping(path = "/averageagebykid")
    public ResponseEntity<ResponseDTO> getAverageAgeByKid() {

        return new ResponseEntity<>(new ResponseDTO(200,
                listSEService.getKids().getAverageAgeByKid(), null), HttpStatus.OK);
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
}
