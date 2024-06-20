package online.ondemandtutor.be.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter

public class Category {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //các cấp học 1 2 3
    private int subjectLevel;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;

    //CRUD - create, read, update, delete

    @OneToMany(mappedBy = "category")
    List<Subject> subject;

    private boolean isDeleted = false;
}
