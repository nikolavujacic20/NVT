package ftn.ntp.controller;
import ftn.ntp.model.RegularUser;
import ftn.ntp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


    @RestController
    @RequestMapping("/api/users")
    public class UserController {

        @GetMapping
        public ResponseEntity<List<RegularUser>> getAllUsers() {
            List<RegularUser> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }

        private final UserService userService;

        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/{id}")
        public ResponseEntity<RegularUser> getUserById(@PathVariable Long id) {
            Optional<RegularUser> user = userService.getUserById(id);
            return user.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        }
        @PutMapping("/{id}")
        public ResponseEntity<RegularUser> updateUser(@PathVariable Long id, @RequestBody RegularUser userDetails) {
            RegularUser updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        }


}
