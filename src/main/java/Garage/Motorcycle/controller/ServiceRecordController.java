package Garage.Motorcycle.controller;

import Garage.Motorcycle.serviceRecordClass.*;
import Garage.Motorcycle.services.ServiceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/motorcycles/{motorcycleId}/service-record")
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
    public Page<ServiceRecord> serviceRecordHistory(
            @AuthenticationPrincipal UserDetails userDetails,//getting user info from jwt token
            @PathVariable("motorcycleId") Long motorcycleId,
            @RequestParam(name="serviceRecordType", required = false) ServiceRecordType motorcycleRecordType,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "currentPage", required = false) Integer currentPage
    ){
        log.info("getting list of services for motorcycle with id: " + motorcycleId);
        return serviceRecordService.allRecords(userDetails.getUsername(), motorcycleId, new ServiceRecordFilters(motorcycleRecordType, pageSize, currentPage));
    }
    //endpoint for maintenance warnings
    @GetMapping("/check")
    public List<NeededMaintenance> maintenanceCheck(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("motorcycleId") Long motorcycleId
    ){
        log.info("getting needed maintenance");
        return serviceRecordService.maintenanceCheck(userDetails.getUsername(), motorcycleId);
    };
    @GetMapping("/analytics")
    public ServiceRecordAnalytics analytics(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("motorcycleId") Long motorcycleId,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate
            ){
        return serviceRecordService.getAnalytics(userDetails.getUsername(), motorcycleId, startDate, endDate);
    }
}
