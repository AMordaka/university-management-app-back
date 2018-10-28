package mordaka.arkadiusz.application.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfInfo {

    private Long id;
    private String fileName;
    private String index;

    public PdfInfo(Long id, String fileName, String index) {
        this.id = id;
        this.fileName = fileName;
        this.index = index;
    }
}
