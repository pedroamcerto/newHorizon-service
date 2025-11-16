package com.newhorizon_service.model;

import com.newhorizon_service.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 255, name = "name")
    private String name;

    @Column(length = 320, name = "email")
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_competence",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences;

    @ManyToMany
    @JoinTable(
            name = "user_trail",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "trail_id")
    )
    private List<Trail> trails;
}
