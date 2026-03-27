package Garage.Motorcycle.services;

import Garage.Motorcycle.UserMapper;
import Garage.Motorcycle.customExeptions.EmailExists;
import Garage.Motorcycle.customExeptions.InvalidPageSize;
import Garage.Motorcycle.customExeptions.UserNotFoundException;
import Garage.Motorcycle.customExeptions.WrongPassword;
import Garage.Motorcycle.db.UserRepository;
import Garage.Motorcycle.db.UsersEntity;
import Garage.Motorcycle.domain.UserFilters;
import Garage.Motorcycle.userClass.LoginRequest;
import Garage.Motorcycle.userClass.UserRequest;
import Garage.Motorcycle.userClass.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    //adding jwt
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

    public String addUser(@Valid UserRequest user) {
        //if email exists
        if (userRepository.existsByEmail(user.email())){
            throw new EmailExists(user.email());
        }
        UsersEntity toEntity=userMapper.toEntity(user);
//        creating date
        toEntity.setCreationDate(LocalDateTime.now());
        //encoding paw
        toEntity.setPassword(passwordEncoder.encode(user.password()));
        userRepository.save(toEntity);
        return "User created successfully";
    }
//    login logic
    public  String loginUser(LoginRequest loginRequest){
        UsersEntity users=userRepository.findByEmail(loginRequest.email()).orElseThrow(()->new UserNotFoundException());
        if (!passwordEncoder.matches(loginRequest.password(), users.getPassword())){
            throw new WrongPassword();
        }
        return jwtService.generateToken(loginRequest.email());
                    }
    public String deleteUser(Long userId){
        UsersEntity user=userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return "user deleted successfully";
    }

    //get all users logic
    public Page<UserResponse> getAllUsers(UserFilters userFilters) {
        //paging
        int pageSize=userFilters.pageSize()!=null ? userFilters.pageSize() : 3;
        int currentPage=userFilters.currentPage()!=null ? userFilters.currentPage() : 0;
//        error checking
        if (pageSize<=0 || pageSize>20){
            throw new InvalidPageSize(pageSize);
        }
        //creating pageable

        Pageable pageable=Pageable.ofSize(pageSize).withPage(currentPage);

        //checking if email is empty
        String email=userFilters.email()!=null ? userFilters.email() : "";
        Page<UsersEntity> userResponses=userRepository.findByEmailContainingIgnoreCase(email,pageable);
        return userResponses.map(userMapper::toResponse);
    }
}
