package online.ondemandtutor.be.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SearchTutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String subject;
    private String location;
    private String bio;

}
