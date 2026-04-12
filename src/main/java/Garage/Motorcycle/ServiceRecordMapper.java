package Garage.Motorcycle;

import Garage.Motorcycle.db.MotorcycleEntity;
import Garage.Motorcycle.db.ServiceRecordEntity;
import Garage.Motorcycle.serviceRecordClass.ServiceRecord;
import org.springframework.stereotype.Component;

@Component
public class ServiceRecordMapper {
    public ServiceRecord reTransform(ServiceRecordEntity serviceRecordEntity){
        return new ServiceRecord(serviceRecordEntity.getId(),serviceRecordEntity.getServiceTime(), serviceRecordEntity.getServiceRecordType(), serviceRecordEntity.getMileage(), serviceRecordEntity.getComment(), serviceRecordEntity.getPrice());
    }
    public ServiceRecordEntity toEntity(ServiceRecord serviceRecord, MotorcycleEntity motorcycleEntity){
        return new ServiceRecordEntity(null, null, serviceRecord.serviceRecordType(), serviceRecord.mileage(), serviceRecord.comment(), motorcycleEntity, serviceRecord.price());
    }
}
