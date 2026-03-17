package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class EmailExists extends RuntimeException {

    public EmailExists(String email) {
        super("Email: "+email+" already exists");
    }
}