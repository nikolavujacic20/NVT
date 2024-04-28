package ftn.ntp.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DriverUser extends RegularUser {
    private String licence;
    private String status;
}
