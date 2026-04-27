package Garage.Motorcycle.controller;

import Garage.Motorcycle.services.UserService;
import Garage.Motorcycle.userClass.LoginRequest;
import Garage.Motorcycle.userClass.UserRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
//    logger
    private static final Logger log= LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    public UserController(UserService userService){this.userService=userService;}
    //add user
    @PostMapping("/register")
    public ResponseEntity<String> addUser(
            @RequestBody @Valid UserRequest user
            ){
        log.info("creating user with email: {}", user.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.addUser(user));
    }
    //login user and getting user token
    @GetMapping("/verify")
    private ResponseEntity<String> verifyUser(@RequestParam String token){
        log.info("verify user");
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.verifyUser(token));
    }
    @PostMapping("/login")
    public ResponseEntity<String> logUser(@RequestBody @Valid LoginRequest login){
        log.info("Trying to log: {}", login.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.loginUser(login));
    }
}
