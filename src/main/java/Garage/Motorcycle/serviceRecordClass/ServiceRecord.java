package Garage.Motorcycle.serviceRecordClass;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record ServiceRecord(
        @Null
        Long id,
        @Null
        LocalDateTime serviceTime,
        @NotNull
        ServiceRecordType serviceRecordType,
        @NotNull
        int mileage,
        @NotNull
        String comment,
        @NotNull
        double price
) {
}
