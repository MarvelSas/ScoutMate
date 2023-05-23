package tk.marvelsas.engineeringProject.model.DTO;

import lombok.*;
import tk.marvelsas.engineeringProject.ENUMS.ORDER_TYPE;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class OrderInputDTO {

    private String place;
    private String quote;//Cytat
    private String exceptions;
    private String others;
    private ORDER_TYPE order_type;
    private List<ActiveQueueLineDTO> activeQueueLineDTOList;
    private String customTextScoutRankAndMeritBadge;
    private String customTextOrganization;
    private String customTextRole;




}
