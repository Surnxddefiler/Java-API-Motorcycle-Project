package Garage.Motorcycle.db;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//creating table for email tokens
//creating table
@Table(name="secure_tokens")
@Entity()
public class SecureTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expired_at", nullable = false, updatable = false)
    @Basic(optional = false)
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity usersEntity;
    //password encoder
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    public SecureTokenEntity() {

    }


    public SecureTokenEntity(Long id, String token, LocalDateTime expiredAt, UsersEntity usersEntity) {
        this.id = id;
        this.token=token;
        this.expiredAt=expiredAt;
        this.usersEntity=usersEntity;
    }

    //getters for retransforming
    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public UsersEntity getUsersEntity() {
        return usersEntity;
    }
    //setter

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }
}
