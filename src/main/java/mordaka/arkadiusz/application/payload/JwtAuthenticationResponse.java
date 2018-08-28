package mordaka.arkadiusz.application.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String name;
    private String surname;

    public JwtAuthenticationResponse(String accessToken, String name, String surname) {
        this.accessToken = accessToken;
        this.name = name;
        this.surname = surname;
    }
}