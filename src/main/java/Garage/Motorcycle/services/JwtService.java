package Garage.Motorcycle.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    //secret key for generating jwt token env variable
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String email) {
        //creating claims
        Map<String, Object> claims = new HashMap<>();
        //returning jwt token
        return Jwts.builder()
                .claims(claims) //claims we use for an additional info for token, for example: claims.put("role", "USER"); claims.put("id", 5);
                .subject(email)//the main identifier of user
                .issuedAt(new Date(System.currentTimeMillis())) //start time
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) //end time
                .signWith(getKey())//sign with a secret key
                .compact();// compile it all
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String decodeEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //extrecting claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    //
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //validating token
    public boolean validateToken(String token, UserDetails userDetails){
        //extracting email
        final String email=decodeEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    //checking if token is expired
    private boolean isTokenExpired(String token){
      return extractExperationDate(token).before(new Date());
    };
    //getting experation date
    private Date extractExperationDate(String token){
        return extractClaims(token, Claims::getExpiration);
    }
}
