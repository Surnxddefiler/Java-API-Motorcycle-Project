package Garage.Motorcycle.domain;

import Garage.Motorcycle.MotocycleClass.MotorcycleOrderBy;
import Garage.Motorcycle.MotocycleClass.MotorcycleType;

public record MotorcycleFilters(MotorcycleType motorcycleType, String mark, Integer pageSize, Integer currentPage, Integer minYear, Integer maxYear, Integer minCc, Integer maxCc, MotorcycleOrderBy motorcycleOrderBy) {
}
