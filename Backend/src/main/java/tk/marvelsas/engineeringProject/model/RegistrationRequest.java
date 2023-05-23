package tk.marvelsas.engineeringProject.model;


import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;




@Getter

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final LocalDate birthday;
    private final String nickName;

}
