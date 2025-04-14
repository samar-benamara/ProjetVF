package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

// ===== GOF Abstract : Classe concrète spécialisée =====

@Entity
@Table(name = "_PersonPhysique")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonPhysique extends Person {
    private String firstName;
    private String lastName;
    private String cin;
    private String adresse;

    @OneToMany(mappedBy = "personnePhysique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operations;
}
