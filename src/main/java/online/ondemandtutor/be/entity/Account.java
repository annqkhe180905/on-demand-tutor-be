package online.ondemandtutor.be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.ondemandtutor.be.enums.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter

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
    RoleEnum role;

    @OneToMany(mappedBy = "account")
    List<Certificate> certificates;

    @OneToMany(mappedBy = "account")
    List<TutorVideo> tutorVideos;

    @OneToMany(mappedBy = "account")
    List<Complaint> complaints;

    @OneToMany(mappedBy = "account")
    List<Review> reviews;

    @OneToMany(mappedBy = "category")
    private List<Subject> subjects;

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
