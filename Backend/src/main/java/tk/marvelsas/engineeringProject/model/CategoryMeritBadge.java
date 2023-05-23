package tk.marvelsas.engineeringProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@Entity
@Table(name = "categoryMeritBadge")
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMeritBadge {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "password can't be empty")
    private String name;


    private String imageURL;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "categoryMeritBadge")
    private Collection<MeritBadge> meritBadge;






}
