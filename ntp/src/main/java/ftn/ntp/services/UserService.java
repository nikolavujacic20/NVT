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
                        user.setPhone(userDetails.getPhone());
                        user.setCity(userDetails.getCity());
                        user.setFirstName(userDetails.getFirstName());
                        user.setLastName(userDetails.getLastName());


                        return userRepository.save(user);
                    }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
        }

        public void updateUserImage(Long userId, String imageUrl) {
            RegularUser user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setImageUrl(imageUrl);
            userRepository.save(user);
        }

    }
