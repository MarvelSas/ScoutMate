package tk.marvelsas.engineeringProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tk.marvelsas.engineeringProject.ENUMS.ORDER_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;


@Data
@Entity
@Table(name = "ScoutOrders")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ScoutOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String place;



    private String quote;//Cytat

    @Column(columnDefinition = "DATE")
    private LocalDate date;
    private String exceptions;
    private String others;
    @NotNull
    private ORDER_TYPE order_type;

    private Integer number;






    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "appUserId")
    private AppUser appUserCreator;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "organizationId")
    private Organization organization;



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id",name = "orderId")
    private Collection<ActionQueueLine> actionQueueLines;

    private String customTextScoutRankAndMeritBadge;
    private String customTextOrganization;
    private String customTextRole;



    public ScoutOrder(String place, String quote, String exceptions, String others, ORDER_TYPE order_type, Collection<ActionQueueLine> actionQueueLines, String customTextScoutRankAndMeritBadge, String customTextOrganization, String customTextRole) {
        this.id = null;
        this.place = place;
        this.quote = quote;
        this.date = LocalDate.now();
        this.exceptions = exceptions;
        this.others = others;
        this.order_type = order_type;
        this.number = null;
        this.appUserCreator = null;
        this.organization = null;
        this.actionQueueLines = actionQueueLines;
        this.customTextScoutRankAndMeritBadge = customTextScoutRankAndMeritBadge;
        this.customTextOrganization = customTextOrganization;
        this.customTextRole = customTextRole;
    }

    public ScoutOrder(ScoutOrder order) {
        this.id = null;
        this.place = order.getPlace();
        this.quote = order.getQuote();
        this.date = LocalDate.now();
        this.exceptions = order.getExceptions();
        this.others = order.getOthers();
        this.order_type = order.getOrder_type();
        this.number = order.getNumber();
        this.appUserCreator = order.getAppUserCreator();
        this.organization = order.getOrganization();
        this.actionQueueLines = order.getActionQueueLines();
        this.customTextScoutRankAndMeritBadge =order.getCustomTextScoutRankAndMeritBadge();
        this.customTextOrganization =order.getCustomTextOrganization();
        this.customTextRole =order.getCustomTextRole();


    }
}
