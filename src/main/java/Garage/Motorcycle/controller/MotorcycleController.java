package Garage.Motorcycle.controller;

import Garage.Motorcycle.MotocycleClass.*;
import Garage.Motorcycle.domain.MotorcycleFilters;
import Garage.Motorcycle.services.MotorcycleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/motorcycles")
public class MotorcycleController {
    //initializing logger
    private static final Logger log= LoggerFactory.getLogger(MotorcycleController.class);
//getting motorcycle service
    private MotorcycleService motorcycleService;
    public MotorcycleController(MotorcycleService motorcycleService){
        this.motorcycleService=motorcycleService;
    }
    //getting all users Motorcycles
    @GetMapping()
    public Page<MotorcycleResponse> getAllMotorcycles(
            @AuthenticationPrincipal UserDetails userDetails,//getting user info from jwt token
            @RequestParam(name="motorcycleType", required = false) MotorcycleType motorcycleType,
            @RequestParam(name="mark", required = false) String mark,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "currentPage", required = false) Integer currentPage,
            @RequestParam(name="minYear", required = false) Integer minYear, //minimal year
            @RequestParam(name="maxYear", required = false) Integer maxYear,//max year
            @RequestParam(name = "minCc", required = false) Integer minCc, //min cc
            @RequestParam(name = "maxCc", required = false) Integer maxCc, //max cc
            @RequestParam(name="sort", required = false) MotorcycleOrderBy motorcycleOrderBy //order by
            ){
        log.info("Getting Motorcycles with mark: "+mark+ " and type: "+motorcycleType);
        return this.motorcycleService.getAllMotorcycles(userDetails.getUsername(), new MotorcycleFilters(motorcycleType, mark, pageSize, currentPage, minYear, maxYear, minCc,maxCc, motorcycleOrderBy));
    }
    @GetMapping("/{id}")
    public MotorcycleResponse getMotorcycleById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Long id

    ){
        log.info("Getting Motorcycles with id: "+id);
        return this.motorcycleService.getMotocycleById(userDetails.getUsername(), id);
    }

}


