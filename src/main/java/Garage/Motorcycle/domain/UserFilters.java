package Garage.Motorcycle.domain;

import Garage.Motorcycle.MotocycleClass.MotorcycleType;

public record UserFilters(Integer pageSize, Integer currentPage, String email) {
}
