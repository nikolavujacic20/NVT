package ftn.ntp.controller;

import ftn.ntp.dto.RegistrationRequest;
import ftn.ntp.model.DriverUser;
import ftn.ntp.model.RegularUser;
import ftn.ntp.model.Role;
import ftn.ntp.util.CustomUserDetails;
import ftn.ntp.util.JwtUtil;
import ftn.ntp.services.CustomUserDetailsService;
import ftn.ntp.dto.AuthenticationRequest;
import ftn.ntp.dto.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            System.out.println(authenticationRequest.getPassword()+"OVO JE NUL");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())

            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());


        Map<String, Object> additionalClaims = new HashMap<>();
        System.out.println(userDetails.getUserId()+"USER ID JE NULL");
        additionalClaims.put("userId", userDetails.getUserId());
        additionalClaims.put("city", userDetails.getCity());
        additionalClaims.put("phone", userDetails.getPhone());
        additionalClaims.put("firstName", userDetails.getFirstName());
        additionalClaims.put("lastName", userDetails.getLastName());


        final String jwt = jwtTokenUtil.generateToken(userDetails, additionalClaims);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        if (userDetailsService.emailExists(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        RegularUser user = new RegularUser();
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setRole(registrationRequest.getRole());
        user.setCity(registrationRequest.getCity());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setPhone(registrationRequest.getPhone());



        userDetailsService.saveUser(user);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(user.getEmail());

        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("userId", user.getUserId());
        additionalClaims.put("city", user.getCity());
        additionalClaims.put("phone", user.getPhone());
        additionalClaims.put("firstName", user.getFirstName());
        additionalClaims.put("lastName", user.getLastName());

        final String jwt = jwtTokenUtil.generateToken(userDetails, additionalClaims);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @PostMapping("/api/auth/registerDriver")
    public ResponseEntity<?> registerDriver(@RequestBody RegistrationRequest registrationRequest) {
        if (userDetailsService.emailExists(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        DriverUser driver = new DriverUser();
        driver.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        driver.setEmail(registrationRequest.getEmail());
        driver.setRole(Role.valueOf("DRIVER"));
        driver.setCity(registrationRequest.getCity());
        driver.setFirstName(registrationRequest.getFirstName());
        driver.setLastName(registrationRequest.getLastName());
        driver.setPhone(registrationRequest.getPhone());
        driver.setCar(registrationRequest.getCar());


        userDetailsService.saveUser(driver);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(driver.getEmail());

        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("userId", driver.getUserId());
        additionalClaims.put("city", driver.getCity());
        additionalClaims.put("phone", driver.getPhone());
        additionalClaims.put("firstName", driver.getFirstName());
        additionalClaims.put("lastName", driver.getLastName());
        additionalClaims.put("car", driver.getLastName());
        additionalClaims.put("status", driver.getLastName());

        final String jwt = jwtTokenUtil.generateToken(userDetails, additionalClaims);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
