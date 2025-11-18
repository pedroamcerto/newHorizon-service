package com.newhorizon_service.model;

import com.newhorizon_service.common.Auditable;
import com.newhorizon_service.model.enums.CompetenceType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Competence extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 255, name = "name")
    private String name;

    @Column(length = 4, name = "type")
    @Enumerated(EnumType.STRING)
    private CompetenceType type;

    @ManyToMany(mappedBy = "competences")
    private List<User> users;

    @ManyToMany(mappedBy = "competences")
    private List<Trail> trails;

    @ManyToMany(mappedBy = "competences")
    private List<Position> positions;
}
