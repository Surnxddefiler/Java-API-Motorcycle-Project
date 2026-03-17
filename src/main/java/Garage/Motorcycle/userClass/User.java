package Garage.Motorcycle.userClass;

import Garage.Motorcycle.db.MotorcycleEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;
import java.util.List;

public record User(
        @Null
        Long id,
        @NotNull
        String email,
        @NotNull
        String password,
        @Null
        LocalDateTime creationDate,
        List<MotorcycleEntity> motorcycles
) {
}
