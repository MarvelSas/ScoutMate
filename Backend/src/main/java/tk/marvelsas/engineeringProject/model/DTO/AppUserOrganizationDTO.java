package tk.marvelsas.engineeringProject.model.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class AppUserOrganizationDTO {

    private String name;
    private String surname;
    private String ScoutRankName;
    private String email;

    private String organizationFunction;
    private String nickName;//Pseudonim


}
