package mordaka.arkadiusz.application.payload;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 3, max = 40)
    private String surname;

    @NotBlank
    @Pattern(regexp = "^(\\d{4,10})")
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    private String street;

    @NotBlank
    private String numberStreet;

    @NotBlank
    @Pattern(regexp = "^(\\d{2}-\\d{3})")
    private String postalCode;

    @NotBlank
    private String city;
}
