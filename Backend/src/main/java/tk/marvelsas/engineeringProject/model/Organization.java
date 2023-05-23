package tk.marvelsas.engineeringProject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.marvelsas.engineeringProject.ENUMS.ORGANIZATION_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;





@Data
@Entity
@Table(name = "Organization")
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "Organization name can't be empty")
    private String name;
    //@NotEmpty(message = "ORGANIZATION TYP can't be empty")
    private ORGANIZATION_TYPE organizationType;
    @OneToMany(cascade = CascadeType.ALL)

    @JsonIgnore
    @JoinColumn(name = "mainoganization")
    private Collection<Organization> subOrganizations = new ArrayList<Organization>();


    @JsonIgnore
    @ManyToMany
    @JoinTable(name="owner_organization",
            joinColumns=@JoinColumn(name="organization_id"),
            inverseJoinColumns=@JoinColumn(name="app_user_id")
    )
    private Collection<AppUser> owners;

    @JsonIgnore
    @OneToMany(mappedBy = "organizationRole",cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Role> roles;



    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "organizationId")
    private Collection<Attempt> attemptList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "organizationId")
    private Collection<ActionQueueLine> actionQueueLines;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "organizationId")
    private Collection<ScoutOrder> orders;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "subOrganizationId")
    private Collection<ScoutOrder> subOrders;




    private String image;




}

