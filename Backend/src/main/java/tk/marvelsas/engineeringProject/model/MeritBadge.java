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
@Table(name = "MeritBadge")
@AllArgsConstructor
@NoArgsConstructor
public class MeritBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name can't be empty")
    private String name;

    private String imageURL;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "categoryMeritBadge")
    private CategoryMeritBadge categoryMeritBadge;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="user_meritBadges",
            joinColumns=@JoinColumn(name="merit_badge_id"),
            inverseJoinColumns=@JoinColumn(name="app_user_id")
    )
    private Collection<AppUser> scout;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "meritBadgeId")
    private Collection<Attempt> attempts;



    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "meritBadgeId")
    private Collection<ActionQueueLine> actionQueueLines;



}
