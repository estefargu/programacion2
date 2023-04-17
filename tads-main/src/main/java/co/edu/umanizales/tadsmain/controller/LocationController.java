package co.edu.umanizales.tadsmain.controller;

import co.edu.umanizales.tadsmain.controller.dto.ResponseDTO;
import co.edu.umanizales.tadsmain.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllLocations(){
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(200,
                locationService.getLocations(),null), HttpStatus.OK);
    }

    @GetMapping(path = "/countries")
    public ResponseEntity<ResponseDTO> getCountries(){
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(200,
                locationService.getLocationsByCodeSize(3),null), HttpStatus.OK);
    }

    @GetMapping(path = "/departments")
    public ResponseEntity<ResponseDTO> getDepartments(){
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(200,
                locationService.getLocationsByCodeSize(5),null), HttpStatus.OK);
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<ResponseDTO> getCities(){
        return new ResponseEntity<ResponseDTO>(new ResponseDTO(200,
                locationService.getLocationsByCodeSize(8),null), HttpStatus.OK);
    }
}
