package ftn.ntp.services;

import ftn.ntp.model.RegularUser;
import ftn.ntp.repositories.RegularUserRepository;
import ftn.ntp.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RegularUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RegularUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Create CustomUserDetails with additional information like city, phone, etc.
        return new CustomUserDetails(user, Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

    public void saveUser(RegularUser user) {
        userRepository.save(user);
    }

    public Optional<RegularUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<RegularUser> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
