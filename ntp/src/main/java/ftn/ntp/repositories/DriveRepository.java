package ftn.ntp.repositories;

import ftn.ntp.model.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {

    @Query("SELECT d FROM Drive d WHERE (d.driver.userId = :userId OR d.passenger.userId = :userId) AND d.status='FINISHED'")
    List<Drive> findDrivesByDriverIdOrPassengerId(@Param("userId") Long userId);
}