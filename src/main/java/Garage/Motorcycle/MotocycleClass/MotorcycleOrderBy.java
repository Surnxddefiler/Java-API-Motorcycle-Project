package Garage.Motorcycle.MotocycleClass;

public enum MotorcycleOrderBy {

    YEAR("year"), ENGINE_CC("engineCc"), MOTORCYCLE_TYPE("motorcycle_type"), MARK("mark"), MILEAGE("mileage"), MODEL("model");
    private final String field;

    MotorcycleOrderBy(String field){
        this.field=field;
    }

    public String getField() {
        return field;
    }
}
