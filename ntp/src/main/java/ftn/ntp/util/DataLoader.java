package ftn.ntp.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import ftn.ntp.model.RegularUser;
import ftn.ntp.model.Role;
import ftn.ntp.model.Status;
import ftn.ntp.repositories.RegularUserRepository;

import ftn.ntp.services.CustomUserDetailsService;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RegularUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        RegularUser admin = userRepository.findByEmail("admin@admin.com")
                .orElse(new RegularUser()); // Retrieve or create new instance

        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);
        admin.setCity("Novi Sad");
        admin.setPhone("0643996648");
        admin.setFirstName("Nikola");
        admin.setLastName("Vujacic");
        admin.setStatus(Status.ACTIVE);
        userRepository.save(admin); // Save or update

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(admin.getEmail());
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("userId", admin.getUserId());
        additionalClaims.put("city", admin.getCity());
        additionalClaims.put("phone", admin.getPhone());
        additionalClaims.put("firstName", admin.getFirstName());
        additionalClaims.put("lastName", admin.getLastName());

        final String jwt = jwtTokenUtil.generateToken(userDetails, additionalClaims);
        System.out.println("Admin JWT: " + jwt);  // Log the token
    }
}
