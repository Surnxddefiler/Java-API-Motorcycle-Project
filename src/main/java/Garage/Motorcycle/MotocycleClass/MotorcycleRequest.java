package Garage.Motorcycle.MotocycleClass;


//request body
public record MotorcycleRequest(
        Long id,
        String mark,
        String model,
        int year,
        int mileage,
        int engineCc,
        MotorcycleType motorcycleType
) {
}
