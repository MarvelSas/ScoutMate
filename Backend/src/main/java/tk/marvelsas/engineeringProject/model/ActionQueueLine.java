package tk.marvelsas.engineeringProject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.marvelsas.engineeringProject.ENUMS.ACTION_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "ActionQueueLine")
@EqualsAndHashCode
@NoArgsConstructor
@Slf4j
public class ActionQueueLine {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private ACTION_TYPE TYPE;
    @NotNull
    private Boolean archived;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "appUserId")
    //Aplicant
    private AppUser appUser;

    @ManyToOne()
    @JoinColumn(name = "organizationId")
    private Organization organization;

    @ManyToOne()
    @JoinColumn(name = "meritBadgeId")
    private MeritBadge meritBadge;

    @ManyToOne()
    @JoinColumn(name = "scoutRankId")
    private ScoutRank scoutRank;

    @ManyToOne()
    @JoinColumn(name = "scoutInstructorRankId")
    private ScoutInstructorRank scoutInstructorRank;
    @ManyToOne()
    @JoinColumn(name = "previousScoutInstructorRankId")
    private ScoutInstructorRank previousScoutInstructorRank;
    @ManyToOne()
    @JoinColumn(name = "roleId")
    private Role role;
    @JsonIgnore
    private String roleName;

    @ManyToOne()
    @JoinColumn(name = "orderId")
    private ScoutOrder order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attempt_id", referencedColumnName = "id")
    @JsonIgnore
    private Attempt attempt;


    @ManyToOne()
    @JoinColumn(name = "subOrganizationId")
    private Organization subOrganization;




    public ActionQueueLine(AppUser appUser, Organization organization, MeritBadge meritBadge,Attempt attempt) {
        this.id = null;
        this.TYPE = ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS;
        this.archived = false;
        this.appUser = appUser;
        this.organization = organization;
        this.meritBadge = meritBadge;
        this.scoutRank = null;
        this.scoutInstructorRank = null;
        this.role = null;
        this.attempt=attempt;
        this.subOrganization=null;
    }
    public ActionQueueLine(AppUser appUser, Organization organization, ScoutRank scoutRank,Attempt attempt) {
        this.id = null;
        this.TYPE = ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS;
        this.archived = false;
        this.appUser = appUser;
        this.organization = organization;
        this.meritBadge = null;
        this.scoutRank = scoutRank;
        this.scoutInstructorRank = null;
        this.role = null;
        this.attempt=attempt;
        this.subOrganization=null;
    }
    public ActionQueueLine( AppUser appUser, Organization organization, ScoutInstructorRank scoutInstructorRank,Attempt attempt,ScoutInstructorRank previousScoutInstructorRank) {
        this.id = null;
        this.TYPE = ACTION_TYPE.GRANTING_MERITBADGE_AND_RANKS;
        this.archived = false;
        this.appUser = appUser;
        this.organization = organization;
        this.meritBadge = null;
        this.scoutRank = null;
        this.scoutInstructorRank = scoutInstructorRank;
        this.role = null;
        this.attempt=attempt;
        this.previousScoutInstructorRank=previousScoutInstructorRank;
        this.subOrganization=null;
    }
    public ActionQueueLine(Organization organization,Organization subOrganization) {
        this.id = null;
        this.TYPE = ACTION_TYPE.CREATE_ORGANIZATION;
        this.archived = false;
        this.appUser = null;
        this.organization = organization;
        this.meritBadge = null;
        this.scoutRank = null;
        this.scoutInstructorRank = null;
        this.role = null;
        this.attempt=null;
        this.subOrganization=subOrganization;
    }
    public ActionQueueLine(Organization organization,AppUser appUser, Role role ,ACTION_TYPE action_type) {
        this.id = null;
        this.TYPE = action_type;
        this.archived = false;
        this.appUser = appUser;
        this.organization = organization;
        this.meritBadge = null;
        this.scoutRank = null;
        this.scoutInstructorRank = null;
        this.role = role;
        this.attempt=null;
        this.roleName=role.getName();
        this.subOrganization=null;
    }




}
