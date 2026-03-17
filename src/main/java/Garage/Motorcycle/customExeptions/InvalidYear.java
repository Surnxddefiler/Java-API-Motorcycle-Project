package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class InvalidYear extends RuntimeException {

    public InvalidYear(int year) {
        super("Year is invalid: "+year);
    }
}