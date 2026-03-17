package Garage.Motorcycle.serviceRecordClass;

import java.time.LocalDateTime;

public record NeededMaintenance(
        ServiceRecordType serviceRecordType,
        LocalDateTime lastCheck,
        int mileageDifference
) {
}
