package com.newhorizon_service.model;

import com.newhorizon_service.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Position extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 255, name = "name")
    private String name;

    @ManyToMany(mappedBy = "positions")
    private List<Trail> trails;

    @ManyToMany
    @JoinTable(
            name = "position_competence",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences;
}
