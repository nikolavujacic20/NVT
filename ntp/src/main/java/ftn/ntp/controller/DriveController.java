package ftn.ntp.controller;

import ftn.ntp.model.Drive;
import ftn.ntp.services.DriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
public class DriveController {

    @Autowired
    private DriveService driveService;

    // Fetch all drives
    @GetMapping
    public ResponseEntity<List<Drive>> getAllDrives() {
        List<Drive> drives = driveService.findAll();
        return ResponseEntity.ok(drives);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Drive> getDriveById(@PathVariable Long id) {
        Drive drive = driveService.findById(id);
        return drive != null ? ResponseEntity.ok(drive) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<Drive> createDrive(@RequestBody Drive drive) {
        Drive newDrive = driveService.save(drive);
        return ResponseEntity.ok(newDrive);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Drive> updateDrive(@PathVariable Long id, @RequestBody Drive drive) {
        Drive updatedDrive = driveService.update(id, drive);
        return updatedDrive != null ? ResponseEntity.ok(updatedDrive) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDrive(@PathVariable Long id) {
        boolean deleted = driveService.delete(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // New endpoint to fetch all drives associated with a user as driver or passenger
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Drive>> getDrivesByUserId(@PathVariable Long userId) {
        List<Drive> drives = driveService.findDrivesByUserId(userId);
        return drives != null && !drives.isEmpty() ? ResponseEntity.ok(drives) : ResponseEntity.notFound().build();
    }
}