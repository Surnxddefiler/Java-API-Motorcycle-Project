package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class MotorcycleNotFoundException extends RuntimeException {

    public MotorcycleNotFoundException(Long id) {
        super("Motorcycle with id " + id + " not found");
    }
}