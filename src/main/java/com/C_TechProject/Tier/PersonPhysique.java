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
@Table(name = "_PersonPhysique")
public class PersonPhysique {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String cin;
    private String adressemail;
    private String Contact;
    private String adresse;

    @Column(name = "rib", columnDefinition = "BIGINT")

    private String rib;

    @OneToMany(mappedBy = "personnePhysique", cascade = CascadeType.ALL)
    @Transient
    private List<Operation> operations;


}
