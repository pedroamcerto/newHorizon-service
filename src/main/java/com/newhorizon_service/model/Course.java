package com.newhorizon_service.model;

import com.newhorizon_service.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Course extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 255, name = "name")
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Trail> trails;
}
