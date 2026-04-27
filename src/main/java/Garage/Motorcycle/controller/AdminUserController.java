package Garage.Motorcycle.controller;

import Garage.Motorcycle.domain.UserFilters;
import Garage.Motorcycle.services.UserService;
import Garage.Motorcycle.userClass.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
//    logger
    private static final Logger log= LoggerFactory.getLogger(AdminUserController.class);
    private final UserService userService;
    public AdminUserController(UserService userService){this.userService=userService;}
    //add user
    @GetMapping()
    public Page<UserResponse> getAllUsers(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "currentPage", required = false) Integer currentPage,
            @RequestParam(name="email", required = false) String email
    ){
        log.info("getting all users");
        return this.userService.getAllUsers(new UserFilters(pageSize, currentPage, email));
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id){
        return userService.deleteUser(id);
    }
}
