package ftn.ntp.dto;

import ftn.ntp.model.Role;
import lombok.Getter;

@Getter
public class RegistrationRequest {

        private String password;
        private String email;
        private Role role;


    public void setPassword(String password) {
            this.password = password;
        }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
            this.email = email;
        }

        // Getters and setters
    }

