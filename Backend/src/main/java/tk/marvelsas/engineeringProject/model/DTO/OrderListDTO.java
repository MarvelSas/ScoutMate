package tk.marvelsas.engineeringProject.model.DTO;

import lombok.*;
import tk.marvelsas.engineeringProject.ENUMS.ORDER_TYPE;

import java.time.LocalDate;




@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class OrderListDTO {

    private Integer id;
    private ORDER_TYPE order_type;
    private Integer numberOrder;
    private LocalDate date;
    private String name;
    private String surname;
}
