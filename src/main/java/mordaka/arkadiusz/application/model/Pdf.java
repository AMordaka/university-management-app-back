package mordaka.arkadiusz.application.model;

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

    @NotNull
    @ManyToOne
    private User user;
    private String fileName;

    @Lob
    private byte[] content;
}
