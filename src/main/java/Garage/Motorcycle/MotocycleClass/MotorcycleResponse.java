package Garage.Motorcycle.MotocycleClass;

public record MotorcycleResponse(
        Long id,
        String mark,
        String model,
        int year,
        int mileage,
        int engineCc,
        MotorcycleType motorcycleType
) {
}
