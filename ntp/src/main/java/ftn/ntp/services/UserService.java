package ftn.ntp.services;
import ftn.ntp.model.RegularUser;
import ftn.ntp.repositories.RegularUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

    @Service
    public class UserService {


        private final RegularUserRepository userRepository;

        @Autowired
        public UserService(RegularUserRepository userRepository) {
            this.userRepository = userRepository;
        }


        public Optional<RegularUser> getUserById(Long id) {
            return userRepository.findById(id);
        }

        public List<RegularUser> getAllUsers() {
            return userRepository.findAll();
        }

        public void deleteUserById(Long id) {
            userRepository.deleteById(id);
        }

        public RegularUser updateUser(Long id, RegularUser userDetails) {
            return userRepository.findById(id)
                    .map(user -> {
                        user.setEmail(userDetails.getEmail());
                        user.setPassword(userDetails.getPassword());
                        // Update other fields as necessary
                        return userRepository.save(user);
                    }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        }
}
