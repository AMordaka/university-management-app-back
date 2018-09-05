package mordaka.arkadiusz.application.payload;

import lombok.Getter;
import lombok.Setter;
import mordaka.arkadiusz.application.model.Role;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class UserProfile {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Instant createdAt;
    private Instant updatedAt;
    private String street;
    private String numberStreet;
    private String postalCode;
    private String city;
    private Set<Role> roles;

    public UserProfile(Long id, String username, String name, String surname, String email, String password, Instant createdAt, Instant updatedAt, String street, String numberStreet, String postalCode, String city, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.street = street;
        this.numberStreet = numberStreet;
        this.postalCode = postalCode;
        this.city = city;
        this.roles = roles;
    }
}
