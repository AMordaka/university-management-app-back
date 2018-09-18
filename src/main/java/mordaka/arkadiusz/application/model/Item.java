package mordaka.arkadiusz.application.model;

import lombok.Getter;
import lombok.Setter;
import mordaka.arkadiusz.application.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Item extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String subjectName;

    @Size(max = 3)
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private Item() {
    }

    public Item(@NotBlank @Size(max = 40) String subjectName, Teacher teacher) {
        this.subjectName = subjectName;
        this.teacher = teacher;
    }
}
