package tk.marvelsas.engineeringProject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_users_id"
    )
    private AppUser appUser;
    


    public ConfirmationToken(String token, LocalDateTime createAt, LocalDateTime expiredAt,AppUser appUser) {
        this.token = token;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.appUser=appUser;

    }
}
