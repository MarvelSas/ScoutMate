package tk.marvelsas.engineeringProject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_STATUS;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity
@Table(name = "Attempt")
@EqualsAndHashCode

@NoArgsConstructor
@Slf4j
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String name;

    @NotNull
    private ATTEMPT_STATUS STATUS;
    @NotNull
    private ATTEMPT_TYPE TYPE;

    @NotNull
    private boolean archived;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "attempt")
    private Collection<AttemptTask> attemptTasks;


    //creator
    //applicant

    @ManyToOne()
    @JoinColumn(name = "creatorId")
    @NotNull
    private AppUser creatorId;

    @ManyToOne()
    @JoinColumn(name = "applicantId")
    @NotNull
    private AppUser applicantId;

    @ManyToOne()
    @JoinColumn(name = "organizationId")
    @NotNull
    private Organization organization;


    @ManyToOne()
    @JoinColumn(name = "scoutRankId")
    private  ScoutRank scoutRankId;

    @ManyToOne()
    @JoinColumn(name = "scoutInstructorRankId")
    private  ScoutInstructorRank scoutInstructorRankId;

    @ManyToOne()
    @JoinColumn(name = "meritBadgeId")
    private MeritBadge meritBadgeId;

    @OneToOne(mappedBy = "attempt")
    @JsonIgnore
    ActionQueueLine actionQueueLine;





    public Attempt(Integer id, String name,AppUser creatorId,AppUser applicantId, Collection<AttemptTask> attemptTasks, ScoutRank scoutRankId) {
        this.id = id;
        this.name = name;
        this.creatorId=creatorId;
        this.applicantId=applicantId;
        this.attemptTasks = attemptTasks;
        this.scoutRankId = scoutRankId;
        this.STATUS = ATTEMPT_STATUS.OPEN;
        this.TYPE = ATTEMPT_TYPE.SCOUT_RANK;
        this.archived = false;
    }

    public Attempt(Integer id, String name,AppUser creatorId,AppUser applicantId, Collection<AttemptTask> attemptTasks, ScoutInstructorRank scoutInstructorRankId) {
        this.id = id;
        this.name = name;
        this.creatorId=creatorId;
        this.applicantId=applicantId;
        this.attemptTasks = attemptTasks;
        this.scoutInstructorRankId = scoutInstructorRankId;
        this.STATUS = ATTEMPT_STATUS.OPEN;
        this.TYPE = ATTEMPT_TYPE.SCOUT_INSTRUCTOR_RANK;
        this.archived = false;
    }

    public Attempt(Integer id, String name,AppUser creatorId,AppUser applicantId, Collection<AttemptTask> attemptTasks, MeritBadge meritBadgeId) {
        this.id = id;
        this.name = name;
        this.creatorId=creatorId;
        this.applicantId=applicantId;
        this.attemptTasks = attemptTasks;
        this.meritBadgeId = meritBadgeId;
        this.STATUS = ATTEMPT_STATUS.OPEN;
        this.TYPE = ATTEMPT_TYPE.MERITBADGE;
        this.archived = false;
    }
}
