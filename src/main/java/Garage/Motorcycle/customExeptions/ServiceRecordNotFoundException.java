package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class ServiceRecordNotFoundException extends RuntimeException {

    public ServiceRecordNotFoundException(Long id) {
        super("Service with id " + id + " not found");
    }
}