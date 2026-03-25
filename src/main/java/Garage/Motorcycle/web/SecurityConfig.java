package Garage.Motorcycle.web;

import Garage.Motorcycle.services.JwtService;
import Garage.Motorcycle.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//security class for encoding passwords
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //creating jwt filter in security config
    @Bean
    public JwtFilter jwtFilter(JwtService jwtService, MyUserDetailsService myUserDetailsService){
        return new JwtFilter(jwtService, myUserDetailsService);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) //disabling csrf
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()//allowing all auth endpoints without authentication
                        .requestMatchers("/admin/**").hasRole("ADMIN")//allowing admins for post requests
                        .anyRequest().authenticated() //everything else will be with token access

                ).sessionManagement(session->session //allowing spring to know that we are using jwt
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
