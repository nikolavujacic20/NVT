package ftn.ntp.repositories;

import ftn.ntp.model.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
    Optional<RegularUser> findByEmail(String email);

}