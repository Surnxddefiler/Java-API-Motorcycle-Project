package Garage.Motorcycle.customExeptions;


//custom Exception if bike not found
public class InvalidPageSize extends RuntimeException {

    public InvalidPageSize(int pageSize) {
        super("Page size should be more then 0 and less then 20, yours is "+pageSize);
    }
}