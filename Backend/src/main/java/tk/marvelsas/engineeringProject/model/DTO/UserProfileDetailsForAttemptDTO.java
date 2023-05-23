package tk.marvelsas.engineeringProject.model.DTO;

import lombok.*;
import tk.marvelsas.engineeringProject.model.MeritBadge;
import tk.marvelsas.engineeringProject.model.Role;
import tk.marvelsas.engineeringProject.model.ScoutInstructorRank;
import tk.marvelsas.engineeringProject.model.ScoutRank;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserProfileDetailsForAttemptDTO  {


    @NotEmpty(message = "password can't be empty")
    private String email;
    @NotEmpty(message = "password can't be empty")
    private String name;
    @NotEmpty(message = "password can't be empty")
    private String surname;

    private ScoutRank scoutRank;
    private ScoutInstructorRank scoutInstructorRank;

}
