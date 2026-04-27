package Garage.Motorcycle.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//we made also
@Repository
public interface SecureTokenRepository extends JpaRepository<SecureTokenEntity, Long> {

    //funding token by token
    SecureTokenEntity findByToken(String token);
    //remove token
    Long removeByToken(String token);
}
