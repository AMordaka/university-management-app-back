package mordaka.arkadiusz.application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Avatar {

    @GeneratedValue
    @Id
    private Long avatarId;

    @NotNull
    @OneToOne
    private User user;

    private String fileName;
    @Lob
    private byte[] image;
}