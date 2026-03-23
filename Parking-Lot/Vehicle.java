// Vehicle class and related enums
public class Vehicle {
    private final String registrationNumber;
    private final VehicleType type;
    
    public Vehicle(String registrationNumber, VehicleType type) {
        this.registrationNumber = registrationNumber;
        this.type = type;
    }
    
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public VehicleType getType() {
        return type;
    }
}

enum VehicleType {
    TWO_WHEELER,
    CAR,
    BUS
}
