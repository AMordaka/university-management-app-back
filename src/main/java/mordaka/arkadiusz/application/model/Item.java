package mordaka.arkadiusz.application.model;

import lombok.Data;
import mordaka.arkadiusz.application.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Data
@Table(name = "Items")
public class Item extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String subjectName;

    @Size(max = 3)
    private String grade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    private Item() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(subjectName, item.subjectName) &&
                Objects.equals(grade, item.grade);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, subjectName, grade);
    }
}
