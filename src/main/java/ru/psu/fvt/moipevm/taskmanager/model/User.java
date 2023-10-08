package ru.psu.fvt.moipevm.taskmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(min = 6, max = 15, message = "Логин должен быть не менее 6 и не более 15 символов")
    @Column(name = "login")
    private String login;
    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    @Column(name = "password")
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Column(name = "email")
    private String email;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "last_activity_date")
    private LocalDateTime lastActivityDate;

    @Column(name = "locked")
    private boolean isLocked;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(isLocked);
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
