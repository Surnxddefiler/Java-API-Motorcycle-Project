package Garage.Motorcycle.controller;

import Garage.Motorcycle.MotocycleClass.Motorcycle;
import Garage.Motorcycle.MotocycleClass.MotorcycleRequest;
import Garage.Motorcycle.MotocycleClass.MotorcycleResponse;
import Garage.Motorcycle.MotocycleClass.MotorcycleType;
import Garage.Motorcycle.domain.MotorcycleFilters;
import Garage.Motorcycle.services.MotorcycleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/motorcycles")
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
    public List<MotorcycleResponse> getAllMotorcycles(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name="motorcycleType", required = false) MotorcycleType motorcycleType,
            @RequestParam(name="mark", required = false) String mark,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "currentPage", required = false) Integer currentPage

    ){
        log.info("Getting Motorcycles with mark: "+mark+ " and type: "+motorcycleType);
        return this.motorcycleService.getAllMotorcycles(userId, new MotorcycleFilters(motorcycleType, mark, pageSize, currentPage));
    }
    @GetMapping("/{id}")
    public MotorcycleResponse getMotorcycleById(
            @PathVariable(name = "userId") Long userId,
            @PathVariable("id") Long id

    ){
        log.info("Getting Motorcycles with id: "+id);
        return this.motorcycleService.getMotocycleById(userId, id);
    }
    @PostMapping()
    public ResponseEntity<MotorcycleResponse> postMotorcycle(
            @PathVariable(name = "userId") Long userId,
            @RequestBody @Valid MotorcycleRequest motorcycle
    ){
        log.info("creating motorcycle with mark: "+ motorcycle.mark()+ " model: " + motorcycle.model()+" engine cc: " + motorcycle.engineCc() + " Motorcycle type: "+motorcycle.motorcycleType()+ " mileage: "+ motorcycle.mileage()+ " year: "+motorcycle.year());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.motorcycleService.postMotorcycle(userId,motorcycle));
    }
    @PutMapping("/{id}")
    public MotorcycleResponse editMotorcycle(
            @PathVariable(name = "userId") Long userId,
            @PathVariable("id") Long id,
            @RequestBody @Valid MotorcycleRequest updatedMotorcycle)
    {
        log.info("editing moto with id: "+id);
        return this.motorcycleService.editMotorcycle(userId,id, updatedMotorcycle);
    }
    @DeleteMapping("/{id}")
    public void deleteMotocycle(
            @PathVariable(name = "userId") Long userId,
            @PathVariable("id") Long id
    ){
        log.info("deleteing moto");
        this.motorcycleService.deleteMotocycle(userId,id);
    }
}


