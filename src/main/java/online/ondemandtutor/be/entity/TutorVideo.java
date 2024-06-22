package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class TutorVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String url;

    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_register_id", referencedColumnName = "id")
    SubjectRegister subjectRegister;

}
