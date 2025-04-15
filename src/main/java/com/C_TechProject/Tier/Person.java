package com.C_TechProject.Tier;

import jakarta.persistence.*;
import lombok.Data;


@Data
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String contact;

    @Column(name = "rib", columnDefinition = "BIGINT")
    private String rib;
}
