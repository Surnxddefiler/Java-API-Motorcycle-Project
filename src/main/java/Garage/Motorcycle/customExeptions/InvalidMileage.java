package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class InvalidMileage extends RuntimeException {

    public InvalidMileage(int newMileage, int oldMileage) {
        super("mileage cannot be less then the old one, old mileage: " + oldMileage+", new mileage: " +newMileage);
    }
}