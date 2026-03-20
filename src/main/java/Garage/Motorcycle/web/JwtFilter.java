package Garage.Motorcycle.web;

import Garage.Motorcycle.services.JwtService;
import Garage.Motorcycle.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

    //passing Jwt Service for some function
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;


    public JwtFilter(JwtService jwtService, MyUserDetailsService myUserDetailsService){
        this.jwtService=jwtService;
        this.myUserDetailsService=myUserDetailsService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //From request we are receiving token like this: Bearer {token}

        //getting header authorization
        String authHeader=request.getHeader("Authorization");
        //token variable
        String token=null;
        //email variable
        String email=null;

        //checking if we have authHeader and its starts with bearer
        if (authHeader !=null && authHeader.startsWith("Bearer ")){
            //getting only token from an authHeader String
            token=authHeader.substring(7);

            //logic for email decoding in JwtService
            email=jwtService.decodeEmail(token);
        }
        //check if if email is not null and checking if user is not authenticated
        if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            //finding user
            UserDetails userDetails= myUserDetailsService.loadUserByUsername(email);

            //validating token
            if (jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
                );
                //putting details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //putting token in context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
