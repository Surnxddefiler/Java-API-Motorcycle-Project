package Garage.Motorcycle.controller;

import Garage.Motorcycle.serviceRecordClass.ServiceRecord;
import Garage.Motorcycle.services.ServiceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users/{userId}/motorcycles/{motorcycleId}/service-record")
@PreAuthorize("hasRole('ADMIN')")
public class AdminServiceRecordController {
    //initializing logger
    private static final Logger log= LoggerFactory.getLogger(AdminServiceRecordController.class);

    //beans
    private final ServiceRecordService serviceRecordService;
    public AdminServiceRecordController(ServiceRecordService serviceRecordService){
        this.serviceRecordService=serviceRecordService;
    }

    //adding new service record
    @PostMapping()
    public ResponseEntity<ServiceRecord> addServiceRecord(
            @PathVariable("userId") Long userId,
            @PathVariable("motorcycleId") Long motorcycleId,
            @RequestBody ServiceRecord serviceRecord
    ){
        log.info("new service done: {} comment: {}", serviceRecord.serviceRecordType(), serviceRecord.comment());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.serviceRecordService.addRecord(userId, motorcycleId, serviceRecord));
    }
//    deleting service record
    @DeleteMapping("/{serviceId}")
    public String deleteServiceRecord(
            @PathVariable("userId") Long userId,
            @PathVariable("motorcycleId") Long motorcycleId,
            @PathVariable("serviceId") Long serviceId
    ){
        log.info("deleting service record with id: {}", serviceId);
        return serviceRecordService.deleteServiceRecord(userId, serviceId, motorcycleId);
    }
}
