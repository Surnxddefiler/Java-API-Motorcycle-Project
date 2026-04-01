package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class SecureTokenNotFoundException extends RuntimeException {

    public SecureTokenNotFoundException() {
        super("Secure Token not found");
    }
}