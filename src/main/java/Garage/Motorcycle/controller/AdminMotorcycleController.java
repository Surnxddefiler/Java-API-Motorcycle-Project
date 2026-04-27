package Garage.Motorcycle.controller;

import Garage.Motorcycle.MotocycleClass.MotorcycleRequest;
import Garage.Motorcycle.MotocycleClass.MotorcycleResponse;
import Garage.Motorcycle.services.MotorcycleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user/{userId}/motorcycles")
@PreAuthorize("hasRole('ADMIN')")//allowing only admins to post motorcycle
public class AdminMotorcycleController {
    //initializing logger
    private static final Logger log= LoggerFactory.getLogger(AdminMotorcycleController.class);
//getting motorcycle service
    private final MotorcycleService motorcycleService;
    public AdminMotorcycleController(MotorcycleService motorcycleService){
        this.motorcycleService=motorcycleService;
    }

    @PostMapping()
    public ResponseEntity<MotorcycleResponse> postMotorcycle(
            @PathVariable(name = "userId") Long userId,
            @RequestBody @Valid MotorcycleRequest motorcycle
    ){
        log.info("creating motorcycle with mark: {} model: {} engine cc: {} Motorcycle type: {} mileage: {} year: {}", motorcycle.mark(), motorcycle.model(), motorcycle.engineCc(), motorcycle.motorcycleType(), motorcycle.mileage(), motorcycle.year());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.motorcycleService.postMotorcycle(userId,motorcycle));
    }
    @PutMapping("/{id}")
    public MotorcycleResponse editMotorcycle(
            @PathVariable(name = "userId") Long userId,
            @PathVariable("id") Long id,
            @RequestBody @Valid MotorcycleRequest updatedMotorcycle)
    {
        log.info("editing motorcycle with id: {}", id);
        return this.motorcycleService.editMotorcycle(userId,id, updatedMotorcycle);
    }
    @DeleteMapping("/{id}")
    public void deleteMotocycle(
            @PathVariable(name = "userId") Long userId,
            @PathVariable("id") Long id
    ){
        log.info("deleting motorcycle");
        this.motorcycleService.deleteMotorcycle(userId,id);
    }
}


