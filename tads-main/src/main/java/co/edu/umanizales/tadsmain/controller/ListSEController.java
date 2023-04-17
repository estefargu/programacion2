package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.KidDTO;
import co.edu.umanizales.tadsmain.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tadsmain.controller.dto.ResponseDTO;
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
                200,"SE ha invertido la lista",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        }
        //boolean identification = listSEService.getKids().getKidById(kidDTO.getIdentification());
        if(listSEService.getKids().getKidById(kidDTO.getIdentification())){
            return new ResponseEntity<>(new ResponseDTO(
                    409, "La identificación del niño ya existe",
                    null), HttpStatus.BAD_REQUEST);
        }

        listSEService.getKids().add(
                new Kid(kidDTO.getIdentification(),
                        kidDTO.getName(), kidDTO.getAge(),
                        kidDTO.getGender(), location));
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el petacón",
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
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/kidsbylocations/departments")
    public ResponseEntity<ResponseDTO> getKidsByLocationDepartments(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(5)) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }
    @GetMapping(path = "/kidsbylocations/cities")
    public ResponseEntity<ResponseDTO> getKidsByLocationCities(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(8)) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
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
