package tk.marvelsas.engineeringProject.model.DTO;


import lombok.*;
import tk.marvelsas.engineeringProject.ENUMS.ACTION_TYPE;
import tk.marvelsas.engineeringProject.model.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ActiveQueueLineDTO {

    private Integer id;
    private ACTION_TYPE action_type;
    private boolean archieved;
    private AppUserDetailsDTO appUserDetailsDTO;
    private Organization organization;
    private MeritBadge meritBadge;
    private ScoutRank scoutRank;
    private ScoutInstructorRank scoutInstructorRank;
    private Role role;
    private Organization subOrganization;


}
