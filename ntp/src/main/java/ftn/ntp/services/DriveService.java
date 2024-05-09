package ftn.ntp.services;

import ftn.ntp.model.Drive;
import ftn.ntp.repositories.DriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriveService {

    @Autowired
    private DriveRepository driveRepository;

    public List<Drive> findAll() {
        return driveRepository.findAll();
    }

    public Drive findById(Long id) {
        return driveRepository.findById(id).orElse(null);
    }

    public Drive save(Drive drive) {
        return driveRepository.save(drive);
    }

    public Drive update(Long id, Drive drive) {
        return driveRepository.findById(id)
                .map(existingDrive -> {
                    existingDrive.setOrigin(drive.getOrigin());
                    existingDrive.setDestination(drive.getDestination());
                    existingDrive.setStatus(drive.getStatus());
                    existingDrive.setStartTime(drive.getStartTime());
                    existingDrive.setEndTime(drive.getEndTime());
                    existingDrive.setEstimatedCost(drive.getEstimatedCost());
                    existingDrive.setActualCost(drive.getActualCost());
                    existingDrive.setDriver(drive.getDriver());
                    existingDrive.setPassenger(drive.getPassenger());
                    return driveRepository.save(existingDrive);
                }).orElse(null);
    }

    public boolean delete(Long id) {
        if (driveRepository.existsById(id)) {
            driveRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<Drive> findDrivesByUserId(Long userId) {
        return driveRepository.findDrivesByDriverIdOrPassengerId(userId);
    }
}