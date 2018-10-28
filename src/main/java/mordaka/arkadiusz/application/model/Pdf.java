package mordaka.arkadiusz.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Pdf {

    @GeneratedValue
    @Id
    private Long pdfId;

    @JsonIgnore
    @NotNull
    @ManyToOne
    private User user;
    private String fileName;

    @Lob
    private byte[] content;
}
