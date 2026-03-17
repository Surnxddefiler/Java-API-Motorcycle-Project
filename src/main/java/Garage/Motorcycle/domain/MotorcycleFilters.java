package Garage.Motorcycle.domain;

import Garage.Motorcycle.MotocycleClass.MotorcycleType;

public record MotorcycleFilters(MotorcycleType motorcycleType, String mark, Integer pageSize, Integer currentPage) {
}
