package Garage.Motorcycle.MotocycleClass;

import Garage.Motorcycle.db.UsersEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

public record Motorcycle(
        @Null
        Long id,
        @NotNull
        String mark,
        @NotNull
        String model,
        @Positive
        @NotNull
        Integer year,
        Integer mileage,
        @Positive
        @NotNull
        Integer engineCc,
        @NotNull
        MotorcycleType motorcycleType,
        @NotNull
        UsersEntity usersEntity
) {
}
