package Garage.Motorcycle.web;

import java.time.LocalDateTime;

//error response template
public record ErrorResponse(String msg, String errorMessage, LocalDateTime errorTime) {
}
