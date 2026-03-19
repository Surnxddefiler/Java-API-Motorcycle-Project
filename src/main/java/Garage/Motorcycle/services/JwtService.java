package Garage.Motorcycle.services;

import Garage.Motorcycle.MotocycleClass.MotorcycleRequest;
import Garage.Motorcycle.MotocycleClass.MotorcycleResponse;
import Garage.Motorcycle.MotorcycleMapper;
import Garage.Motorcycle.customExeptions.InvalidPageSize;
import Garage.Motorcycle.customExeptions.InvalidYear;
import Garage.Motorcycle.customExeptions.MotorcycleNotFoundException;
import Garage.Motorcycle.customExeptions.UserNotFoundException;
import Garage.Motorcycle.db.MotorcycleEntity;
import Garage.Motorcycle.db.MotorcycleRepository;
import Garage.Motorcycle.db.UserRepository;
import Garage.Motorcycle.db.UsersEntity;
import Garage.Motorcycle.domain.MotorcycleFilters;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class JwtService {
    //secret key for generating jwt token env variable
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String email){
        //creating claims
        Map<String, Object> claims=new HashMap<>();
        //returning jwt token
        return Jwts.builder()
                .claims(claims) //claims we use for an additional info for token, for example: claims.put("role", "USER"); claims.put("id", 5);
                .subject(email)//the main identifier of user
                .issuedAt(new Date(System.currentTimeMillis())) //start time
                .expiration(new Date(System.currentTimeMillis()+1000*60*30)) //end time
                .signWith(getKey())//sign with a secret key
                .compact();// compile it all
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

}
