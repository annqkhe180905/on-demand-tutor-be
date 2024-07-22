package online.ondemandtutor.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import online.ondemandtutor.be.enums.MonthlyPackageEnum;
import online.ondemandtutor.be.enums.RequestStatus;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import online.ondemandtutor.be.service.DateService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    MonthlyPackageEnum monthlyPackage = MonthlyPackageEnum.DEACTIVATED;

    ////////////////

    @Column(length = 1000)
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<TutorCertificate> tutorCertificates = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @Column(length = 1000)
    List<TutorVideo> tutorVideos;

    ////////////////
    @OneToMany(mappedBy = "fromStudent")
    List<Complaint> complaintSender;

    @ManyToMany(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<ScheduleRecord> scheduleRecords;

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

    @Column(length = 500)
    private String brief;

    //////////

    @OneToMany(mappedBy = "tutor")
    private List<Booking> tutorBookings;

    @OneToMany(mappedBy = "student")
    private List<Booking> studentBookings;

    @OneToMany(mappedBy = "tutor")
    private List<Review> tutorGotReview;

    @OneToMany(mappedBy = "student")
    private List<Review> studentReview;

    //////////

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private Wallet wallet;

    //////////

    //monthly package
    private LocalDateTime nextPaymentDate;
//    @PrePersist
//    protected void onCreate() {
//        nextPaymentDate = DateService.getCurrentLocalDateTime();
//    }

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
