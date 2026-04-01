package Garage.Motorcycle.services;

import Garage.Motorcycle.UserMapper;
import Garage.Motorcycle.customExeptions.*;
import Garage.Motorcycle.db.SecureTokenEntity;
import Garage.Motorcycle.db.UserRepository;
import Garage.Motorcycle.db.UsersEntity;
import Garage.Motorcycle.domain.UserFilters;
import Garage.Motorcycle.mail.AccountVerificationContext;
import Garage.Motorcycle.userClass.LoginRequest;
import Garage.Motorcycle.userClass.UserRequest;
import Garage.Motorcycle.userClass.UserResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    //base url for senidng email
    @Value("@{site.base.url.https}")
    private String baseUrl;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    //adding jwt
    private final JwtService jwtService;
    //adding token logic for an email sending + emailService
    private final  SecureTokenService secureTokenService;
    private final EmailService emailService;
    //creating variable String for init email function
    @Value("${spring.mail.username}")
    private String fromEmail;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService, SecureTokenService secureTokenService, EmailService emailService){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
        this.secureTokenService=secureTokenService;
        this.emailService=emailService;
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
        //making account not verified
        toEntity.setAccountVerified(false);
        //saving user
        userRepository.save(toEntity);
        //creating token
        SecureTokenEntity secureTokenEntity=secureTokenService.createToken(toEntity);


        //creating email template
        AccountVerificationContext accountVerificationContext=new AccountVerificationContext();
        accountVerificationContext.init(toEntity, fromEmail);//init user
        accountVerificationContext.setToken(secureTokenEntity.getToken());//passing token
        accountVerificationContext.generateUrl(baseUrl, secureTokenEntity.getToken());//generating url

        //sending email
        try {
            emailService.sendEmail(accountVerificationContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "User created successfully";
    }

    public String verifyUser(String token) {
        //getting token from db
        SecureTokenEntity secureToken=secureTokenService.verifyToken(token);
        //checking for errors
        if (secureToken==null){
            throw new SecurityException();
        }
        if (secureToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException();
        }

        //getting user and enabling User
        UsersEntity usersEntity=secureToken.getUsersEntity();
        usersEntity.setAccountVerified(true);
        userRepository.save(usersEntity);

        return "success verification";
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
