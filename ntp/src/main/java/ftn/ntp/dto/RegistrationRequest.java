package ftn.ntp.dto;

import ftn.ntp.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

        private String password;
        private String email;
        private Role role;
        private String city;
        private String phone;
        private String firstName;
        private String lastName;
        private String car;
        private String status;



    }

