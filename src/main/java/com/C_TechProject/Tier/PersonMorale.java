package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_PersonMorale")
public class PersonMorale {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String code;
    private String email;
    private String contact;

    @Column(name = "rib", columnDefinition = "BIGINT")

    private String rib;

    @OneToMany(mappedBy = "personneMorale", cascade = CascadeType.ALL)
    @Transient
    private List<Operation> operations;


}
