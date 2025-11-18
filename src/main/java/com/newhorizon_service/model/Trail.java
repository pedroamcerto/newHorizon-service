package com.newhorizon_service.model;

import com.newhorizon_service.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Trail extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 255, name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "trail_course",
            joinColumns = @JoinColumn(name = "trail_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @ManyToMany(mappedBy = "trails")
    private List<User> users;

    @ManyToMany
    @JoinTable(
            name = "trail_competence",
            joinColumns = @JoinColumn(name = "trail_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences;

    @ManyToMany
    @JoinTable(
            name = "trail_position",
            joinColumns = @JoinColumn(name = "trail_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private List<Position> positions;


}
