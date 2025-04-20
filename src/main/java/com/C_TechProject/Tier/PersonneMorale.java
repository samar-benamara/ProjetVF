package com.C_TechProject.Tier;

import com.C_TechProject.Operation.Operation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personne_morale")
public class PersonneMorale extends Tier {

    private String nom;
    private String code;

    @OneToMany(mappedBy = "personneMorale", cascade = CascadeType.ALL)
    @Transient
    private List<Operation> operations;
}
