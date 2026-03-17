package Garage.Motorcycle;

import Garage.Motorcycle.MotocycleClass.Motorcycle;
import Garage.Motorcycle.MotocycleClass.MotorcycleRequest;
import Garage.Motorcycle.MotocycleClass.MotorcycleResponse;
import Garage.Motorcycle.db.MotorcycleEntity;
import Garage.Motorcycle.db.UsersEntity;
import Garage.Motorcycle.userClass.User;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleMapper {

    //retransform
    public Motorcycle reTransform(MotorcycleEntity motorcycleEntity) {
        return new Motorcycle(motorcycleEntity.getId(), motorcycleEntity.getModel(), motorcycleEntity.getMark(), motorcycleEntity.getYear(), motorcycleEntity.getMileage(), motorcycleEntity.getEngineCc(), motorcycleEntity.getMotorcycleType(), motorcycleEntity.getUsersEntity());
    }
    public MotorcycleEntity toEntity(MotorcycleRequest motorcycle, UsersEntity user){
        return new MotorcycleEntity(motorcycle.id(), motorcycle.model(), motorcycle.mark(), motorcycle.year(), motorcycle.mileage(), motorcycle.engineCc(), motorcycle.motorcycleType(), user);
    }
    //for response without user
    public MotorcycleResponse toResponse(MotorcycleEntity motorcycleEntity) {
        return new MotorcycleResponse(motorcycleEntity.getId(), motorcycleEntity.getModel(), motorcycleEntity.getMark(), motorcycleEntity.getYear(), motorcycleEntity.getMileage(), motorcycleEntity.getEngineCc(), motorcycleEntity.getMotorcycleType());
    }
}
