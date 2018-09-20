package mordaka.arkadiusz.application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ItemStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemStudentId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "grade")
    private String grade;

}
