package online.ondemandtutor.be.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Category {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String subject;
    Integer number;

    boolean isDeleted = false;
}
