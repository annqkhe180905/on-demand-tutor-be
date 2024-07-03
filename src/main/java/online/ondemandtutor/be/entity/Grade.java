package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private long id;

    private String grade;

    @ManyToMany
    @JoinTable
            (
                    joinColumns = @JoinColumn(name = "grade_id"),
                    inverseJoinColumns = @JoinColumn(name = "account_id")
            )
    @JsonIgnore
    List<Account> account;
}
