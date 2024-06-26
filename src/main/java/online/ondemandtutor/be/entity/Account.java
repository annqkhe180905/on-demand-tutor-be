package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;
import online.ondemandtutor.be.enums.RequestStatus;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isDeleted = false;

    @Column(unique = true)
    private String email; // login bang email

    private String fullname;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    StatusEnum requestStatus;

    @Enumerated(EnumType.STRING)
    RoleEnum role;

    @Enumerated(EnumType.STRING)
    RequestStatus subjectRegistrationStatus;

    //setAvatar cho user, up certificate và video cho tutor

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<TutorCertificate> tutorCertificates = new ArrayList<>();


    @OneToMany(mappedBy = "account")
    List<TutorVideo> tutorVideos;


    @OneToMany(mappedBy = "account")
    List<Complaint> complaints;

    @OneToMany(mappedBy = "account")
    List<Review> reviews;

    @ManyToMany(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<ScheduleRecord> scheduleRecords;

    //test case
    public Account(String email, String password, String fullname,String phone, RoleEnum role, boolean isDeleted) {
    }

    //////////

    @ManyToMany(mappedBy = "account")
    List<Subject> subjects;

    @ManyToMany(mappedBy = "account")
    List<Location> locations;

    @ManyToMany(mappedBy = "account")
    List<Grade> grades;

    @ManyToOne
    @JoinColumn(name = "education_level_id")
    EducationLevel educationLevel;

    private String brief;

    //////////

    @ManyToMany(mappedBy = "tutors")
    private List<Booking> tutorBookings;

    @ManyToMany(mappedBy = "students")
    private List<Booking> studentBookings;

    //////////

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
