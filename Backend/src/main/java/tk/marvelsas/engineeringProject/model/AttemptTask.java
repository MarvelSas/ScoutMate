package tk.marvelsas.engineeringProject.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "AttemptTask")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AttemptTask {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String description;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "attempt")
    private Attempt attempt;

}
