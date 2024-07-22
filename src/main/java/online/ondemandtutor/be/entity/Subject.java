package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Subject {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    Category category;

    @ManyToMany
    @JoinTable
            (
                    joinColumns = @JoinColumn(name = "subject_id"),
                    inverseJoinColumns = @JoinColumn(name = "account_id")
            )
    @JsonIgnore
    List<Account> account;

    private String name;
}
