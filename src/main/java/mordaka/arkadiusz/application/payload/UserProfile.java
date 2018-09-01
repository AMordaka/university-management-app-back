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
    private Instant joinedAt;
    private Set<Role> roles;

    public UserProfile(Long id, String username, String name, String surname, String email, Instant joinedAt, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.joinedAt = joinedAt;
        this.roles = roles;
    }
}
