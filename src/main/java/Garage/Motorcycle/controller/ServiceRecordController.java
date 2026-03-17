package Garage.Motorcycle.controller;

import Garage.Motorcycle.serviceRecordClass.NeededMaintenance;
import Garage.Motorcycle.serviceRecordClass.ServiceRecord;
import Garage.Motorcycle.serviceRecordClass.ServiceRecordFilters;
import Garage.Motorcycle.serviceRecordClass.ServiceRecordType;
import Garage.Motorcycle.services.ServiceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/motorcycles/{motorcycleId}/service-record")
public class ServiceRecordController {
    //initializing logger
    private static final Logger log= LoggerFactory.getLogger(ServiceRecordController.class);

    //beans
    private ServiceRecordService serviceRecordService;
    public ServiceRecordController(ServiceRecordService serviceRecordService){
        this.serviceRecordService=serviceRecordService;
    }
    //getting all history
    @GetMapping()
    public List<ServiceRecord> serviceRecordHistory(
            @PathVariable("userId") Long userId,
            @PathVariable("motorcycleId") Long motorcycleId,
            @RequestParam(name="serviceRecordType", required = false) ServiceRecordType motorcycleRecordType,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "currentPage", required = false) Integer currentPage
    ){
        log.info("getting list of services for motorcycle with id: " + motorcycleId);
        return serviceRecordService.allRecords(userId, motorcycleId, new ServiceRecordFilters(motorcycleRecordType, pageSize, currentPage));
    }
    //endpoint for maintenance warnings
    @GetMapping("/check")
    public List<NeededMaintenance> maintenanceCheck(
            @PathVariable("userId") Long userId,
            @PathVariable("motorcycleId") Long motorcycleId
    ){
        log.info("getting needed maintenance");
        return serviceRecordService.maintenanceCheck(motorcycleId, userId);
    };
    //adding new service record
    @PostMapping()
    public ResponseEntity<ServiceRecord> addServiceRecord(
            @PathVariable("userId") Long userId,
            @PathVariable("motorcycleId") Long motorcycleId,
            @RequestBody ServiceRecord serviceRecord
    ){
        log.info("new service done: " + serviceRecord.serviceRecordType() + " comment: "+serviceRecord.comment());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.serviceRecordService.addRecord(userId, motorcycleId, serviceRecord));
    }
//    deleting service record
    @DeleteMapping("/{serviceId}")
    public String deleteServiceRecord(
            @PathVariable("userId") Long userId,
            @PathVariable("motorcycleId") Long motorcycleId,
            @PathVariable("serviceId") Long serviceId
    ){
        log.info("deleting service record with id: " + serviceId);
        return serviceRecordService.deleteServiceRecord(userId, serviceId, motorcycleId);
    };
}
