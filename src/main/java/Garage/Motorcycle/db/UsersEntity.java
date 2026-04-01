package Garage.Motorcycle.db;

import Garage.Motorcycle.userClass.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//creating table
@Table(name="users")
@Entity()
public class UsersEntity {
    @Id
//    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="email", nullable=false)
    private String email;
    @Column(name = "password", nullable = false)
    private  String password;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;
    //users motorcycle
    @OneToMany(
            mappedBy = "usersEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MotorcycleEntity> motorcycles = new ArrayList<>();

    //checking account mail verification
    @Column(name = "account_verified")
    private boolean accountVerified;

    //reciveing token itself
    @OneToMany(mappedBy = "usersEntity",
    fetch=FetchType.LAZY,
    cascade = CascadeType.ALL,//deleting everything if user was deleted
            orphanRemoval = true

    )
    Set<SecureTokenEntity> tokens;

    //password encoder
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    public UsersEntity(){

    }
    public UsersEntity(Long id, String email, String password, LocalDateTime creationDate, List<MotorcycleEntity> motorcycles, UserRole userRole, boolean accountVerified){
        this.id=id;
        this.email=email;
        this.password=password;
        this.creationDate=creationDate;
        this.motorcycles=motorcycles;
        this.userRole=userRole;
        this.accountVerified=accountVerified;
    }
    //getters for retransforming
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public  List<MotorcycleEntity> getMotorcycles(){return motorcycles;}
    public UserRole getUserRole(){return userRole;}
    //setter

    public void setId(Long id) {
        this.id = id;
    }
    public void setCreationDate(LocalDateTime localDateTime){
        this.creationDate=localDateTime;
    }
    public void setPassword(String password){this.password=password;}
    public void setAccountVerified(boolean accountVerified) {this.accountVerified = accountVerified;}
}
