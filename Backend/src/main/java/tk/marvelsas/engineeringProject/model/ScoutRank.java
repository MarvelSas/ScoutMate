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
@Table(name = "ScoutRank")
@AllArgsConstructor
@NoArgsConstructor
public class ScoutRank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name can't be empty")
    private String name;

    private String imageURL;

    private int level;

    private String statutePDFURL;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "scoutRankId")
    private Collection<AppUser> appUsers;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "scoutRankId")
    private Collection<Attempt> attempts;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "scoutRankId")
    private Collection<ActionQueueLine> actionQueueLines;





}
