package ftn.ntp.util;

import ftn.ntp.model.RegularUser;
import ftn.ntp.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

public class CustomUserDetails extends User {

    private long userId;
    private String city;
    private String phone;

    private String firstName;
    private String lastName;

    private String birthday;

    private Role role;
    public CustomUserDetails(RegularUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.city = user.getCity();
        this.phone = user.getPhone();
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public long getUserId() {
        return userId;
    }


}