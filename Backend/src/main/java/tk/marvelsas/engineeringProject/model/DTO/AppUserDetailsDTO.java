package tk.marvelsas.engineeringProject.model.DTO;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
//Class only needed for return information
public class AppUserDetailsDTO {


    private String email;
    private String name;
    private String surname;


}
