package mordaka.arkadiusz.application.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemProfile {

    private Long id;
    private String subjectName;
    private String grade;
    private String name;
    private String username;

    public ItemProfile(Long id, String subjectName, String grade, String name, String username) {
        this.id = id;
        this.subjectName = subjectName;
        this.grade = grade;
        this.name = name;
        this.username = username;
    }
}
