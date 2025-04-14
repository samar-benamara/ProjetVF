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
@Table(name = "_PersonMorale")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonMorale extends Person {
    private String name;
    private String code;

    @OneToMany(mappedBy = "personneMorale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operations;
}

