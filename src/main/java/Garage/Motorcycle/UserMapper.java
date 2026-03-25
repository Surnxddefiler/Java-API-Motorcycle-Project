package Garage.Motorcycle;

import Garage.Motorcycle.MotocycleClass.Motorcycle;
import Garage.Motorcycle.MotocycleClass.MotorcycleRequest;
import Garage.Motorcycle.MotocycleClass.MotorcycleResponse;
import Garage.Motorcycle.db.MotorcycleEntity;
import Garage.Motorcycle.db.UsersEntity;
import Garage.Motorcycle.userClass.User;
import Garage.Motorcycle.userClass.UserRequest;
import Garage.Motorcycle.userClass.UserRole;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    //to entity
    public UsersEntity toEntity(UserRequest user){
        //id we will get from base,
        //creation date we will get from service code:
//        toEntity.setCreationDate(LocalDateTime.now());
//        motorcycle will be empty for now, you can add them after creating an User
//        password will be encoding in service
        return new UsersEntity(null, user.email(), null, null, null, UserRole.ROLE_USER);
    }

}
