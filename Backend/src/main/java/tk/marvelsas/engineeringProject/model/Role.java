package tk.marvelsas.engineeringProject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "Role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "password can't be empty")
    private String name;


    @ManyToOne()
    @JoinColumn(name = "appuser_id")
    @JsonIgnore
    private AppUser appUserRole;



    @ManyToOne
    @JoinColumn( name = "organization_id")
    private Organization organizationRole;






}
