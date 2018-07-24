package mordaka.arkadiusz.application.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


public class UserProfile {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Instant joinedAt;

    public UserProfile(Long id, String username, String name, Instant joinedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
    }
}
