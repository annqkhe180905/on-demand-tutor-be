package online.ondemandtutor.be.entity;

import jakarta.persistence.*;
import lombok.*;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    //setAvatar cho user, up certificate và video cho tutor

    @OneToMany(mappedBy = "account")
    List<TutorCertificate> tutorCertificates;

    @OneToMany(mappedBy = "account")
    List<TutorVideo> tutorVideos;

    @OneToMany(mappedBy = "account")
    List<Complaint> complaints;

    @OneToMany(mappedBy = "account")
    List<Review> reviews;

    @OneToMany(mappedBy = "account")
    List<TutorSchedule> tutorSchedules;

    //test
    public Account(String email, String password, String fullname,String phone, RoleEnum role, boolean isDeleted) {
    }

//    @OneToMany(mappedBy = "account")
//    List<Subject> subjects;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(this.role);
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
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
