package mordaka.arkadiusz.application.model;

import lombok.Getter;
import lombok.Setter;
import mordaka.arkadiusz.application.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Item extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(unique = true)
    private String subjectName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private Set<ItemStudent> student = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private Item() {
    }

    public Item(@NotBlank @Size(max = 40) String subjectName, Teacher teacher) {
        this.subjectName = subjectName;
        this.teacher = teacher;
    }

    public void addItemStudent(ItemStudent itemStudent){
        student.add(itemStudent);
    }
}
