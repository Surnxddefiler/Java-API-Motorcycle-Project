package Garage.Motorcycle.services;

import Garage.Motorcycle.db.SecureTokenEntity;
import Garage.Motorcycle.db.SecureTokenRepository;
import Garage.Motorcycle.db.UsersEntity;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class SecureTokenService {
    //creating token
    private static BytesKeyGenerator DEFAULT_TOKEN_GENERATOR= KeyGenerators.secureRandom(12);
    private static int tokenTimeInSeconds=2800;
    //repository adding
    private final SecureTokenRepository secureTokenRepository;
    public SecureTokenService(SecureTokenRepository secureTokenRepository){
     this.secureTokenRepository=secureTokenRepository;
    }
    //crating a token logic(passing User as a parameter)
    public SecureTokenEntity createToken(UsersEntity usersEntity){
        //token value
        String tokenValue=new String(Base64.getUrlEncoder().withoutPadding().encodeToString(DEFAULT_TOKEN_GENERATOR.generateKey()));
        //creating secureToken
        SecureTokenEntity secureTokenEntity=new SecureTokenEntity();
        //setting user
        secureTokenEntity.setUsersEntity(usersEntity);
        //putting token into entity
        secureTokenEntity.setToken(tokenValue);
        //adding token
        secureTokenEntity.setExpiredAt(LocalDateTime.now().plusSeconds(tokenTimeInSeconds));
        //saving token
        secureTokenRepository.save(secureTokenEntity);
        return secureTokenEntity;
    };

    public SecureTokenEntity verifyToken(String token){
        return secureTokenRepository.findByToken(token);
    };

    public void deleteToken(String token) {
        secureTokenRepository.removeByToken(token);
    }
}
