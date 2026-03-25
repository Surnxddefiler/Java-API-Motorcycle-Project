package Garage.Motorcycle.controller;

import Garage.Motorcycle.MotocycleClass.MotorcycleRequest;
import Garage.Motorcycle.MotocycleClass.MotorcycleResponse;
import Garage.Motorcycle.MotocycleClass.MotorcycleType;
import Garage.Motorcycle.domain.MotorcycleFilters;
import Garage.Motorcycle.services.MotorcycleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user/{userId}/motorcycles")
//allowing only admins to post motorcycle
@PreAuthorize("hasRole('ADMIN')")
public class AdminMotorcycleController {
    //initializing logger
    private static final Logger log= LoggerFactory.getLogger(AdminMotorcycleController.class);
//getting motorcycle service
    private MotorcycleService motorcycleService;
    public AdminMotorcycleController(MotorcycleService motorcycleService){
        this.motorcycleService=motorcycleService;
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
        this.motorcycleService.deleteMotorcycle(userId,id);
    }
}


