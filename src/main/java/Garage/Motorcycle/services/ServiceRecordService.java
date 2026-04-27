package Garage.Motorcycle.services;

import Garage.Motorcycle.MotocycleClass.MotorcycleType;
import Garage.Motorcycle.ServiceRecordMapper;
import Garage.Motorcycle.customExeptions.*;
import Garage.Motorcycle.db.*;
import Garage.Motorcycle.serviceRecordClass.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceRecordService {
    //    spring boot beans
    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final ServiceRecordRepository serviceRecordRepository;
    private final ServiceRecordMapper serviceRecordMapper;

    public ServiceRecordService(UserRepository userRepository, MotorcycleRepository motorcycleRepository, ServiceRecordRepository serviceRecordRepository, ServiceRecordMapper serviceRecordMapper) {
        this.userRepository = userRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.serviceRecordRepository = serviceRecordRepository;
        this.serviceRecordMapper = serviceRecordMapper;
    }

    //getting all records
    public Page<ServiceRecord> allRecords(String email, Long motorcycleId, ServiceRecordFilters serviceRecordFilters) {
        userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        motorcycleRepository.findByIdAndUsersEntityEmail(motorcycleId, email).orElseThrow(() -> new MotorcycleNotFoundException(motorcycleId));


        //creating pageable
        int pageSize = serviceRecordFilters.pageSize() != null ? serviceRecordFilters.pageSize() : 3;
        int currentPage = serviceRecordFilters.currentPage() != null ? serviceRecordFilters.currentPage() : 0;
        if (pageSize <= 0 || pageSize > 20) {
            throw new InvalidPageSize(pageSize);
        }
        Pageable pageable = Pageable.ofSize(pageSize).withPage(currentPage);
        Page<ServiceRecordEntity> serviceRecordList = serviceRecordRepository.searchAllByFilters(motorcycleId, serviceRecordFilters.serviceRecordType(), pageable);
        return serviceRecordList.map(serviceRecordMapper::reTransform);
    }

    public ServiceRecord addRecord(Long userId, Long motorcycleId, @Valid ServiceRecord serviceRecord) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        MotorcycleEntity motorcycleEntity = motorcycleRepository.findByIdAndUsersEntityId(motorcycleId, userId).orElseThrow(() -> new MotorcycleNotFoundException(motorcycleId));

        //checking mileage error, if everything is good, new mileage will appear
        if (motorcycleEntity.getMileage() > serviceRecord.mileage()) {
            throw new InvalidMileage(serviceRecord.mileage(), motorcycleEntity.getMileage());
        } else {
            //rewriting mileage
            motorcycleEntity.setMileage(serviceRecord.mileage());
            motorcycleRepository.save(motorcycleEntity);
        }
        //saving service record
        ServiceRecordEntity entity = serviceRecordMapper.toEntity(serviceRecord, motorcycleEntity);
        entity.setServiceTime(LocalDateTime.now());
        //saving and remaping to default class
        serviceRecordRepository.save(entity);
        return serviceRecordMapper.reTransform(entity);
    }

    //checking what needs to be maintained asap
    public List<NeededMaintenance> maintenanceCheck(String email, Long motorcycleId) {
        List<NeededMaintenance> maintainList = new ArrayList<>();
        //checking motorcycle info
        userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        MotorcycleEntity motorcycleEntity = motorcycleRepository.findByIdAndUsersEntityEmail(motorcycleId, email).orElseThrow(() -> new MotorcycleNotFoundException(motorcycleId));

        //looping all types of maintenance
        for (ServiceRecordType type : ServiceRecordType.values()) {
//            finding last recorded maintenance
            ServiceRecordEntity foundMaintenance = serviceRecordRepository.findFirstByMotorcycleEntityIdAndServiceRecordTypeOrderByServiceTimeDesc(motorcycleId, type).orElse(null);
            //mileage difference for maintenance logic

            //logic of maintenance needed
            switch (type) {
                //oil logic(we will look on motorcycle type for knowing when to change oil and look at its mileage)
                case OIL:
                    if (foundMaintenance!=null) {
                        int mileageDifference = motorcycleEntity.getMileage() - foundMaintenance.getMileage();
                        if (LocalDateTime.now().getYear() - foundMaintenance.getServiceTime().getYear() >= 1) {
                            maintainList.add(new NeededMaintenance(ServiceRecordType.OIL, foundMaintenance.getServiceTime(), mileageDifference));
                        } else if (motorcycleEntity.getMotorcycleType() == MotorcycleType.CHOPPER && mileageDifference >= 10000) {
                            maintainList.add(new NeededMaintenance(ServiceRecordType.OIL, foundMaintenance.getServiceTime(), mileageDifference));
                        } else if (motorcycleEntity.getMotorcycleType() == MotorcycleType.SPORT && mileageDifference >= 3000) {
                            maintainList.add(new NeededMaintenance(ServiceRecordType.OIL, foundMaintenance.getServiceTime(), mileageDifference));
                        } else if (mileageDifference >= 8000) {
                            maintainList.add(new NeededMaintenance(ServiceRecordType.OIL, foundMaintenance.getServiceTime(), mileageDifference));
                        }
                    }
                    else {
                        maintainList.add(new NeededMaintenance(ServiceRecordType.OIL, null, 0));
                    }
                    break;
                    //chain logic
                case CHAIN:
                    if (foundMaintenance!=null){
                        int mileageDifference = motorcycleEntity.getMileage() - foundMaintenance.getMileage();
                        if (mileageDifference>700){
                            maintainList.add(new NeededMaintenance(ServiceRecordType.CHAIN, foundMaintenance.getServiceTime(), mileageDifference));
                        }
                    }
                    else {
                        maintainList.add(new NeededMaintenance(ServiceRecordType.CHAIN, null, 0));
                    }
                    break;
                    //brakes logic
                case BRAKES:
                    if (foundMaintenance!=null){
                        int mileageDifference = motorcycleEntity.getMileage() - foundMaintenance.getMileage();
                        if (mileageDifference>7000){
                            maintainList.add(new NeededMaintenance(ServiceRecordType.BRAKES, foundMaintenance.getServiceTime(), mileageDifference));
                        }
                    }
                    else {
                        maintainList.add(new NeededMaintenance(ServiceRecordType.BRAKES, null, 0));
                    }

                    break;
                    //tires logic
                case TIRES:
                    if (foundMaintenance!=null){
                        int mileageDifference = motorcycleEntity.getMileage() - foundMaintenance.getMileage();
                        if (LocalDateTime.now().getYear()-foundMaintenance.getServiceTime().getYear()>4){
                            maintainList.add(new NeededMaintenance(ServiceRecordType.TIRES, foundMaintenance.getServiceTime(), mileageDifference));
                        } else if (motorcycleEntity.getMotorcycleType()==MotorcycleType.SPORT && mileageDifference>3000) {
                            maintainList.add(new NeededMaintenance(ServiceRecordType.TIRES, foundMaintenance.getServiceTime(), mileageDifference));
                        } else if (motorcycleEntity.getMotorcycleType()==MotorcycleType.CHOPPER && mileageDifference>10000) {
                            maintainList.add(new NeededMaintenance(ServiceRecordType.TIRES, foundMaintenance.getServiceTime(), mileageDifference));
                        }
                    }
                    else {
                        maintainList.add(new NeededMaintenance(ServiceRecordType.TIRES, null, 0));
                    }

                    break;
            }
        }
        return maintainList;
    }
    //deleting info
    public String deleteServiceRecord(Long userId, Long serviceId, Long motorcycleId){
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        //MotorcycleEntity motorcycleEntity = motorcycleRepository.findByIdAndUsersEntityId(motorcycleId, userId).orElseThrow(() -> new MotorcycleNotFoundException(motorcycleId));
        ServiceRecordEntity serviceRecordEntity=serviceRecordRepository.findByServiceIdAndMotorcycleEntityId(serviceId, motorcycleId).orElseThrow(()->new ServiceRecordNotFoundException(serviceId));
        serviceRecordRepository.delete(serviceRecordEntity);
        return "Service Deleted Successfully";
    }

    //getting analytics
    public ServiceRecordAnalytics getAnalytics(String email, Long motorcycleId, LocalDateTime startDate, LocalDateTime endDate) {

        userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        motorcycleRepository.findByIdAndUsersEntityEmail(motorcycleId, email).orElseThrow(() -> new MotorcycleNotFoundException(motorcycleId));

        return serviceRecordRepository.getAnalytics(motorcycleId, startDate, endDate);
    }
}
