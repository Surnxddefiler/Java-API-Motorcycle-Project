package Garage.Motorcycle.userClass;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull
        String email,
        @NotNull
        String password
) {
}
