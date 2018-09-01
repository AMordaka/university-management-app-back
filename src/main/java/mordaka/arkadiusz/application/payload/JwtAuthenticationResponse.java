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
    private Set<Role> roles;

    public JwtAuthenticationResponse(String accessToken, String name, String surname, Set<Role> roles ) {
        this.accessToken = accessToken;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }
}