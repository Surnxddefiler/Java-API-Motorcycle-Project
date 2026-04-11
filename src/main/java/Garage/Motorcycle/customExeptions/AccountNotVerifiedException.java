package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class AccountNotVerifiedException extends RuntimeException {

    public AccountNotVerifiedException(String email) {
        super("Your account is not verified, please check " + email + "for account verification");
    }
}