package tk.marvelsas.engineeringProject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "AppUsers")
@EqualsAndHashCode
@NoArgsConstructor
@Slf4j
public class AppUser implements UserDetails {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "email can't be empty")
    @Column(unique = true)
    @Email
    private String email;
    @NotEmpty(message = "password can't be empty")
    private String password;
    @NotEmpty(message = "password can't be empty")
    private String name;
    @NotEmpty(message = "password can't be empty")
    private String surname;
    @NotNull(message = "birthday can't be blank")
    private LocalDate birthday ;
//    @Column(columnDefinition = "boolean default false")
//    private boolean authorized;
//    @Column(columnDefinition = "boolean default true")
//    private boolean active;
    private String nickName;//Pseudonim


    @OneToMany(mappedBy = "appUserRole",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Role> roles;
    private Boolean locked=false;
    private Boolean enabled=false;


    @ManyToMany
    @JoinTable(name="owner_organization",
            joinColumns=@JoinColumn(name="app_user_id"),
            inverseJoinColumns=@JoinColumn(name="organization_id")
    )
    private Collection<Organization> ownedOrganization;

    @ManyToMany
    @JoinTable(name="user_meritBadges",
            joinColumns=@JoinColumn(name="app_user_id"),
            inverseJoinColumns=@JoinColumn(name="merit_badge_id")
    )
    private Collection<MeritBadge> meritBadges;



    @ManyToOne()
    @JoinColumn(name = "scoutRankId")
    private ScoutRank scoutRankId;

    @ManyToOne()
    @JoinColumn(name = "scoutInstructorRankId")
    private ScoutInstructorRank scoutInstructorRankId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "creatorId")
    private Collection<Attempt> attemptCreate;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "applicantId")
    private Collection<Attempt> attemptApply;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "appUserId")
    private Collection<ActionQueueLine> actionQueueLines;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "appUserId")
    private Collection<ScoutOrder> orders;
    private String photo;





    public AppUser(@NotEmpty(message = "email can't be empty") String email,
                   @NotEmpty(message = "password can't be empty") String password,
                   @NotEmpty(message = "password can't be empty") String name,
                   @NotEmpty(message = "password can't be empty") String surname,
                   @NotNull(message = "birthday can't be blank") LocalDate birthday,
                   String nickName,
                   Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.nickName = nickName;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
            return authorities ;
    }




    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
