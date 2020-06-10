package kz.teamInclusion.Inclusion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private Users users;

    @Column(name = "question_title")
    private String question_title;

    @Column(name = "question_description")
    private String question_description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_qacategory")
    private QACategory categories;

    @Column(name = "created_at")
    private Date created_at;

}
