package mordaka.arkadiusz.application.payload;

import lombok.Getter;
import lombok.Setter;
import mordaka.arkadiusz.application.model.Role;

import java.util.Set;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String name;
    private String surname;
    private String username;
    private Set<Role> roles;

    public JwtAuthenticationResponse(String accessToken, String name, String surname, Set<Role> roles, String username) {
        this.accessToken = accessToken;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
        this.username = username;
    }
}