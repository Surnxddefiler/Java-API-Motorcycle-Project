package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token is expired");
    }
}