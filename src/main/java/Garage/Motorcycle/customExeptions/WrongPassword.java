package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class WrongPassword extends RuntimeException {

    public WrongPassword() {
        super("incorrect password, please try again");
    }
}