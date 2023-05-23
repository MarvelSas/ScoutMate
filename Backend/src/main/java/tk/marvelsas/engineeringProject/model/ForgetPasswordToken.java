package tk.marvelsas.engineeringProject.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class ForgetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime resetPasswordAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_users_id"
    )
    private AppUser appUser;

    public ForgetPasswordToken(String token, LocalDateTime createAt, LocalDateTime expiredAt, AppUser appUser) {
        this.token = token;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.appUser = appUser;
    }
}
