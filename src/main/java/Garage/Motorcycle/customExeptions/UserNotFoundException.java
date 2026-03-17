package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found");
    }
}