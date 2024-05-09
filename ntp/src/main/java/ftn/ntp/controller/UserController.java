package ftn.ntp.controller;

import ftn.ntp.model.RegularUser;
import ftn.ntp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ResourceLoader resourceLoader;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public UserController(UserService userService, ResourceLoader resourceLoader) {
        this.userService = userService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public ResponseEntity<List<RegularUser>> getAllUsers() {
        List<RegularUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegularUser> getUserById(@PathVariable Long id) {
        Optional<RegularUser> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/upload-image")
    public ResponseEntity<?> uploadImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "File is empty"));
        }
        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadDirectory = Paths.get(uploadPath);
            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }
            Path filePath = uploadDirectory.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            userService.updateUserImage(userId, "/static/images/" + filename);
            return ResponseEntity.ok(Map.of("message", "Image uploaded successfully: /static/images/" + filename));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Could not upload the file: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegularUser> updateUser(@PathVariable Long id, @RequestBody RegularUser userDetails) {
        RegularUser updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
