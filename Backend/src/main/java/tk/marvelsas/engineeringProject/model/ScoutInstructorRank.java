package tk.marvelsas.engineeringProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;


@Data
@Entity
@Table(name = "ScoutInstructorRank")
@AllArgsConstructor
@NoArgsConstructor
public class ScoutInstructorRank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name can't be empty")
    private String name;

    private int level;

    private String color;

    private String statutePDFURL;

    private String shortcut;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "scoutInstructorRankId")
    private Collection<AppUser> appUsers;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "scoutInstructorRankId")
    private Collection<Attempt> attempts;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "scoutInstructorRankId")
    private Collection<ActionQueueLine> actionQueueLines;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "previousScoutInstructorRankId")
    private Collection<ActionQueueLine> previousaActionQueueLines;


}
